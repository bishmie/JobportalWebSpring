package com.example.careerfyJobPortal.service;

import com.example.careerfyJobPortal.dto.ResponseDTO;
import com.example.careerfyJobPortal.dto.UserDto;
import com.example.careerfyJobPortal.dto.UserloggingDto;
import com.example.careerfyJobPortal.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
//    public boolean registerUser(UserDto userDto) throws Exception;
//
//    boolean loginUser(UserloggingDto userloggingDto);

    List<UserDto> getAllUsers();

    Long countUsers();

    int addUser(UserDto userDTO);

    UserDetails loadUserByUsername(String email);

    List<UserDto> getAllCandidates();

    List<UserDto> getAllEmployers();

//    void deactivateUser(UserDto userDTO);

    User findByEmail(String email);

    boolean deactivateUserByEmail(String email);


//    User getUserById(String userId);


//    int addUser(UserDto userDTO);

//    UserDetails loadUserByUsername(String email);


//    int saveUser(UserDto userDTO);
//    UserDto searchUser(String username);
//
//    int loginUser(@Valid UserDto userDTO);



}
