package com.company.service.user;

import com.company.common.dtos.request.UserRequestDto;
import com.company.common.dtos.request.UserUpdateRequestDto;
import com.company.common.dtos.response.UserResponseDto;
import com.company.dao.User;

import java.util.UUID;

public interface UserService {

    UserResponseDto getUserByReference(UUID reference);

    UserResponseDto getUserDetails();

    UserResponseDto createUser(UserRequestDto request);

    UserResponseDto updateUser(UserUpdateRequestDto request);

    User getCurrentUser();

    boolean checkIfUserRole();

}
