package com.youcash.uc.service;

import com.youcash.uc.entity.User;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author linhansheng.
 * @date 2018/3/23
 */
@Path("/user")
@Produces({ MediaType.TEXT_XML,MediaType.APPLICATION_JSON })
public interface UserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    @POST
    @Path("/login")
    String login(@FormParam("username") String username, @FormParam("password") String password);

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    @POST
    @Path("/register")
    String register(@BeanParam User user);

    /**
     * 刷新密钥
     *
     * @param oldToken 原密钥
     * @return 新密钥
     */
    @GET
    @Path("/refresh")
    @PreAuthorize("hasRole('ROLE_USER')")
    String refreshToken (@HeaderParam("Authorization") String oldToken);
}
