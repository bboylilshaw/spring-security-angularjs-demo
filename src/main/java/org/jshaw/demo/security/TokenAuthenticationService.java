package org.jshaw.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public void addAuthentication(HttpServletResponse response, Authentication authentication) {
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(userDetails));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            final UserDetails userDetails = tokenHandler.parseUserFromToken(token);
            if (userDetails != null) {
                UserAuthentication userAuthentication = new UserAuthentication(userDetails);
                userAuthentication.setAuthenticated(true);
                return userAuthentication;
            }
        }
        return null;
    }

}
