package com.example.careerfyJobPortal.service;

import com.example.careerfyJobPortal.dto.JobApplicationDTO;
import com.example.careerfyJobPortal.dto.JobApplyDto;
import com.example.careerfyJobPortal.entity.JobApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JobApplicationService {
    int saveJobApplication(JobApplicationDTO jobApplicationDTO, MultipartFile file, String uploadDir) throws IOException;

    JobApplication updateStatus(Long id, String newStatus);

    List<JobApplyDto> getApplicationsByEmployerId(Long employerId);

//    JobApplication saveJobApplication(JobApplicationDTO jobApplicationDTO);
}

