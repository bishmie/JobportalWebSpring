package com.example.careerfyJobPortal.repositry;

import com.example.careerfyJobPortal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepositry extends JpaRepository<Job, Long> {

    Optional<Job> findById(Long id);

//    int countByUserId(String userId);

    List<Job> findByEmployerId(Long employerId);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.employer.id = :employerId")
    Long countJobsByEmployerId(@Param("employerId") Long employerId);


    @Query("SELECT COUNT(j) FROM Job j")
    Long getTotalJobCount();
}
