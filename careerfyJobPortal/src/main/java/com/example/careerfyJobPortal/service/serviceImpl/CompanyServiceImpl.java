package com.example.careerfyJobPortal.service.serviceImpl;


import com.example.careerfyJobPortal.dto.CompanyDTO;
import com.example.careerfyJobPortal.entity.Company;
import com.example.careerfyJobPortal.repositry.ComapanyReposity;
import com.example.careerfyJobPortal.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private ComapanyReposity comapanyReposity;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Company saveCompany(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        return comapanyReposity.save(company);
    }


    @Override
    public Company updateCompany(Long id, CompanyDTO companyDTO) {
        Optional<Company> existingCompanyOpt = comapanyReposity.findById(Math.toIntExact(id));

        if (existingCompanyOpt.isPresent()) {
            Company existingCompany = existingCompanyOpt.get();
            modelMapper.map(companyDTO, existingCompany);  // Map DTO to Entity
            return comapanyReposity.save(existingCompany);
        }
        return null;  // Company not found
    }


    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = comapanyReposity.findAll();

        return companies.stream().map(company ->
                new CompanyDTO(
                        company.getId(),
                        company.getName(),
                        company.getEmail(),
                        company.getCompanyDescription(),
                        company.getLogo(),
                        null // Jobs are not included here, modify if needed
                )
        ).collect(Collectors.toList());
    }


}








//












//    @Override
//    public Company updateCompany(Long id, CompanyDTO companyDTO) {
//        Company existingCompany = comapanyReposity.findById(Math.toIntExact(id)).orElse(null);
//        if (existingCompany != null) {
//            existingCompany.setName(companyDTO.getName());
//            existingCompany.setEmail(companyDTO.getEmail());
//            existingCompany.setCompanyDescription(companyDTO.getCompanyDescription());
//            existingCompany.setLogo(companyDTO.getLogo());
//            // If you want to update jobs, handle it here as well
//
//            return comapanyReposity.save(existingCompany);
//        }
//        return null;  // If company is not found
//    }


//
//@Override
//public List<CompanyDTO> getAllCompanies() {
//    try {
//        List<Company> companies = comapanyReposity.findAll();
//        return companies.stream()
//                .map(company -> modelMapper.map(company, CompanyDTO.class))
//                .collect(Collectors.toList());
//    } catch (Exception e) {
//        throw new RuntimeException("Error retrieving companies", e);
//    }
//}


//    @Override
//    public Company saveCompany(CompanyDTO companyDTO) {
//        Company company = new Company();
//        company.setName(companyDTO.getName());
//        company.setEmail(companyDTO.getEmail());
//        company.setCompanyDescription(companyDTO.getCompanyDescription());
//        company.setLogo(companyDTO.getLogo());
//        // If you need to handle jobs too, do it here.
//
//        // Save the company entity to DB
//        return comapanyReposity.save(company);
//    }