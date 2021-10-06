package com.company.common.dtos.response;

import com.company.common.dtos.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserReferencePublicResponseDto {

    private UUID reference;

    private String email;

    private String firstName;

    private String lastName;

    private Boolean enabled;

    private String authorization;

    private String refreshToken;

    private Role role;

}
