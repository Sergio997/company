package com.company.security;

import com.company.common.dtos.enums.Role;
import com.company.dao.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAccount implements UserDetails {

    @EqualsAndHashCode.Include
    @ToString.Include
    private final String username;

    @EqualsAndHashCode.Include
    @ToString.Include
    private final String password;

    @EqualsAndHashCode.Include
    @ToString.Include
    private final List<SimpleGrantedAuthority> authorities;

    @EqualsAndHashCode.Include
    @ToString.Include
    private final boolean isActive;

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;

    }

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getEnabled().equals(true),
                user.getEnabled().equals(true),
                user.getEnabled().equals(true),
                user.getEnabled().equals(true),
                mapToGrantedAuthorities(user.getRole())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
