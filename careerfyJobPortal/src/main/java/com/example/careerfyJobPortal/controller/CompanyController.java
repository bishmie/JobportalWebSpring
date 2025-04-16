package com.example.careerfyJobPortal.controller;

import com.example.careerfyJobPortal.dto.CompanyDTO;
import com.example.careerfyJobPortal.entity.Company;
import com.example.careerfyJobPortal.service.CompanyService;
import com.example.careerfyJobPortal.utility.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
//@CrossOrigin(origins = "*")
@Validated
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @PostMapping("/save")
    public ResponseEntity<ResponseUtil> saveCompany(@RequestBody CompanyDTO companyDTO) {
        try {
            Company savedCompany = companyService.saveCompany(companyDTO);
            return ResponseEntity.ok(new ResponseUtil(201, "Company Saved Successfully", savedCompany));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseUtil(500, "Company Not Saved", null));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseUtil> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyDTO companyDTO) {
        try {
            Company updatedCompany = companyService.updateCompany(id, companyDTO);

            if (updatedCompany != null) {
                return ResponseEntity.ok(new ResponseUtil(200, "Company Updated Successfully", updatedCompany));
            }
            return ResponseEntity.status(404).body(new ResponseUtil(404, "Company Not Found", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ResponseUtil(500, "Error Updating Company", null));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }


}


//    @GetMapping("/getAll")
//    public ResponseEntity<ResponseUtil> getAllCompanies() {
//        try {
//            List<CompanyDTO> companies = companyService.getAllCompanies();
//            return ResponseEntity.ok(new ResponseUtil(200, "Companies fetched successfully", companies));
//        } catch (Exception e) {
//            return ResponseEntity.status(500)
//                    .body(new ResponseUtil(500, "Error Retrieving Companies", null));
//        }
//    }









//    @PostMapping("/save")
//    public ResponseEntity<Company> saveCompany(@RequestBody CompanyDTO companyDTO) {
//        Company savedCompany = companyService.saveCompany(companyDTO);
//        return ResponseEntity.ok(savedCompany);
//    }


// Update company
//    @PutMapping("/{id}")
//    public ResponseEntity<Company> updateCompany(@PathVariable("id") Long id, @RequestBody CompanyDTO companyDTO) {
//        Company updatedCompany = companyService.updateCompany(id, companyDTO);
//        if (updatedCompany != null) {
//            return ResponseEntity.ok(updatedCompany);
//        }
//        return ResponseEntity.status(500).body(null);  // If update fails
//    }



//
