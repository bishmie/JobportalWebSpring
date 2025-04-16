package com.example.careerfyJobPortal.dto;

public class JobApplicationDTO {

    private Long id;
    private Long jobId;
    private Long userId;
    private String resume; // Resume URL or File Path
    private String status; // Pending, Approved, Rejected

    public JobApplicationDTO() {
    }

    public JobApplicationDTO(Long id, Long jobId, Long userId, String resume, String status) {
        this.id = id;
        this.jobId = jobId;
        this.userId = userId;
        this.resume = resume;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "JobApplicationDTO{" +
                "id=" + id +
                ", jobId=" + jobId +
                ", userId=" + userId +
                ", resume='" + resume + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
