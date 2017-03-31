package org.jshaw.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used for add token to response header, and get user info from request header
 *
 * @author Jason Xiao
 */
public class TokenAuthenticationService {

    private static final String AUTH_TOKEN_HEADER = "X-Auth-Token";

    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public void addTokenToResponse(UserAuthentication authentication, HttpServletResponse response) {
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        String token = tokenHandler.createTokenForUser(userDetails);
        response.addHeader(AUTH_TOKEN_HEADER, token);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_TOKEN_HEADER);
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
