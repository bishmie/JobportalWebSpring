package com.example.careerfyJobPortal.entity;

import jakarta.persistence.*;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String resume; // Resume URL or File Path
    private String status; // Pending, Approved, Rejected

    public JobApplication() {
    }

    public JobApplication(Long id, Job job, User user, String resume, String status) {
        this.id = id;
        this.job = job;
        this.user = user;
        this.resume = resume;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "JobApplication{" +
                "id=" + id +
                ", job=" + job +
                ", user=" + user +
                ", resume='" + resume + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
