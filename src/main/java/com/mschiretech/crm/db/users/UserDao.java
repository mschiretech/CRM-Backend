package com.mschiretech.crm.db.users;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    // Create
    Long createUser(User user);

    // Read
    Optional<User> findById(Long slNo);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    List<User> findAllUsers();
    List<User> findActiveUsers();
    List<User> findAdminUsers();

    // Update
    boolean updateUser(User user);
    boolean updatePassword(String userId, String newHashedPassword);
    boolean updateToken(String userId, String token, LocalDateTime tokenExpiry);
    boolean updateLastLoginTime(String userId);
    boolean activateUser(String userId);
    boolean deactivateUser(String userId);
    boolean promoteToAdmin(String userId);
    boolean demoteFromAdmin(String userId);

    // Delete
    boolean deleteUser(Long slNo);
    boolean deleteByUserId(String userId);

    // Utility methods
    boolean existsByUserId(String userId);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    long getUserCount();
    boolean isTokenValid(String userId, String token);
}