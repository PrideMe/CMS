package com.wangjikai.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 22717 on 2017/11/24.
 * 登陆校验
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        //除了登录页不拦截（整个网站只能显示登录页）外，其余页面均被拦截在外
        if ("/views/login.jsp".equals(path) || path.contains(".css")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/views/login.jsp");
        }
    }

    @Override
    public void destroy() {
    }
}
