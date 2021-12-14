package com.example.cs319project.security;

import com.example.cs319project.model.Role;
import com.example.cs319project.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(Integer id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static MyUserDetails build(User user) {
        Role role = user.getRole();

        GrantedAuthority authority = new SimpleGrantedAuthority(role.getName().name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);

        return new MyUserDetails(user.getId(), user.getName(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
