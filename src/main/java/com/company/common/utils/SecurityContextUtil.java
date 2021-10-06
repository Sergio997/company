package com.company.common.utils;

import com.company.common.dtos.enums.ErrorCode;
import com.company.common.dtos.enums.Role;
import com.company.common.exceptions.BackendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

@Slf4j
public class SecurityContextUtil {

    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated() ||
                Objects.isNull(authentication.getPrincipal())) {
            return null;
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        } else {
            log.error("User not found ");
            throw new BackendException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public static boolean currentUserHasAuthority(Role role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(role) || Objects.isNull(authentication) ||
                !authentication.isAuthenticated() || Objects.isNull(authentication.getPrincipal())) {
            return false;
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getAuthorities()
                    .stream().anyMatch(grantedAuthority -> role.toString()
                            .equalsIgnoreCase(grantedAuthority.getAuthority()));
        } else {
            return false;
        }
    }
}
