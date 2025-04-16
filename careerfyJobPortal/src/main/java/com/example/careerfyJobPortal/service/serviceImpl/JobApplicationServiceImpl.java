package com.example.careerfyJobPortal.service.serviceImpl;

import com.example.careerfyJobPortal.dto.JobApplicationDTO;
import com.example.careerfyJobPortal.dto.JobApplyDto;
import com.example.careerfyJobPortal.entity.Job;
import com.example.careerfyJobPortal.entity.JobApplication;
import com.example.careerfyJobPortal.entity.User;
import com.example.careerfyJobPortal.repositry.JobApplicationRepository;
import com.example.careerfyJobPortal.repositry.JobRepositry;
import com.example.careerfyJobPortal.repositry.UserRepositry;
import com.example.careerfyJobPortal.service.JobApplicationService;
import com.example.careerfyJobPortal.utility.VarList;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Value("./uploads")
    private String uploadDir;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepositry jobRepository;

    @Autowired
    private UserRepositry userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

//    public JobApplication saveJobApplication(JobApplicationDTO jobApplicationDTO) {
//        Job job = jobRepository.findById(jobApplicationDTO.getJobId())
//                .orElseThrow(() -> new RuntimeException("Job not found"));
//
//        User user = userRepository.findById(Math.toIntExact(jobApplicationDTO.getUserId()))
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        JobApplication jobApplication = new JobApplication();
//        jobApplication.setResume(jobApplicationDTO.getResume());
//        jobApplication.setStatus(jobApplicationDTO.getStatus());
//        jobApplication.setJob(job);
//        jobApplication.setUser(user);
//
//        return jobApplicationRepository.save(jobApplication);
//    }





    @Override
    public int saveJobApplication(JobApplicationDTO jobApplicationDTO, MultipartFile file, String uploadDir) throws IOException {
        JobApplication jobApplication = new JobApplication();

        // Set job & user
        Job job = jobRepository.findById(jobApplicationDTO.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User user = userRepository.findById(Math.toIntExact(jobApplicationDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        jobApplication.setJob(job);
        jobApplication.setUser(user);

        // File save
        String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        jobApplication.setResume(filename);

        // Set default status
        jobApplication.setStatus("pending");

        // Save
        jobApplicationRepository.save(jobApplication);

        return VarList.Created;
    }




    @Transactional
    public JobApplication updateStatus(Long applicationId, String newStatus) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Job application not found"));

        String oldStatus = application.getStatus();
        application.setStatus(newStatus);
        JobApplication savedApplication = jobApplicationRepository.save(application);

        // Send email notification if status changed to Approved or Rejected
        if (!newStatus.equals(oldStatus) &&
                ("Approved".equals(newStatus) || "Rejected".equals(newStatus))) {
            sendStatusNotification(savedApplication);
        }

        return savedApplication;
    }
    private void sendStatusNotification(JobApplication application) {
        String email = application.getUser().getEmail();
        String subject = "Your Job Application Status Update";
        String body;

        if ("Approved".equals(application.getStatus())) {
            body = "Congratulations! You have been selected for the job: " +
                    application.getJob().getTitle();
        } else {
            body = "Thank you for your application. We regret to inform you that " +
                    "you have not been selected for the job: " + application.getJob().getTitle() +
                    "\n\nWe encourage you to apply for other positions that match your skills.";
        }

        emailService.sendEmail(email, subject, body);
    }


    @Override
    public List<JobApplyDto> getApplicationsByEmployerId(Long employerId) {
        List<JobApplication> applications = jobApplicationRepository.findByJob_Employer_Id(employerId);

        return applications.stream().map(app -> {
            JobApplyDto dto = new JobApplyDto();
            dto.setId(app.getId());
            dto.setUsername(app.getUser().getUsername());
            dto.setEmail(app.getUser().getEmail());
            dto.setResume(app.getResume());
            dto.setStatus(app.getStatus());
            dto.setJobTitle(app.getJob().getTitle()); // only if needed
            dto.setCompanyName(app.getJob().getCompanyName());
            return dto;
        }).collect(Collectors.toList());
    }

}
