package com.example.careerfyJobPortal.dto;

import com.example.careerfyJobPortal.entity.AccountType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



public class UserDto {
    private int id;
    @NotBlank(message = "username is null")
    private String username;
    @NotBlank(message = "email is null")
    @Email(message = "invalid Email")
    private String email;
    @NotBlank(message = "password is null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+]{8,}")
    private String password;
    private AccountType accountType;
    private boolean isActive;

    public UserDto(int id, String username, String email, String password, AccountType accountType, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    public UserDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "username is null") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "username is null") String username) {
        this.username = username;
    }

    public @NotBlank(message = "email is null") @Email(message = "invalid Email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "email is null") @Email(message = "invalid Email") String email) {
        this.email = email;
    }

    public @NotBlank(message = "password is null") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+]{8,}") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "password is null") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+]{8,}") String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accountType=" + accountType +
                ", isActive=" + isActive +
                '}';
    }
}
