package com.company.service.auth.impl;

import com.company.common.dtos.enums.ErrorCode;
import com.company.common.dtos.enums.Role;
import com.company.common.dtos.request.LoginRequestDto;
import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;
import com.company.common.exceptions.BackendException;
import com.company.common.utils.Constants;
import com.company.dao.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.security.jwt.JwtTokenProvider;
import com.company.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Value("${spring.security.jwt.token-time}")
    private Long tokenTime;

    @Value("${spring.security.jwt.refresh-token-time}")
    private Long refreshTokenTime;

    @Override
    public UserReferencePublicResponseDto loginUser(LoginRequestDto request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            log.error(String.format("User with email '%s' not found", request.getEmail().toLowerCase()));
            throw new BackendException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            log.error(String.format("Wrong password for user with reference '%s", userOptional.get().getReference()));
            throw new BackendException(ErrorCode.BAD_CREDENTIALS);
        }

        if (userOptional.get().getEnabled().equals(Boolean.FALSE)) {
            log.error(String.format("User with reference '%s' is not enabled", userOptional.get().getReference()));
            throw new BackendException(ErrorCode.BAD_CREDENTIALS);
        }
        User user = userOptional.get();

        String token = tokenProvider.createToken(request.getEmail(), user.getRole().name(), tokenTime);
        String refreshToken = tokenProvider.createToken(request.getEmail().toLowerCase(), user.getRole().name(), refreshTokenTime);
        response.addHeader(Constants.AUTHORIZATION, Constants.BEARER + token);

        UserReferencePublicResponseDto userReferencePublicResponseDto = userMapper.modelToUserReferenceResponse(user);
        userReferencePublicResponseDto.setAuthorization(token);
        userReferencePublicResponseDto.setRefreshToken(refreshToken);

        return userReferencePublicResponseDto;
    }

    @Override
    public UserReferencePublicResponseDto registerNewAccount(RegisterUserRequestDto request) {
        User checkUser = userRepository.findByEmail(request.getEmail())
                .orElse(null);
        if (Objects.nonNull(checkUser) && checkUser.getEnabled().equals(false)) {
            log.error("User is exist: " + request.getEmail());
            throw new BackendException(ErrorCode.USER_ALREADY_EXIST);
        }

        User user = userMapper.registrationUserRequestToEntity(request);
        user.setRole(Role.USER);
        user.setEnabled(Boolean.TRUE);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);

        return userMapper.modelToUserReferenceResponse(userRepository.save(user));

    }
}
