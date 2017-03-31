package org.jshaw.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Used for auth user for every request
 *
 * @author Jason Xiao
 */
public class TokenAuthenticationFilter extends GenericFilterBean {

    private final TokenAuthenticationService authenticationService;

    public TokenAuthenticationFilter(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        if (httpRequest.getRequestURI().toLowerCase().startsWith("/api")) {
            Authentication authentication = authenticationService.getAuthentication(httpRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
