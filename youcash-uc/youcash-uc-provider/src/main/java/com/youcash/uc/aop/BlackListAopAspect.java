package com.youcash.uc.aop;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.UrlUtils;
import com.alibaba.dubbo.rpc.RpcContext;
import com.youcash.uc.common.UCWebApplicationException;
import com.youcash.uc.common.ReflectUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author linhs
 * @Date 2018/2/13 0013
 */
@Aspect
@Configuration
public class BlackListAopAspect {

    /**
     * 定义AOP扫描路径
     */
    @Pointcut("execution(public * com.youcash.uc.impl.*.*.*(..))")
    public void blackList() {
    }

    @Around("blackList()")
    public Object doAroundInServiceLayer(ProceedingJoinPoint joinPoint) {
        Class<?> targetInterface = joinPoint.getTarget().getClass().getInterfaces()[0];
        Object[] args = joinPoint.getArgs();
        String realIp = RpcContext.getContext().getRemoteAddressString().split(":")[0];
        String interfaceName = null;
        try {
            //从目标接口中获取到接口全限定名称
            Field name = ReflectUtils.getDeclaredField(targetInterface, "name");
            interfaceName = (String) name.get(targetInterface);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从Zookeeper中获取到该接口对应服务的路由规则，路由规则包含有黑白名单
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(3, 5000));
        client.start();// 连接
        try {
            // 获取子节点
            List<String> children = client.getChildren().forPath("/dubbo/" + interfaceName + "/routers");
            if (children.size() == 0) {
                return joinPoint.proceed(args);
            }

            for (String url : children) {
                String route = URL.decode(URL.decode(url));
                if (route.contains("consumer.host !=") && route.contains("consumer.host =")) {
                    String[] whiteList = null;
                    String[] split = route.substring(route.indexOf("consumer.host"), route.lastIndexOf("=>")).split("&");
                    for (String blackWhiteList : split) {
                        if (blackWhiteList.contains("!=")) {
                            whiteList = blackWhiteList.split("!=")[1].split(",");
                        }
                    }
                    boolean forbid = true;
                    for (String white : whiteList) {
                        if (UrlUtils.isMatchGlobPattern(white.trim(), realIp)) {
                            return joinPoint.proceed(args);
                        } else {
                            forbid = false;
                        }
                    }
                    if (!forbid) {
                        throw new RuntimeException("Consumer IP:" + realIp + " in the service blacklist.");
                    }
                }
                //whiteList
                if (route.contains("consumer.host !=")) {
                    boolean forbid = true;
                    String[] whiteList = route.substring(route.indexOf("consumer.host"), route.lastIndexOf("=>")).split("!=")[1].split(",");
                    for (String white : whiteList) {
                        if (UrlUtils.isMatchGlobPattern(white.trim(), realIp)) {
                            return joinPoint.proceed(args);
                        } else {
                            forbid = false;
                        }
                    }
                    if (!forbid) {
                        throw new RuntimeException("Consumer IP:" + realIp + " in the service blacklist.");
                    }
                }
                //blackList
                if (route.contains("consumer.host =")) {
                    String[] blackList = route.substring(route.indexOf("consumer.host"), route.lastIndexOf("=>")).split("=")[1].split(",");
                    boolean forbid = true;
                    for (String black : blackList) {
                        if (UrlUtils.isMatchGlobPattern(black.trim(), realIp)) {
                            forbid = false;
                        }
                    }
                    if (forbid) {
                        return joinPoint.proceed(args);
                    } else {
                        throw new RuntimeException("Consumer IP:" + realIp + " in the service blacklist.");
                    }
                }
            }
            return joinPoint.proceed(args);
        } catch (Exception e) {
            if (client != null) {
                client.close();
            }
            throw new UCWebApplicationException("500", e.getMessage());
        } catch (Throwable throwable) {
            if (client != null) {
                client.close();
            }
            throw new UCWebApplicationException("500", throwable.getMessage());
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
