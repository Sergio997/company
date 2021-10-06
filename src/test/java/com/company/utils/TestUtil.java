package com.company.utils;


import com.company.common.dtos.enums.Role;
import com.company.common.dtos.request.LoginRequestDto;
import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.request.UserRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;
import com.company.common.dtos.response.UserResponseDto;
import com.company.dao.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestUtil {


    private static final UUID USER_REFERENCE = UUID.randomUUID();
    private static final String USER_EMAIL = "email@email.com";
    private static final String USER_PASSWORD = "password";
    private static final Boolean USER_ENABLED = true;
    private static final String USER_LAST_NAME = "lastName";
    private static final String USER_FIRST_NAME = "firstName";
    private static final Role USER_ROLE = Role.USER;


    public static User createUser() {
        return new User() {{
            setReference(USER_REFERENCE);
            setEmail(USER_EMAIL);
            setFirstname(USER_FIRST_NAME);
            setLastname(USER_LAST_NAME);
            setPassword(USER_PASSWORD);
            setEnabled(USER_ENABLED);
            setRole(Role.USER);
        }};
    }

    public static UserResponseDto createUserResponseDto() {
        return UserResponseDto.builder()
                .reference(USER_REFERENCE)
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .build();
    }

    public static UserReferencePublicResponseDto createUserReferencePublicResponseDto() {
        return new UserReferencePublicResponseDto() {{
            setEmail(USER_EMAIL);
            setEnabled(USER_ENABLED);
            setFirstName(USER_FIRST_NAME);
            setLastName(USER_LAST_NAME);
            setRole(USER_ROLE);
        }};

    }

    public static UserRequestDto createUserRequestDto() {
        return UserRequestDto.builder()
                .email(USER_EMAIL)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME).build();
    }

    public static LoginRequestDto createLoginRequest() {
        return new LoginRequestDto() {{
            setEmail(USER_EMAIL);
            setPassword(USER_PASSWORD);
        }};
    }

    public static RegisterUserRequestDto createRegisterUserRequestDto() {
        return new RegisterUserRequestDto() {{
            setEmail(USER_EMAIL);
            setPassword(USER_PASSWORD);
            setFirstname(USER_FIRST_NAME);
            setLastname(USER_LAST_NAME);
        }};
    }
}

