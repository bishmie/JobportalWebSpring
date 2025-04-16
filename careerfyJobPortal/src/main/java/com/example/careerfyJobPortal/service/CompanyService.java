package com.example.careerfyJobPortal.service;

import com.example.careerfyJobPortal.dto.CompanyDTO;
import com.example.careerfyJobPortal.entity.Company;

import java.util.List;

public interface CompanyService {
    Company saveCompany(CompanyDTO companyDTO);

    Company updateCompany(Long id, CompanyDTO companyDTO);

    List<CompanyDTO> getAllCompanies();
}
