package com.company.service;

import com.company.dao.dto.request.CompanyRequestDto;
import com.company.dao.dto.response.CompanyResponseDto;
import com.company.dao.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompanyService {

    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto);

    CompanyResponseDto getCompanyByReference(UUID reference);

    PageResponse<CompanyResponseDto> getCompanies(Pageable pageable);

    CompanyResponseDto updateCompany(UUID reference, CompanyRequestDto companyRequestDto);

    void deleteCompanyByReference(UUID reference);

}
