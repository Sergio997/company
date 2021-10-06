package com.company.service.company.impl;

import com.company.dao.Company;
import com.company.common.dtos.request.CompanyRequestDto;
import com.company.common.dtos.response.CompanyResponseDto;
import com.company.common.dtos.response.PageResponse;
import com.company.mapper.CompanyMapper;
import com.company.repository.CompanyRepository;
import com.company.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;

    private final CompanyMapper mapper;

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto) {
        Company company = mapper.requestToEntity(companyRequestDto);

        return mapper.toCompanyResponseDto(repository.save(company));
    }

    @Override
    public CompanyResponseDto getCompanyByReference(UUID reference) {
        Optional<Company> optionalCompany = repository.findByReference(reference);
        if (optionalCompany.isEmpty()) {
            log.error(String.format("Company not found 'reference' : %s", reference.toString()));
        }

        return mapper.toCompanyResponseDto(optionalCompany.get());
    }

    @Override
    public PageResponse<CompanyResponseDto> getCompanies(Pageable pageable) {
        Page<Company> allCompanies = repository.findAll(pageable);
        if (Objects.isNull(allCompanies)) {
            log.error("not fount companies");
        }

        return mapper.toPageResponseDto(allCompanies);
    }

    @Override
    @Transactional
    public CompanyResponseDto updateCompany(UUID reference, CompanyRequestDto companyRequestDto) {
        Optional<Company> optionalCompany = repository.findByReference(reference);
        if (optionalCompany.isEmpty()) {
            log.error(String.format("Company not found 'reference' : %s", reference.toString()));
        }

        Company company = optionalCompany.get();

        return mapper.toCompanyResponseDto(mapper.requestToEntity(companyRequestDto, company));
    }

    @Override
    public void deleteCompanyByReference(UUID reference) {
        Optional<Company> optionalCompany = repository.findByReference(reference);
        if (optionalCompany.isEmpty()) {
            log.error(String.format("Company not found 'reference' : %s", reference.toString()));
        }
        repository.delete(optionalCompany.get());
    }
}
