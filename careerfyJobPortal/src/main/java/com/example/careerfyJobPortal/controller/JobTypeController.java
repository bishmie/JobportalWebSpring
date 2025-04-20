package com.example.careerfyJobPortal.controller;

import com.example.careerfyJobPortal.dto.JobTypeDtO;
import com.example.careerfyJobPortal.dto.ResponseDTO;
import com.example.careerfyJobPortal.entity.JobType;
import com.example.careerfyJobPortal.repositry.JobRepositry;
import com.example.careerfyJobPortal.repositry.JobTypeRepository;
import com.example.careerfyJobPortal.service.JobTypeService;
import com.example.careerfyJobPortal.utility.ResponseUtil;
import com.example.careerfyJobPortal.utility.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobType")
public class JobTypeController {

    @Autowired
    private JobTypeService jobTypeService;

    @Autowired
    private JobTypeRepository jobTypeRepository;

    @PostMapping("/save")
    public ResponseEntity<ResponseUtil> saveJobType(@RequestBody JobTypeDtO jobTypeDTO) {
        System.out.println(jobTypeDTO);
        jobTypeService.saveJobType(jobTypeDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseUtil(VarList.OK,"jobType list retrieved",jobTypeDTO));

    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<JobType> updateJobType(@PathVariable("id") Long id,
                                                 @RequestBody JobTypeDtO jobTypeDtO) {

        JobType updateJobType = jobTypeService.updateJobType(id, jobTypeDtO);

        if (updateJobType != null) {
            return ResponseEntity.ok(updateJobType);
        }
        return ResponseEntity.status(500).body(null);  // If update fails

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobTypeDtO>> getAllJobTypes() {
        List<JobTypeDtO> jobTypes = jobTypeService.getAllJobTypes();
        return ResponseEntity.ok(jobTypes);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseUtil deleteJobType(@PathVariable ("id") Integer id){

        try {
            Optional<JobType> optionalJobType = jobTypeRepository.findById(Long.valueOf(id));
            if (optionalJobType.isPresent()) {
                boolean res = jobTypeService.deleteJobType(id);

                if (res) {
                    return new ResponseUtil(201, "Job Type Delete ok", null);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseUtil(500,"Job Type does not Delete",null);
    }


}





