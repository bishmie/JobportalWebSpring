package com.example.careerfyJobPortal.dto;

public class JobApplyDto {


        private Long id;
        private String username;
        private String email;
        private String resume;
        private String status;
        private String jobTitle; // optional: for frontend display
        private  String companyName;

    public JobApplyDto(Long id, String username, String email, String resume, String status, String jobTitle, String companyName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.resume = resume;
        this.status = status;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
    }

    public JobApplyDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JobApplyDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", resume='" + resume + '\'' +
                ", status='" + status + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
