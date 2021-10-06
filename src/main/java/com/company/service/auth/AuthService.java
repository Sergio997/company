package com.company.service.auth;

import com.company.common.dtos.request.LoginRequestDto;
import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;

public interface AuthService {

    UserReferencePublicResponseDto loginUser(LoginRequestDto request);

    UserReferencePublicResponseDto registerNewAccount(RegisterUserRequestDto request);

}
