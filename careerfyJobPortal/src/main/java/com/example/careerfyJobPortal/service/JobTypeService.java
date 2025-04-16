package com.example.careerfyJobPortal.service;

import com.example.careerfyJobPortal.dto.JobTypeDtO;
import com.example.careerfyJobPortal.entity.JobType;

import java.util.List;

public interface JobTypeService {
    void saveJobType(JobTypeDtO jobTypeDTO);

    JobType updateJobType(Long id, JobTypeDtO jobTypeDTO);

    List<JobTypeDtO> getAllJobTypes();

    boolean deleteJobType(Integer id);
}
