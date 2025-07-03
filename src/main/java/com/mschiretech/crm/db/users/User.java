package com.mschiretech.crm.db.users;

import java.time.LocalDateTime;

public class User {
    private Long slNo;
    private String userId;
    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private String hashedPassword;
    private String token;
    private LocalDateTime tokenExpiry;
    private LocalDateTime joiningDate;
    private Boolean isActive;
    private Boolean isAdmin;
    private LocalDateTime lastLoginTime;

    // Default constructor
    public User() {
        this.joiningDate = LocalDateTime.now();
        this.isActive = true;
        this.isAdmin = false;
    }

    // Parameterized constructor
    public User(String userId, String userName, String firstName, String middleName,
                String lastName, String address, String email, String phone,
                String hashedPassword) {
        this();
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.hashedPassword = hashedPassword;
    }

    // Getters and Setters
    public Long getSlNo() { return slNo; }
    public void setSlNo(Long slNo) { this.slNo = slNo; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getHashedPassword() { return hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public LocalDateTime getTokenExpiry() { return tokenExpiry; }
    public void setTokenExpiry(LocalDateTime tokenExpiry) { this.tokenExpiry = tokenExpiry; }

    public LocalDateTime getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDateTime joiningDate) { this.joiningDate = joiningDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Boolean getIsAdmin() { return isAdmin; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

    public LocalDateTime getLastLoginTime() { return lastLoginTime; }
    public void setLastLoginTime(LocalDateTime lastLoginTime) { this.lastLoginTime = lastLoginTime; }

    @Override
    public String toString() {
        return "User{" +
                "slNo=" + slNo +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", joiningDate=" + joiningDate +
                ", isActive=" + isActive +
                ", isAdmin=" + isAdmin +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}