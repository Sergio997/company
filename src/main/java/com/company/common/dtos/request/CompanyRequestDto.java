package com.company.common.dtos.request;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CompanyRequestDto {

    @NotBlank
    @EqualsAndHashCode.Include
    @ToString.Include
    private String name;

}
