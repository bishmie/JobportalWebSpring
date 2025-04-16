package com.example.careerfyJobPortal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @NotBlank(message = "email is null")
    @Email(message = "invalid Email")
    private String email; // Company Email ekak
    private String companyDescription;
    private String logo; // Logo (image URL or file path)

//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
//    private List<Job> jobs;

    public Company() {
    }

    public Company(Long id, String name, String email, String companyDescription, String logo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.companyDescription = companyDescription;
        this.logo = logo;
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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}

