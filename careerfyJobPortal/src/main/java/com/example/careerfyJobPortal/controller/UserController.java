package com.example.careerfyJobPortal.controller;


import com.example.careerfyJobPortal.config.JwtFilter;
import com.example.careerfyJobPortal.dto.*;
import com.example.careerfyJobPortal.entity.User;
import com.example.careerfyJobPortal.execptionHandling.InvalidPasswordException;
import com.example.careerfyJobPortal.repositry.UserRepositry;
import com.example.careerfyJobPortal.service.UserService;
import com.example.careerfyJobPortal.utility.JwtUtil;
import com.example.careerfyJobPortal.utility.ResponseUtil;
import com.example.careerfyJobPortal.utility.VarList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "*")
@Validated
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private UserRepositry userRepositry;

//    @PostMapping("/register")
//    public ResponseUtil registerUser(@RequestBody @Valid UserDto userDto) throws Exception {
//        boolean registerResult = userService.registerUser(userDto);
//
//        if (registerResult) {
//            return new ResponseUtil(201, "USER REGISTERED", null);
//
//        } else {
//            return new ResponseUtil(500, "USER DOES NOT REGISTERED", null);
//
//        }
//    }


//    @PostMapping("/login")
//    public ResponseUtil loginUser(@RequestBody @Valid UserloggingDto userloggingDto) throws Exception {
//        boolean loginResult = userService.loginUser(userloggingDto);
//
//        if (loginResult) {
//            return new ResponseUtil(201, "LOGIN SUCCESSFULLY", null);
//
//        } else {
//            return new ResponseUtil(500, "LOGIN FAILED", null);
//
//        }
//
//
//    }


    @GetMapping("getAll")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/totalUsers")
    public Long getTotalUsers() {
        return userService.countUsers();
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserDto userDTO) {
        System.out.println("register");
        System.out.println(userDTO.getEmail());
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getAccountType());
        try {
            int res = userService.addUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }


    @GetMapping("/getAllCandidates")
    public List<UserDto> getAllCandidates() {

        return userService.getAllCandidates();
    }

    @GetMapping("/getAllEmployers")
    public List<UserDto> getAllEmployers() {

        return userService.getAllEmployers();
    }


//    @PostMapping(value = "/deactivate")
////    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<ResponseDTO> deactivateUser(@RequestBody UserDto userDTO) {
//        userService.deactivateUser(userDTO);
//        return ResponseEntity.ok(new ResponseDTO(VarList.OK, "User Deactivated", null));
//    }

    @GetMapping("/getUserIdFromToken")
    public ResponseEntity<?> getUserId(@RequestHeader("Authorization") String token) {
        try {
            // **Step 1: Token Decode**
            String jwt = token.replace("Bearer ", ""); // Bearer prefix eka ain karanawa
            String userId = jwtFilter.extractUserId(jwt); // Token eken userId ganna

            // **Step 2: Response eka return karanawa**
            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
    }



    @GetMapping("/getByEmail")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> getUserIdByEmail(HttpServletRequest request) {
        // Extract the token from the Authorization header
        String token = request.getHeader("Authorization").substring(7);  // Remove "Bearer " from token
        String email = jwtFilter.extractEmailFromToken(token);  // Use a utility method to extract email from the JWT token

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email not found in token");
        }

        // Fetch user by email
        User user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(Collections.singletonMap("userId", user.getId()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PutMapping("/deactivate/{email}")
    public ResponseEntity<String> deactivateUser(@PathVariable String email) {
        boolean result = userService.deactivateUserByEmail(email);
        if (result) {
            return ResponseEntity.ok("User deactivated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @GetMapping("/active-count")
    public Long getActiveUserCount() {
        return userRepositry.countByIsActiveTrue();
    }


}
