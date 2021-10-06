package com.company.common.dtos.request;

import com.company.common.utils.Constants;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegisterUserRequestDto {

    @Pattern(regexp = Constants.ValidationRegex.LOGIN_REGEX,
            message = Constants.Message.WRONG_PATTERN)
    @NotBlank(message = Constants.Message.NOT_EMPTY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String email;

    @NotNull
    @NotBlank(message = Constants.Message.NOT_EMPTY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String password;

    @Size(min = 2)
    @NotBlank(message = Constants.Message.NOT_EMPTY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String firstname;

    @Size(min = 2)
    @NotBlank(message = Constants.Message.NOT_EMPTY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private String lastname;

}
