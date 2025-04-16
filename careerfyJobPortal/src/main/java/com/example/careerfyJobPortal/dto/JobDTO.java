package com.example.careerfyJobPortal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


    public class JobDTO {
    private Long id;
    private String title;
    private String description;
    @NotBlank(message = "Responsibilities cannot be blank")
    @Size(min = 10, max = 1000, message = "Responsibilities must be between 10 and 1000 characters")
    private String responsibilities;
    @NotBlank(message = "Experiences cannot be blank")
    @Size(min = 10, max = 1000, message = "Experiences must be between 10 and 1000 characters")
    private String experience;
    private String location;
    private String type; // Full-time, Part-time, Remote
    private String salary;
    private Long jobTypeId;
    private String deadline;
    private String CompanyName;
//        @NotBlank(message = "Email cannot be null")
//        @Email(message = "Invalid email format")
    private String email;
    private String companyDescription;
    private String logo;
    private int employerId;


        public JobDTO() {
        }


        public JobDTO(Long id, String title, String description, String responsibilities, String experience, String location, String type, String salary, Long jobTypeId, String deadline, String companyName, String email, String companyDescription, String logo, int employerId) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.responsibilities = responsibilities;
            this.experience = experience;
            this.location = location;
            this.type = type;
            this.salary = salary;
            this.jobTypeId = jobTypeId;
            this.deadline = deadline;
            this.CompanyName = companyName;
            this.email = email;
            this.companyDescription = companyDescription;
            this.logo = logo;
            this.employerId = employerId;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public @NotBlank(message = "Responsibilities cannot be blank") @Size(min = 10, max = 1000, message = "Responsibilities must be between 10 and 1000 characters") String getResponsibilities() {
            return responsibilities;
        }

        public void setResponsibilities(@NotBlank(message = "Responsibilities cannot be blank") @Size(min = 10, max = 1000, message = "Responsibilities must be between 10 and 1000 characters") String responsibilities) {
            this.responsibilities = responsibilities;
        }

        public @NotBlank(message = "Experiences cannot be blank") @Size(min = 10, max = 1000, message = "Experiences must be between 10 and 1000 characters") String getExperience() {
            return experience;
        }

        public void setExperience(@NotBlank(message = "Experiences cannot be blank") @Size(min = 10, max = 1000, message = "Experiences must be between 10 and 1000 characters") String experience) {
            this.experience = experience;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public Long getJobTypeId() {
            return jobTypeId;
        }

        public void setJobTypeId(Long jobTypeId) {
            this.jobTypeId = jobTypeId;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
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

        public int getEmployerId() {
            return employerId;
        }

        public void setEmployerId(int employerId) {
            this.employerId = employerId;
        }

        @Override
        public String toString() {
            return "JobDTO{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", responsibilities='" + responsibilities + '\'' +
                    ", experience='" + experience + '\'' +
                    ", location='" + location + '\'' +
                    ", type='" + type + '\'' +
                    ", salary=" + salary +
                    ", jobTypeId=" + jobTypeId +
                    ", deadline=" + deadline +
                    ", CompanyName='" + CompanyName + '\'' +
                    ", email='" + email + '\'' +
                    ", companyDescription='" + companyDescription + '\'' +
                    ", logo='" + logo + '\'' +
                    ", employerId=" + employerId +
                    '}';
        }
    }

