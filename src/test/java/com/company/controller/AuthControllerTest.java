package com.company.controller;

import com.company.common.dtos.request.LoginRequestDto;
import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;
import com.company.config.TestConfig;
import com.company.controller.publicapi.PublicAuthController;
import com.company.utils.TestMapperUtil;
import com.company.utils.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PublicAuthController.class)
@ContextConfiguration(classes = {TestConfig.class})
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicAuthController publicAuthControllerMock;

    private final UserReferencePublicResponseDto userResponseDto = TestUtil.createUserReferencePublicResponseDto();
    private final LoginRequestDto loginRequestDto = TestUtil.createLoginRequest();
    private final RegisterUserRequestDto registerUserRequestDto = TestUtil.createRegisterUserRequestDto();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loginUserTest() throws Exception {
        when(publicAuthControllerMock.loginUser(loginRequestDto))
                .thenReturn(userResponseDto);

        mockMvc.perform(post("/public/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestMapperUtil.convertObjectToJsonBytes(loginRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userResponseDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userResponseDto.getLastName()))
                .andExpect(jsonPath("$.enabled").value(userResponseDto.getEnabled().toString()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole().toString()));
    }

    @Test
    void registrationUserTest() throws Exception {
        when(publicAuthControllerMock.registrationUser(registerUserRequestDto))
                .thenReturn(userResponseDto);

        mockMvc.perform(post("/public/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestMapperUtil.convertObjectToJsonBytes(registerUserRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(userResponseDto.getEmail()))
                .andExpect(jsonPath("$.firstName").value(userResponseDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(userResponseDto.getLastName()))
                .andExpect(jsonPath("$.enabled").value(userResponseDto.getEnabled().toString()))
                .andExpect(jsonPath("$.role").value(userResponseDto.getRole().toString()));
    }

    @Test
    void registrationUserTestWithException() throws Exception {
        registerUserRequestDto.setLastname("last name");
        registerUserRequestDto.setEmail("backend/notification/email");

        when(publicAuthControllerMock.registrationUser(registerUserRequestDto))
                .thenReturn(userResponseDto);

        mockMvc.perform(post("/public/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestMapperUtil.convertObjectToJsonBytes(registerUserRequestDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
