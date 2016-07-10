package org.jshaw.demo.security;

import org.jshaw.demo.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl extends User implements UserDetails {

    public UserDetailsImpl(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if (super.getRole().equals(Role.ROLE_USER)) {
            authorityList = AuthorityUtils.createAuthorityList(Role.ROLE_USER.toString());
        } else if (super.getRole().equals(Role.ROLE_ADMIN)) {
            authorityList = AuthorityUtils.createAuthorityList(Role.ROLE_ADMIN.toString(), Role.ROLE_USER.toString());
        }
        return authorityList;
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
