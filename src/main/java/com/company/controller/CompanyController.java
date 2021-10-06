package com.company.controller;

import com.company.common.dtos.request.CompanyRequestDto;
import com.company.common.dtos.response.CompanyResponseDto;
import com.company.common.dtos.response.PageResponse;
import com.company.service.company.CompanyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @ApiOperation(value = "get companies")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "get companies", response = CompanyResponseDto.class),
    })
    public PageResponse<CompanyResponseDto> getCompanies(Pageable pageable) {
        return companyService.getCompanies(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{reference}")
    @ApiOperation(value = "get companies")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "get companies", response = CompanyResponseDto.class),
    })
    public CompanyResponseDto getCompanyByReference(@PathVariable("reference") UUID reference) {
        return companyService.getCompanyByReference(reference);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @ApiOperation(value = "create company")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "created company", response = CompanyResponseDto.class),
    })
    public CompanyResponseDto createCompany(@Valid CompanyRequestDto companyRequestDto) {
        return companyService.createCompany(companyRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{reference}")
    @ApiOperation(value = "update company")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "update company", response = CompanyResponseDto.class),
    })
    public CompanyResponseDto updateCompany(CompanyRequestDto companyRequestDto, @PathVariable("reference") UUID reference) {
        return companyService.updateCompany(reference, companyRequestDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{reference}")
    @ApiOperation(value = "delete company by reference")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "delete company"),
    })
    public void deleteCompany(@PathVariable("reference") UUID reference) {
        companyService.deleteCompanyByReference(reference);
    }

}
