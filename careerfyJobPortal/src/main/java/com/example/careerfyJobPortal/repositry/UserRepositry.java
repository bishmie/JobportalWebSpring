package com.example.careerfyJobPortal.repositry;

import com.example.careerfyJobPortal.entity.AccountType;
import com.example.careerfyJobPortal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositry extends JpaRepository<User, Integer> {
//  public Optional<User> findByEmail(String email);

  @Query("SELECT COUNT(u) FROM User u")
  Long countUsers();

//  User findByEmail(String email);

  User findByEmail(String email); // Find user by email
  boolean existsByEmail(String email);

  List<User> findByAccountType(AccountType accountType);


  Long countByIsActiveTrue();

//  Optional<User> findByEmails(String email);
//  User findByUserId(String userId);


}


