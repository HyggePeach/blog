package com.wangjunblog.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * @author wangjun
 * @date 2024/7/17 21:56
 */
public class TokenFilter extends HttpFilter {


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        request.setAttribute("token", token);
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

    private boolean requiresAuth(HttpServletRequest request) {
        // /api/posts路径下所有GET方法都不需要权限
        String uri = request.getRequestURI();
        return uri.startsWith("/api/posts") && !"GET".equalsIgnoreCase(request.getMethod());
    }
}
