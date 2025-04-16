package com.example.careerfyJobPortal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CompanyDTO {

    private Long id;
    private String name;
    @NotBlank(message = "email is null")
    @Email(message = "invalid Email")
    private String email;
    private String companyDescription;
    private String logo; // Logo as image URL or file path
    private List<JobDTO> jobs;

    public CompanyDTO() {
    }

    public CompanyDTO(Long id, String name, String email, String companyDescription, String logo, List<JobDTO> jobs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyDescription = companyDescription;
        this.logo = logo;
        this.jobs = jobs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotBlank(message = "email is null") @Email(message = "invalid Email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "email is null") @Email(message = "invalid Email") String email) {
        this.email = email;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", logo='" + logo + '\'' +
                ", jobs=" + jobs +
                '}';
    }
}
