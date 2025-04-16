package com.example.careerfyJobPortal.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "username is null")
    private String username;
    @NotBlank(message = "email is null")
    @Email(message = "invalid email")
    private String email;
    @NotBlank(message = "password is null")
    private String password;
    @Transient
    private String confirmPassword;
    private AccountType accountType;
    private boolean isActive;

    public User() {
    }

    public User(int id, String username, String email, String password, String confirmPassword, AccountType accountType, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.accountType = accountType;
        this.isActive = isActive;
    }

    public long getId() {
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

    public @NotBlank(message = "email is null") @Email(message = "invalid email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "email is null") @Email(message = "invalid email") String email) {
        this.email = email;
    }

    public @NotBlank(message = "password is null") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "password is null") String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", accountType=" + accountType +
                ", isActive=" + isActive +
                '}';
    }
}
