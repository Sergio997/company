package com.company.service.company;

import com.company.common.dtos.request.CompanyRequestDto;
import com.company.common.dtos.response.CompanyResponseDto;
import com.company.common.dtos.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompanyService {

    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto);

    CompanyResponseDto getCompanyByReference(UUID reference);

    PageResponse<CompanyResponseDto> getCompanies(Pageable pageable);

    CompanyResponseDto updateCompany(UUID reference, CompanyRequestDto companyRequestDto);

    void deleteCompanyByReference(UUID reference);

}
