package com.example.careerfyJobPortal.service;

import com.example.careerfyJobPortal.dto.JobDTO;
import com.example.careerfyJobPortal.dto.JobsDto;
import com.example.careerfyJobPortal.entity.Job;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JobService {

//    Job updateJob(Long id, JobDTO jobDTO);

    List<JobDTO> getAllJobs();

    List<JobsDto> getAllForJobCard();

    JobDTO getJobById(Long jobId);

    List<JobDTO> getFromEmployerId(Long employerId);

    boolean deleteJobById(Long id);

    Long getJobCountByEmployer(Long employerId);

    Long getTotalJobCount();


//    List<Job> findJobsByEmployerId(Long employerId);

//    int getJobsCountByUserId(String userId);

//    Map<String, Object> getJobWithCompany(Long id);

}
