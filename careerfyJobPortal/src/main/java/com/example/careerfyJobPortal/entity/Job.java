package com.example.careerfyJobPortal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description; // Job Description

    @Lob
    private String responsibilities; // Key Responsibilities

    @Lob
    private String experience; // Experience needed

    private String location;
    private String type; // Full-time, Part-time, Remote
    private String salary;

    @ManyToOne
    @JoinColumn(name = "job_type_id")
    private JobType jobType;
    private String deadline;

    private String CompanyName;
//    @NotBlank(message = "email is null")
//    @Email(message = "invalid Email")
    private String email; // Company Email ekak
    private String companyDescription;
    private String logo;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User employer;



    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<JobApplication> applications;

    public Job(Long id, String title, String description, String responsibilities, String experience, String location, String type, String salary, JobType jobType, String deadline, String companyName, String email, String companyDescription, String logo, User employer, List<JobApplication> applications) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.responsibilities = responsibilities;
        this.experience = experience;
        this.location = location;
        this.type = type;
        this.salary = salary;
        this.jobType = jobType;
        this.deadline = deadline;
        this.CompanyName = companyName;
        this.email = email;
        this.companyDescription = companyDescription;
        this.logo = logo;
        this.employer = employer;
        this.applications = applications;
    }

    public Job() {
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

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
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

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
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

    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public List<JobApplication> getApplications() {
        return applications;
    }

    public void setApplications(List<JobApplication> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", responsibilities='" + responsibilities + '\'' +
                ", experience='" + experience + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", salary=" + salary +
                ", jobType=" + jobType +
                ", deadline=" + deadline +
                ", CompanyName='" + CompanyName + '\'' +
                ", email='" + email + '\'' +
                ", companyDescription='" + companyDescription + '\'' +
                ", logo='" + logo + '\'' +
                ", employer=" + employer +
                ", applications=" + applications +
                '}';
    }
}


