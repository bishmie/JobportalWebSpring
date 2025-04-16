package com.example.careerfyJobPortal.repositry;

import com.example.careerfyJobPortal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Get all applications for a specific employer's job
    List<JobApplication> findByJob_Employer_Id(Long employerId);}
