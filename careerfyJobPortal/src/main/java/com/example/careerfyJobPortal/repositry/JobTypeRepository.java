package com.example.careerfyJobPortal.repositry;

import com.example.careerfyJobPortal.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {


}
