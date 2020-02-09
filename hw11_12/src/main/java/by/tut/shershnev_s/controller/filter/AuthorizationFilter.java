package by.tut.shershnev_s.controller.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        String login = (String) session.getAttribute("login");
        String role = (String) session.getAttribute("role");
        if (login.isEmpty() || role.isEmpty()) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + "/");
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + "/user");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
