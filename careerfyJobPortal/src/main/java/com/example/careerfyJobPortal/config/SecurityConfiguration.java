package com.example.careerfyJobPortal.config;

import com.example.careerfyJobPortal.service.UserService;
import com.example.careerfyJobPortal.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                /*.cors()
                .and()*/
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/authenticate",
                                "/users/register",
                                "/jobType/getAll",
                                "/users/getAll",
                                "users/totalUsers",
                                "/jobType/save",
                                "jobType/update/{id}",
                                "jobType/delete/{id}",
                                "users/getAllCandidates",
                                "users/getAllEmployers",
                                "users/deactivate",
                                "users/getUserIdFromToken",
                                "/jobs/getAllForJobCards",
                                "/jobs/getAll",
//                                "/jobs/add",
                                "/jobs/images/{filename}",
                                "/jobs/update/{id}",
                                  "/jobs/delete/{id}",
                                  "/job-applications/fromEmployer/{employerId}",
//                                 "users/getByEmail?email/{encodeURIComponent(userEmail)",
//                                "jobs/get",
                                "users/getByEmail",
                                "api/v1/auth/forgot-password",
                                "api/v1/auth/reset-password",
                                "api/v1/auth/verify-otp",
                                "/jobs/get/{jobId}",
                                "/job-applications/{id}/status",
                                "jobs/jobFromEmployerId/{employerId}",
                                "/job-applications/download-resume/{id}",
                                "/users/getUserDetails/${userId}",
                                "/users/change-password",
                                "/users/deactivate/{email}",
                                 "/users/active-count",
                                 "jobs/count",
//                                "/jobs/count/{employerId}",
                                "/uploads/**",
                                "/api/v1/auth/refreshToken",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll() // Allow these endpoints without authentication
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(Customizer.withDefaults())
                .build();
    }


//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }


}
