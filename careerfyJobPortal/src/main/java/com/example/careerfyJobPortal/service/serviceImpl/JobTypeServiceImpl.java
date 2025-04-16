package com.example.careerfyJobPortal.service.serviceImpl;

import com.example.careerfyJobPortal.dto.JobTypeDtO;
import com.example.careerfyJobPortal.entity.JobType;
import com.example.careerfyJobPortal.repositry.JobTypeRepository;
import com.example.careerfyJobPortal.service.JobTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobTypeServiceImpl implements JobTypeService {

    @Autowired
    private JobTypeRepository jobTypeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveJobType(JobTypeDtO jobTypeDTO) {
        try{
            jobTypeRepository.save(modelMapper.map(jobTypeDTO, JobType.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JobType updateJobType(Long id, JobTypeDtO jobTypeDTO) {
//        Optional<JobType> existingJobType = jobTypeRepository.findById(id);
//
//        if (existingJobType.isPresent()) {
//            JobType jobType = existingJobType.get();
//            jobType.setName(jobTypeDTO.getName()); // Updating name
//            return jobTypeRepository.save(jobType);
//        } else {
//            throw new RuntimeException("JobType Not Found!");
//        }
        // }
        JobType existingJobType = jobTypeRepository.findById(id).orElse(null);

        if (existingJobType != null) {


            existingJobType.setName(jobTypeDTO.getName());


            return jobTypeRepository.save(existingJobType);


        } else {
            throw new RuntimeException("Job type not found");
        }
    }   

    @Override
    public List<JobTypeDtO> getAllJobTypes() {
        List<JobType> jobTypes = jobTypeRepository.findAll();

        return jobTypes.stream().map(jobType ->
                new JobTypeDtO(jobType.getId(), jobType.getName(), null) // Convert entity to DTO
        ).collect(Collectors.toList());
    }

    @Override
    public boolean deleteJobType(Integer id) {

        try{
            Optional<JobType> optionalJobType = jobTypeRepository.findById(Long.valueOf(id));

            if (optionalJobType.isPresent()) {
                jobTypeRepository.deleteById(Long.valueOf(id));
                return true;
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
        }
    }



