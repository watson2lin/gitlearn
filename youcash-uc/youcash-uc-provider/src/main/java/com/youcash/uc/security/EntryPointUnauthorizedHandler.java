package com.youcash.uc.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linhansheng.
 * @date 2018/3/23
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        response.setStatus(401);
        response.getWriter().print("{\"code\":1,\"message\":\"" + e.getMessage() + "\"}");
        response.getWriter().flush();

    }
}
