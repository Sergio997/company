package com.company.service.auth;

import com.company.common.dtos.enums.Role;
import com.company.common.dtos.request.LoginRequestDto;
import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.request.UserRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;
import com.company.common.dtos.response.UserResponseDto;
import com.company.common.exceptions.BackendException;
import com.company.dao.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.security.jwt.JwtTokenProvider;
import com.company.service.auth.impl.AuthServiceImpl;
import com.company.service.user.impl.UserServiceImpl;
import com.company.utils.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl testObj;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private JwtTokenProvider tokenProviderMock;

    @Mock
    private UserServiceImpl userServiceMock;

    @Mock
    private UserMapper userMapperMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private Logger logger;


    private final User user = TestUtil.createUser();
    private final UserResponseDto userResponseDto = TestUtil.createUserResponseDto();
    private final UserReferencePublicResponseDto userReferencePublicResponseDto = TestUtil.createUserReferencePublicResponseDto();
    private final UserRequestDto userRequestDto = TestUtil.createUserRequestDto();
    private final LoginRequestDto loginRequestDto = TestUtil.createLoginRequest();
    private final RegisterUserRequestDto registerUserRequestDto = TestUtil.createRegisterUserRequestDto();

    private final String FAKE_PASSWORD = "fake password";
    private final String AUTHORIZATION = "Authorization";
    private final String BEARER = "Bearer";
    private final String TOKEN = "Token";
    private final String FAKE_EMAIL = "Fake.email";
    private final String FAKE_TOKEN = "Fake token";
    private final String FAKE_LOGIN_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2b2xvZHlteXIuZHVkYXMud29ya0BnbWFpbC5jb20iLCJyb2xlIjoiVVNFUiIsImlhdCI6MTYyNjY5MzcyMCwiZXhwIjoxNjI2Njk0MzIwfQ.NiYyumth77iVzN9P3KkB6ry5TpC6IFNxqZ0Fny0gg6g";
    private final String NEW_PASSWORD = "newPassword";
    private final String subject = String.format(" You have new temporary password.");
    private final UUID FAKE_UUID = UUID.randomUUID();
    private final Long TOKEN_TIME = 10L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loginUserTest() {
        user.setRole(Role.USER);
        Mockito.when(userRepositoryMock.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        Mockito.when(passwordEncoderMock.matches(user.getPassword(), user.getPassword()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(tokenProviderMock.createToken(userRequestDto.getEmail(), user.getRole().name(), TOKEN_TIME))
                .thenReturn(new String());
        Mockito.doNothing()
                .when(responseMock)
                .addHeader(AUTHORIZATION, TOKEN);
        Mockito.when(userMapperMock.modelToUserReferenceResponse(user))
                .thenReturn(userReferencePublicResponseDto);

        UserReferencePublicResponseDto result = testObj.loginUser(loginRequestDto);

        Mockito.verify(userRepositoryMock)
                .findByEmail(userRequestDto.getEmail());
        Mockito.verify(userMapperMock)
                .modelToUserReferenceResponse(user);

        Assertions.assertNotNull(result);
    }

    @Test
    void loginUserTestUserNotFound() {
        user.setRole(Role.USER);
        Mockito.when(userRepositoryMock.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        Mockito.when(passwordEncoderMock.matches(user.getPassword(), user.getPassword()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(tokenProviderMock.createToken(userRequestDto.getEmail(), user.getRole().name(), TOKEN_TIME))
                .thenReturn(new String());
        Mockito.doNothing()
                .when(responseMock)
                .addHeader(AUTHORIZATION, TOKEN);
        Mockito.when(userMapperMock.modelToUserReferenceResponse(user))
                .thenReturn(userReferencePublicResponseDto);

        loginRequestDto.setEmail(FAKE_EMAIL);
        Assertions.assertThrows(BackendException.class, () ->
                testObj.loginUser(loginRequestDto));
        Mockito.verifyNoMoreInteractions(logger);

    }

    @Test
    void loginUserTestBadCredentials() {
        user.setRole(Role.USER);
        user.setEnabled(false);
        Mockito.when(userRepositoryMock.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        Mockito.when(passwordEncoderMock.matches(user.getPassword(), user.getPassword()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(tokenProviderMock.createToken(userRequestDto.getEmail(), user.getRole().name(), TOKEN_TIME))
                .thenReturn(new String());
        Mockito.doNothing()
                .when(responseMock)
                .addHeader(AUTHORIZATION, TOKEN);
        Mockito.when(userMapperMock.modelToUserReferenceResponse(user))
                .thenReturn(userReferencePublicResponseDto);

        Assertions.assertThrows(BackendException.class, () ->
                testObj.loginUser(loginRequestDto));
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    void loginUserTestWrongPassword() {
        user.setRole(Role.USER);
        user.setPassword("wrongPass");
        Mockito.when(userRepositoryMock.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        Mockito.when(passwordEncoderMock.matches(user.getPassword(), user.getPassword()))
                .thenReturn(Boolean.TRUE);
        Mockito.when(tokenProviderMock.createToken(userRequestDto.getEmail(), user.getRole().name(), TOKEN_TIME))
                .thenReturn(new String());
        Mockito.doNothing()
                .when(responseMock)
                .addHeader(AUTHORIZATION, TOKEN);
        Mockito.when(userMapperMock.modelToUserReferenceResponse(user))
                .thenReturn(userReferencePublicResponseDto);

        Assertions.assertThrows(BackendException.class, () ->
                testObj.loginUser(loginRequestDto));
        Mockito.verifyNoMoreInteractions(logger);
    }

    @Test
    void registerNewAccountException() {
        user.setEnabled(false);
        Mockito.when(userRepositoryMock.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
        Mockito.when(userMapperMock.registrationUserRequestToEntity(registerUserRequestDto))
                .thenReturn(user);
        Mockito.when(tokenProviderMock.createVerifyToken(userRequestDto.getEmail(), user.getRole().name(), null))
                .thenReturn(new String());
        Mockito.when(userMapperMock.modelToUserReferenceResponse(user))
                .thenReturn(userReferencePublicResponseDto);

        Assertions.assertThrows(BackendException.class, () ->
                testObj.registerNewAccount(registerUserRequestDto));

        Mockito.verifyNoMoreInteractions(logger);
    }

}
