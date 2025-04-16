package com.example.careerfyJobPortal.dto;


import java.util.List;

public class JobTypeDtO {

    private Long id;
    private String name;
    private List<JobDTO> jobs; // A list of JobDTOs


    public JobTypeDtO(Long id, String name, List<JobDTO> jobs) {
        this.id = id;
        this.name = name;
        this.jobs = jobs;
    }

    public JobTypeDtO() {
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

    public List<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "JobTypeDtO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jobs=" + jobs +
                '}';
    }
}

