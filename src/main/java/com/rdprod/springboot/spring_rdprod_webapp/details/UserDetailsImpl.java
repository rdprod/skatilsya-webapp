package com.rdprod.springboot.spring_rdprod_webapp.details;

import com.rdprod.springboot.spring_rdprod_webapp.entity.Role;
import com.rdprod.springboot.spring_rdprod_webapp.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        user.getRoles().forEach(role ->
                grantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getName())));

        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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

    public String getEmail() {
        return user.getEmail();
    }

    public Set<Role> getRoles() {
        return user.getRoles();
    }

    public int getId() {
        return user.getId();
    }
}
