package com.company.common.dtos.request;

import com.company.common.utils.Constants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoginRequestDto {

    @NotNull
    @Pattern(regexp = Constants.ValidationRegex.LOGIN_REGEX,
            message = Constants.Message.EMAIL_MESSAGE)
    @NotBlank
    @EqualsAndHashCode.Include
    @ToString.Include
    private String email;

    @NotNull(message = Constants.Message.NOT_EMPTY)
    @NotBlank
    @EqualsAndHashCode.Include
    @ToString.Include
    private String password;

}
