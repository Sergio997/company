package com.company.controller.publicapi;

import com.company.common.dtos.request.LoginRequestDto;
import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;
import com.company.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("public/api/v1")
public class PublicAuthController {

    @Autowired
    private AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/registration")
    @ApiOperation(value = "Register user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "register user", response = UserReferencePublicResponseDto.class),
    })
    public UserReferencePublicResponseDto registrationUser(@RequestBody @Valid RegisterUserRequestDto request) {
        return authService.registerNewAccount(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "/login")
    public UserReferencePublicResponseDto loginUser(@RequestBody @Valid LoginRequestDto request) {
        return authService.loginUser(request);
    }

}
