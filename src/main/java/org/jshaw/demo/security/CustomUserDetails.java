package org.jshaw.demo.security;

import org.jshaw.demo.common.Role;
import org.jshaw.demo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Jason on 6/15/15.
 */
public class CustomUserDetails extends User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        if (getRole().equals(Role.USER)) {
            authList = AuthorityUtils.createAuthorityList("ROLE_USER");
        } else if (getRole().equals(Role.ADMIN)) {
            authList = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        } else if (getRole().equals(Role.SUPER_ADMIN)) {
            authList = AuthorityUtils.createAuthorityList("ROLE_SUPER_ADMIN");
        }
        return authList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
