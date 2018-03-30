package com.youcash.uc.common;


import com.alibaba.dubbo.registry.integration.RegistryDirectory;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.router.condition.ConditionRouter;
import com.alibaba.dubbo.rpc.cluster.support.wrapper.MockClusterInvoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.util.*;

/**
 * @author linhs
 * @Date 2018/2/12 0012
 */
public class WhiteListUtil {

    public static Map<String, List<String>> getBlackWhite(Object instance) {
        try {
            Field handler = ReflectUtils.getDeclaredField(instance, "handler");
            InvocationHandler obj = (InvokerInvocationHandler) handler.get(instance);
            Field invokerField = ReflectUtils.getDeclaredField(obj, "invoker");
            Invoker invoker = (MockClusterInvoker) invokerField.get(obj);
            Field directoryField = ReflectUtils.getDeclaredField(invoker, "directory");
            Directory directory = (Directory) directoryField.get(invoker);

            Map<String, List<String>> blackWhiteMap = new HashMap(16);
            List<String> blackList = new ArrayList<>();
            List<String> whiteList = new ArrayList<>();

            if (directory instanceof RegistryDirectory) {
                Field routerField = ReflectUtils.getDeclaredField(directory, "routers");
                List routers = (ArrayList) routerField.get(directory);
                parseBlackWhiteList(routers, blackList, whiteList);

            } else {
                Field nestInvokerField = ReflectUtils.getDeclaredField(directory, "invokers");
                List<Invoker> nestInvokers = (ArrayList) nestInvokerField.get(directory);
                for (Invoker netsInvoker : nestInvokers) {
                    Field nestDirectory = ReflectUtils.getDeclaredField(netsInvoker, "directory");
                    Directory d = (Directory) nestDirectory.get(netsInvoker);
                    Field routersF = ReflectUtils.getDeclaredField(d, "routers");
                    List routers = (ArrayList) routersF.get(d);
                    parseBlackWhiteList(routers, blackList, whiteList);
                }
            }
            blackWhiteMap.put("black", blackList);
            blackWhiteMap.put("white", whiteList);
            return blackWhiteMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isBlackOrWhite(Object instance) {
        Map<String, List<String>> blackWhite = WhiteListUtil.getBlackWhite(instance);
        String realIp = RpcContext.getContext().getRemoteHost();
        List<String> blackList = blackWhite.get("black");
        List<String> whiteList = blackWhite.get("white");
        if (whiteList.size() > 0) {
            if (whiteList.contains(realIp)) {
                return true;
            } else {
                return false;
            }
        } else if (blackList.size() > 0) {
            if (blackList.contains(realIp)) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private static void parseBlackWhiteList(List routers, List<String> blackList, List<String> whiteList) {
        try {
            for (int i = 0; i < routers.size(); i++) {
                Object router = routers.get(i);
                if (router instanceof ConditionRouter) {
                    ConditionRouter condition = (ConditionRouter) router;
                    Field whenCondition = ReflectUtils.getDeclaredField(condition, "whenCondition");
                    Map map = (HashMap) whenCondition.get(condition);

                    //黑名单
                    Field matchesField = ReflectUtils.getDeclaredField(map.get("host"), "matches");
                    Set blackHost = (HashSet) matchesField.get(map.get("host"));
                    Iterator it = blackHost.iterator();
                    while (it.hasNext()) {
                        String black = (String) it.next();
                        if (blackList.contains(black)) {
                            continue;
                        }
                        blackList.add(black);
                    }

                    //白名单
                    Field mismatchesField = ReflectUtils.getDeclaredField(map.get("host"), "mismatches");
                    Set whiteHost = (HashSet) mismatchesField.get(map.get("host"));
                    Iterator wIt = whiteHost.iterator();
                    while (wIt.hasNext()) {
                        String white = (String) wIt.next();
                        if (whiteList.contains(white)) {
                            continue;
                        }
                        whiteList.add(white);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
