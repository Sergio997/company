package com.company.mapper;

import com.company.dao.Company;
import com.company.common.dtos.request.CompanyRequestDto;
import com.company.common.dtos.response.CompanyResponseDto;
import com.company.common.dtos.response.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyResponseDto toCompanyResponseDto(Company company);

    Company requestToEntity(CompanyRequestDto companyRequestDto);

    Company requestToEntity(CompanyRequestDto companyRequestDto, @MappingTarget Company company);

    default PageResponse<CompanyResponseDto> toPageResponseDto(Page<Company> page) {
        return new PageResponse<>
                (Long.parseLong(String.valueOf(page.getTotalPages())),
                        Long.parseLong(String.valueOf(page.getSize())),
                        page.getTotalElements(),
                        page.getContent().stream()
                                .map(this::toCompanyResponseDto)
                                .collect(Collectors.toList()));
    }
}
