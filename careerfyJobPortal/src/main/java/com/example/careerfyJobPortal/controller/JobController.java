package com.example.careerfyJobPortal.controller;

import com.example.careerfyJobPortal.dto.JobDTO;
import com.example.careerfyJobPortal.dto.JobsDto;
import com.example.careerfyJobPortal.dto.ResponseDTO;
import com.example.careerfyJobPortal.service.JobService;
import com.example.careerfyJobPortal.service.serviceImpl.JobServiceImpl;
import com.example.careerfyJobPortal.utility.JwtUtil;
import com.example.careerfyJobPortal.utility.VarList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@Validated
public class JobController {
    @Value("./uploads")
    private String uploadDir;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobServiceImpl jobServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('EMPLOYER')")

    public ResponseEntity<ResponseDTO> createJob(
            @RequestPart("job") String jobDtoJson,
            @RequestPart("files") MultipartFile file) {

        try {
            // Log received data
            System.out.println("Raw JSON received: " + jobDtoJson);
            System.out.println("File received: " + file.getOriginalFilename());

            // Configure ObjectMapper to ignore unknown properties
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Convert JSON string to JobDTO object
            JobDTO jobDTO = objectMapper.readValue(jobDtoJson, JobDTO.class);
            System.out.println("Successfully parsed to DTO: " + jobDTO);

            // Save job with file
            int result = jobServiceImpl.saveJob(jobDTO, file);

            if (result == VarList.Created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(HttpStatus.CREATED.value(), "Job created successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Error creating job"));
            }
        } catch (Exception e) {
            System.err.println("General Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }





    @GetMapping("/getAll")
    public List<JobDTO> getAllJobs() {
        return jobService.getAllJobs();
    }


//    @GetMapping("/getAllForJobCards/{id}")
//    public ResponseEntity<?> getJobWithCompany(@PathVariable Long id) {
//        Map<String, Object> response = jobService.getJobWithCompany(id);
//
//        if (response != null) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job Not Found");
//        }
//    }


    @GetMapping("/getAllForJobCards")
    public List<JobsDto> getAllJobsAttr() {
        // Return the list of JobDTOs containing only title and responsibilities
        return jobService.getAllForJobCard();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(null)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("/get/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId) {
        JobDTO job = jobService.getJobById(jobId);
        return job == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(job);
    }


//    @GetMapping("/count/{userId}")
//    public ResponseEntity<Integer> getJobsCount(@PathVariable("userId") String userId) {
//        int jobCount = jobService.getJobsCountByUserId(userId);
//        return ResponseEntity.ok(jobCount);  // Return the job count
//    }


    @GetMapping("/jobFromEmployerId/{employerId}")
    public ResponseEntity<List<JobDTO>> getJobsByEmployerId(
            @PathVariable Long employerId) {

        List<JobDTO> jobs = jobService.getFromEmployerId(employerId);

        if (jobs.isEmpty()) {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
        return ResponseEntity.ok(jobs); // HTTP 200
    }




//    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ResponseDTO> updateJob(
//            @RequestPart("job") String jobDtoJson,
//            @RequestPart("files") MultipartFile file) {
//
//        try {
//            // Log received data
//            System.out.println("Raw JSON received: " + jobDtoJson);
//            System.out.println("File received: " + file.getOriginalFilename());
//
//            // Configure ObjectMapper to ignore unknown properties
//            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//            // Convert JSON string to JobDTO object
//            JobDTO jobDTO = objectMapper.readValue(jobDtoJson, JobDTO.class);
//            System.out.println("Successfully parsed to DTO: " + jobDTO);
//
//            // Update job with file
//            int result = jobServiceImpl.updateJob(jobDTO, file);
//
//            if (result == VarList.Updated) {
//                return ResponseEntity.status(HttpStatus.OK)
//                        .body(new ResponseDTO(HttpStatus.OK.value(), "Job updated successfully"));
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Error updating job"));
//            }
//        } catch (Exception e) {
//            System.err.println("General Error: " + e.getMessage());
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
//        }
//    }







    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        try {
            jobService.deleteJobById(id);
            return ResponseEntity.ok().build(); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", e.getMessage())); // Error message
        }
    }

//
//    @PutMapping("/update")
//    public ResponseEntity<ResponseDTO> updateCompany(
//            @RequestPart("company") @Valid String companyData,
//            @RequestPart(value = "files", required = false) List<MultipartFile> files) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JobDTO jobDTO;
//
//            try {
//                jobDTO = objectMapper.readValue(companyData, JobDTO.class);
//            } catch (JsonProcessingException e) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ResponseDTO(VarList.Bad_Request, "Invalid JSON structure", e.getMessage()));
//            }
//
//            int result = jobService.updateJob(jobDTO, files);
//
//            return switch (result) {
//                case VarList.Created -> ResponseEntity.status(HttpStatus.CREATED)
//                        .body(new ResponseDTO(VarList.Created, "Job updated successfully", jobDTO));
//
//                case VarList.Not_Found -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ResponseDTO(VarList.Not_Found, "Job not found!", jobDTO));
//
//                case VarList.Not_Acceptable -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                        .body(new ResponseDTO(VarList.Not_Acceptable, "Job URL already exists!", jobDTO));
//
//                default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                        .body(new ResponseDTO(VarList.Bad_Gateway, "An unknown error occurred", jobDTO));
//            };
//
//        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error: ", ex.getMessage()));
//        }
//    }



    @GetMapping("/count/{employerId}")
    @PreAuthorize("hasRole('EMPLOYER')")

    public ResponseEntity<Long> getJobCount(@PathVariable Long employerId) {
        Long count = jobService.getJobCountByEmployer(employerId);
        return ResponseEntity.ok(count);
    }


    @GetMapping("/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalJobCount() {
        return ResponseEntity.ok(jobService.getTotalJobCount());
    }

}
