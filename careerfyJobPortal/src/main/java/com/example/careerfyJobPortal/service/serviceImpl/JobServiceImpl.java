package com.example.careerfyJobPortal.service.serviceImpl;

import com.example.careerfyJobPortal.dto.JobDTO;
import com.example.careerfyJobPortal.dto.JobsDto;
import com.example.careerfyJobPortal.entity.Job;
import com.example.careerfyJobPortal.entity.JobType;
import com.example.careerfyJobPortal.entity.User;

import com.example.careerfyJobPortal.repositry.ComapanyReposity;
import com.example.careerfyJobPortal.repositry.JobRepositry;
import com.example.careerfyJobPortal.repositry.JobTypeRepository;
import com.example.careerfyJobPortal.repositry.UserRepositry;
import com.example.careerfyJobPortal.service.JobService;
import com.example.careerfyJobPortal.utility.VarList;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    @Value("./uploads")
    private String uploadDir;

    @Autowired
    private JobRepositry jobRepository;

    @Autowired
    private ComapanyReposity companyRepository;

    @Autowired
    private UserRepositry userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JobTypeRepository jobTypeRepository;

//    public Job saveJob(JobDTO jobDTO) {
//        Job job = new Job();
//        job.setTitle(jobDTO.getTitle());
//        job.setDescription(jobDTO.getDescription());
//        job.setResponsibilities(jobDTO.getResponsibilities());
//        job.setExperience(jobDTO.getExperience());
//        job.setLocation(jobDTO.getLocation());
//        job.setType(jobDTO.getType());
//        job.setSalary(jobDTO.getSalary());
//        job.setDeadline(jobDTO.getDeadline());
//        job.setCompanyName(jobDTO.getCompanyName());
//        jobDTO.setEmail(job.getEmail());
//        jobDTO.setCompanyDescription(job.getCompanyDescription());
//        jobDTO.setLogo(job.getLogo());
//
////        Company company = companyRepository.findById(Math.toIntExact(jobDTO.getCompanyId())).orElse(null);
//        User employer = userRepository.findById(Math.toIntExact(jobDTO.getEmployerId())).orElse(null);
//        JobType jobType = jobTypeRepository.findById(jobDTO.getJobTypeId()).orElse(null);
//
////        job.setCompany(company);
//        job.setEmployer(employer);
//        job.setJobType(jobType);
//
//        return jobRepository.save(job);
//    }


    public int saveJob(JobDTO jobDTO,MultipartFile file) throws IOException {

        Job job = modelMapper.map(jobDTO, Job.class);

        String filename = UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Path filePath = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), filePath);

        // Set Employer from DTO
        User employer = userRepository.findById(jobDTO.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        job.setEmployer(employer);
        job.setLogo(filename);
        jobRepository.save(job);

        return VarList.Created;
    }





    @Override
    public List<JobDTO> getAllJobs() {
        try{
            return modelMapper.map(jobRepository.findAll(), new TypeToken<List<JobDTO>>() {}.getType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<JobsDto> getAllForJobCard() {
        return jobRepository.findAll()
                .stream()
                .map(job -> new JobsDto(job.getTitle(), job.getDescription(),
                        job.getLocation(),
                        job.getType(),
                        job.getCompanyName(),
                        job.getLogo()))
                .collect(Collectors.toList());
    }


//    public Map<String, Object> getJobWithCompany(Long id) {
//        Optional<Job> jobOptional = jobRepository.findById(id);
//        if (jobOptional.isPresent()) {
//            Job job = jobOptional.get();
//            Map<String, Object> response = new HashMap<>();
//            response.put("job", job);
//
//            Map<String, Object> companyData = new HashMap<>();
//            companyData.put("name", job.getCompany().getName());
//            companyData.put("logo", job.getCompany().getLogo()); // Logo URL eka
//
//            response.put("company", companyData);
//            return response;
//        } else {
//            return null; // Job not found
//        }
//    }



@Override
    public JobDTO getJobById(Long jobId) {
        Optional<Job> have =  jobRepository.findById(jobId);
        if (have.isPresent()) {
            return modelMapper.map(have.get(), JobDTO.class);
        }
        return null;
    }

    @Override
    public List<JobDTO> getFromEmployerId(Long employerId) {
        List<Job> jobs = jobRepository.findByEmployerId(employerId);
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }


//
//    public int getJobsCountByUserId(String userId) {
//        return jobRepository.countByUserId(userId);  // Assuming repository method that fetches count
//    }



    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

   @Override
    public Long getJobCountByEmployer(Long employerId) {
        return jobRepository.countJobsByEmployerId(employerId);
    }

    @Override
    public Long getTotalJobCount() {
        return jobRepository.getTotalJobCount();
    }


}
