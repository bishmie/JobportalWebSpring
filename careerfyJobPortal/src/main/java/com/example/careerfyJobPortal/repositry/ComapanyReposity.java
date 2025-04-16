package com.example.careerfyJobPortal.repositry;

import com.example.careerfyJobPortal.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComapanyReposity extends JpaRepository<Company,Integer> {

}
