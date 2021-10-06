package com.company.service.user.impl;

import com.company.common.dtos.enums.ErrorCode;
import com.company.common.dtos.enums.Role;
import com.company.common.dtos.request.UserRequestDto;
import com.company.common.dtos.request.UserUpdateRequestDto;
import com.company.common.dtos.response.UserResponseDto;
import com.company.common.exceptions.BackendException;
import com.company.common.utils.SecurityContextUtil;
import com.company.dao.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDto getUserByReference(UUID reference) {
        Optional<User> byReferenceId = userRepository.findByReference(reference);
        if (byReferenceId.isEmpty()) {
            log.error("User not found 'reference ': " + reference);
            throw new BackendException(ErrorCode.USER_NOT_FOUND);
        }

        return userMapper.modelToResponse(byReferenceId.get());
    }

    @Override
    public UserResponseDto getUserDetails() {
        return userMapper.modelToResponse(getCurrentUser());
    }

    @Override
    public UserResponseDto createUser(UserRequestDto request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            log.error("User is exist : " + request.getEmail());
            throw new BackendException(ErrorCode.USER_ALREADY_EXIST);
        }

        User user = userMapper.requestToModel(request);

        return userMapper.modelToResponse(userRepository.save(user));
    }


    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto request) {
        User currentUser = getCurrentUser();
        if (Objects.isNull(currentUser)) {
            log.error("User not found");
            throw new BackendException(ErrorCode.USER_NOT_FOUND);
        }

        User savedUser = userMapper.requestToEntity(request, currentUser);

        return userMapper.modelToResponse(savedUser);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextUtil.getCurrentUserName();
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            log.error("User not found with email " + username);
            throw new BackendException(ErrorCode.USER_NOT_FOUND);
        }

        return userOptional.get();
    }

    @Override
    public boolean checkIfUserRole() {
        User currentUser = getCurrentUser();
        if (!Role.USER.equals(currentUser.getRole()) &&
                !Role.ADMIN.equals(currentUser.getRole())) {
            return false;
        }

        return true;
    }
}
