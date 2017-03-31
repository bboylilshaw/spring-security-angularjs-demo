package org.jshaw.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private final UserDetails userDetails;
    private boolean isAuthenticated = false;

    public UserAuthentication(UserDetails userDetails) {
        super();
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return this.userDetails.getPassword();
    }

    @Override
    public Object getDetails() {
        return this.userDetails;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }
}
