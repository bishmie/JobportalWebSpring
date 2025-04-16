package com.example.careerfyJobPortal.dto;

import com.example.careerfyJobPortal.entity.JobApplication;

public class JobApplicationStatusDTO {
    private Long id;
    private String status;
    private String jobTitle;
    private String candidateName;
    
    // Constructors, getters, and setters
    public JobApplicationStatusDTO(JobApplication application) {
        this.id = application.getId();
        this.status = application.getStatus();
        this.jobTitle = application.getJob().getTitle();
        this.candidateName = application.getUser().getUsername();
    }

    public JobApplicationStatusDTO() {
    }

    public JobApplicationStatusDTO(Long id, String status, String jobTitle, String candidateName) {
        this.id = id;
        this.status = status;
        this.jobTitle = jobTitle;
        this.candidateName = candidateName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    @Override
    public String toString() {
        return "JobApplicationStatusDTO{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", candidateName='" + candidateName + '\'' +
                '}';
    }
}