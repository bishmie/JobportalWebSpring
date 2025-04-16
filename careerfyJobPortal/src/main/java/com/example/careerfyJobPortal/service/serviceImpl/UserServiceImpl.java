package com.example.careerfyJobPortal.service.serviceImpl;

import com.example.careerfyJobPortal.dto.ResponseDTO;
import com.example.careerfyJobPortal.dto.UserDto;
import com.example.careerfyJobPortal.entity.AccountType;
import com.example.careerfyJobPortal.entity.User;
import com.example.careerfyJobPortal.execptionHandling.InvalidPasswordException;
import com.example.careerfyJobPortal.repositry.UserRepositry;
import com.example.careerfyJobPortal.service.UserService;
import com.example.careerfyJobPortal.utility.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService,UserService {
    @Autowired
    private UserRepositry userRepositry;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private JavaMailSender mailSender;

    private Map<String, String> otpStorage = new HashMap<>();

    public String forgotPassword(String email) {
        User user = userRepositry.findByEmail(email);
        if (user == null) {
            return "User not found!";
        }
        String otp = generateOTP();
        otpStorage.put(email, otp);
        sendEmail(email, otp);
        return "OTP sent to your email";
    }

    public String verifyOtp(String email, String otp) {
        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp)) {
            otpStorage.remove(email);
            return "OTP verified. You can reset your password.";
        }
        return "Invalid OTP";
    }

    public String resetPassword(String email, String newPassword) {
        User user = userRepositry.findByEmail(email);
        if (user == null) {
            return "User not found!";

        }

        // BCrypt encoder object hadanna
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // New password eka hash karanna
        String hashedPassword = encoder.encode(newPassword);

        // Hashed password eka database ekata save karanna
        user.setPassword(hashedPassword);
        userRepositry.save(user);
        return "Password updated successfully!";
    }

    private String generateOTP() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset OTP");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }


//    @Override
//    public boolean registerUser(UserDto userDto) {
//
//        Optional<Optional<User>> optionalUser = Optional.ofNullable(userRepositry.findByEmail(userDto.getEmail()));
//        if (optionalUser.isPresent()) {
//            return false;
//        }
//
////        if (userRepositry.existsById(userDto.getId())) {
////            return false;
////        }
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        System.out.println("Raw Password: " + userDto.getPassword());
//
//        User user =modelMapper.map(userDto, User.class);
//
//        userRepositry.save(user);
//
//        return true;
//
//    }

//    @Override
//    public boolean loginUser(UserloggingDto userloggingDto) {
//        Optional<Optional<User>> optionalUser = Optional.ofNullable(userRepositry.findByEmail(userloggingDto.getEmail()));
//        if (optionalUser.isPresent()) {
//            Optional<User> user = optionalUser.get();
//            // Compare the raw password with the encrypted one in the database
//            return passwordEncoder.matches(userloggingDto.getPassword(), user.get().getPassword());
//        }
//        return false; // User not found
//    }




    @Override
    public List<UserDto> getAllUsers() {
        return modelMapper.map(userRepositry.findAll(), new TypeToken<List<UserDto>>(){}.getType());
    }

    @Override
    public Long countUsers() {

        return userRepositry.countUsers();
    }

    public UserDto loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositry.findByEmail(username);
        return modelMapper.map(user,UserDto.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getAccountType().toString()));
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositry.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Use email as the username
                user.getPassword(), // Password
                getAuthority(user) // Convert role to GrantedAuthority
        );
    }


    @Override
    public int addUser(UserDto userDTO) {
        if (userRepositry.existsByEmail(userDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            // Map UserDTO to User entity
            User user = modelMapper.map(userDTO, User.class);

            // Encrypt the password
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setActive(true);

            // Set default role if not provided
            if (user.getAccountType() == null) {
                user.setAccountType(AccountType.CANDIDATE); // Default role
            }

//            // Set default verification status if not provided
//            if (user.getVerificationStatus() == null) {
//                user.setVerificationStatus("Pending"); // Example default value
//            }
            userRepositry.save(user);
            // Return success response
            return VarList.Created;
        }
    }



    @Override
    public List<UserDto> getAllCandidates() {
        List<User> candidates = userRepositry.findByAccountType(AccountType.CANDIDATE);

        return candidates.stream()
                .map(user -> modelMapper.map(user, UserDto.class)) // Convert User to UserDto
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllEmployers() {
        List<User> employer = userRepositry.findByAccountType(AccountType.EMPLOYER);

        return employer.stream()
                .map(user -> modelMapper.map(user, UserDto.class)) // Convert User to UserDto
                .collect(Collectors.toList());
    }

//    @Override
//    public void deactivateUser(UserDto userDTO) {
//        if (userRepositry.existsByEmail(userDTO.getEmail())) {
//            User user = userRepositry.findByEmail(userDTO.getEmail());
//            user.setActive(false);
//            userRepositry.save(user);
//        }
//    }

    @Override
    public User findByEmail(String email) {
        return userRepositry.findByEmail(email);
    }

    @Override
    public boolean deactivateUserByEmail(String email) {
        User user = userRepositry.findByEmail(email);
        if (user != null) {
            user.setActive(false);
            userRepositry.save(user);
            return true;
        } else {
            return false;
        }
    }





}
