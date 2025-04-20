package com.example.careerfyJobPortal.controller;

import com.example.careerfyJobPortal.dto.*;
import com.example.careerfyJobPortal.entity.JobApplication;
import com.example.careerfyJobPortal.repositry.JobApplicationRepository;
import com.example.careerfyJobPortal.service.JobApplicationService;
import com.example.careerfyJobPortal.utility.VarList;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/job-applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("./uploads")
    private String uploadDir;

    @Autowired
    private ObjectMapper objectMapper;


//    @PostMapping("/add")
//    public JobApplication createJobApplication(@RequestBody JobApplicationDTO jobApplicationDTO) {
//        return jobApplicationService.saveJobApplication(jobApplicationDTO);
//    }


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO> saveApplication(
            @RequestPart("jobApplication") String jobApplicationDtoJson,
            @RequestPart("files") MultipartFile file) {

        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JobApplicationDTO jobApplicationDTO = objectMapper.readValue(jobApplicationDtoJson, JobApplicationDTO.class);

            int result = jobApplicationService.saveJobApplication(jobApplicationDTO, file, uploadDir);

            if (result == VarList.Created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(HttpStatus.CREATED.value(), "Job application saved successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Error saving application"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error: " + e.getMessage()));
        }
    }




//    @PostMapping("/update-status")
//    public JobApplication updateJobApplicationStatus(@RequestBody JobApplicationDTO jobApplicationDTO) {
//        return jobApplicationService.saveJobApplication(jobApplicationDTO);
//    }

//    @GetMapping("/download/{filename}")
//    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
//        try {
//            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if(resource.exists()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                        .body(resource);
//
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (MalformedURLException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }




    @PatchMapping("/{id}/status")
    public ResponseEntity<JobApplicationStatusDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {

        JobApplication application = jobApplicationService.updateStatus(id, request.getNewStatus());
        return ResponseEntity.ok(new JobApplicationStatusDTO(application));
    }
    // Inner class for request body
    public static class StatusUpdateRequest {
        private String newStatus;

        // Getters and Setters
        public String getNewStatus() {
            return newStatus;
        }
        public void setNewStatus(String newStatus) {
            this.newStatus = newStatus;
        }
    }



    @GetMapping("/fromEmployer/{employerId}")
    public ResponseEntity<List<JobApplyDto>> getApplicationsFromEmployer(@PathVariable Long employerId) {
        List<JobApplyDto> apps = jobApplicationService.getApplicationsByEmployerId(employerId);

        if (apps.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(apps);
    }





//    @GetMapping("/download/{filename}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
//        try {
//            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//
//            if (!resource.exists()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            String contentType = "application/octet-stream";
//            String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
//                    .body(resource);
//
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }




    @GetMapping("/download-resume/{id}")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {
        Optional<JobApplication> optional = jobApplicationRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        JobApplication jobApp = optional.get();
        String fileName = jobApp.getResume(); // e.g., "resume123.pdf"

        Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
