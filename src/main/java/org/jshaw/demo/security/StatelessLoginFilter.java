package org.jshaw.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jshaw.demo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatelessLoginFilter.class);

    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserDetailsService userDetailsService;

    public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService, UserDetailsService userDetailsService, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(urlMapping));
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userDetailsService = userDetailsService;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Authenticate user successfully");
        final UserDetails authenticatedUser;
        if (authResult.getPrincipal() instanceof User) {
            authenticatedUser = new UserDetailsImpl((User) authResult.getPrincipal());
        } else {
            // Lookup the complete User object from the database and create an Authentication for it
            authenticatedUser = userDetailsService.loadUserByUsername(authResult.getName());
        }

        final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

        // Add the custom token as HTTP header to the response
        tokenAuthenticationService.addAuthentication(response, userAuthentication);

        // Add the authentication to the Security context
        SecurityContextHolder.getContext().setAuthentication(userAuthentication);
    }
}
