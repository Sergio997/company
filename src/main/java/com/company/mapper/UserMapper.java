package com.company.mapper;

import com.company.common.dtos.request.RegisterUserRequestDto;
import com.company.common.dtos.request.UserRequestDto;
import com.company.common.dtos.request.UserUpdateRequestDto;
import com.company.common.dtos.response.UserReferencePublicResponseDto;
import com.company.common.dtos.response.UserResponseDto;
import com.company.dao.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto modelToResponse(User user);

    User requestToModel(UserRequestDto request);

    UserReferencePublicResponseDto modelToUserReferenceResponse(User user);

    User registrationUserRequestToEntity(RegisterUserRequestDto request);

    User requestToEntity(UserRequestDto request, @MappingTarget User user);

    User requestToEntity(UserUpdateRequestDto request, @MappingTarget User user);

}
