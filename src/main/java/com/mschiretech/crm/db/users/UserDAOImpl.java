package com.mschiretech.crm.db.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDao {

    private static UserDAOImpl instance;
    private final JdbcTemplate jdbcTemplate;

    // Private constructor for singleton
    private UserDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        createTableIfNotExists();
    }

    // Singleton instance getter
    public static synchronized UserDAOImpl getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new UserDAOImpl(dataSource);
        }
        return instance;
    }

    // Create table if not exists
    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                sl_no BIGINT PRIMARY KEY AUTO_INCREMENT,
                user_id VARCHAR(50) UNIQUE NOT NULL,
                user_name VARCHAR(100) UNIQUE NOT NULL,
                first_name VARCHAR(100) NOT NULL,
                middle_name VARCHAR(100),
                last_name VARCHAR(100) NOT NULL,
                address TEXT,
                email VARCHAR(255) UNIQUE NOT NULL,
                phone VARCHAR(20),
                hashed_password VARCHAR(255) NOT NULL,
                token VARCHAR(500),
                token_expiry TIMESTAMP,
                joining_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                is_active BOOLEAN DEFAULT TRUE,
                is_admin BOOLEAN DEFAULT FALSE,
                last_login_time TIMESTAMP
            )
            """;
        jdbcTemplate.execute(sql);
    }

    // Row mapper for User entity
    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setSlNo(rs.getLong("sl_no"));
            user.setUserId(rs.getString("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setFirstName(rs.getString("first_name"));
            user.setMiddleName(rs.getString("middle_name"));
            user.setLastName(rs.getString("last_name"));
            user.setAddress(rs.getString("address"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setHashedPassword(rs.getString("hashed_password"));
            user.setToken(rs.getString("token"));

            Timestamp tokenExpiry = rs.getTimestamp("token_expiry");
            if (tokenExpiry != null) {
                user.setTokenExpiry(tokenExpiry.toLocalDateTime());
            }

            Timestamp joiningDate = rs.getTimestamp("joining_date");
            if (joiningDate != null) {
                user.setJoiningDate(joiningDate.toLocalDateTime());
            }

            user.setIsActive(rs.getBoolean("is_active"));
            user.setIsAdmin(rs.getBoolean("is_admin"));

            Timestamp lastLoginTime = rs.getTimestamp("last_login_time");
            if (lastLoginTime != null) {
                user.setLastLoginTime(lastLoginTime.toLocalDateTime());
            }

            return user;
        }
    };

    @Override
    public Long createUser(User user) {
        String sql = """
            INSERT INTO users (user_id, user_name, first_name, middle_name, last_name, 
                              address, email, phone, hashed_password, joining_date, is_active, is_admin)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"sl_no"});
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getMiddleName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getEmail());
            ps.setString(8, user.getPhone());
            ps.setString(9, user.getHashedPassword());
            ps.setTimestamp(10, Timestamp.valueOf(user.getJoiningDate()));
            ps.setBoolean(11, user.getIsActive());
            ps.setBoolean(12, user.getIsAdmin());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<User> findById(Long slNo) {
        String sql = "SELECT * FROM users WHERE sl_no = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, slNo);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, userId);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE user_name = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, userName);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, email);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users ORDER BY sl_no";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public List<User> findActiveUsers() {
        String sql = "SELECT * FROM users WHERE is_active = TRUE ORDER BY sl_no";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public List<User> findAdminUsers() {
        String sql = "SELECT * FROM users WHERE is_admin = TRUE ORDER BY sl_no";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public boolean updateUser(User user) {
        String sql = """
            UPDATE users SET user_name = ?, first_name = ?, middle_name = ?, last_name = ?,
                            address = ?, email = ?, phone = ?, is_active = ?, is_admin = ?
            WHERE sl_no = ?
            """;

        int rowsAffected = jdbcTemplate.update(sql,
                user.getUserName(), user.getFirstName(), user.getMiddleName(),
                user.getLastName(), user.getAddress(), user.getEmail(),
                user.getPhone(), user.getIsActive(), user.getIsAdmin(), user.getSlNo());

        return rowsAffected > 0;
    }

    @Override
    public boolean updatePassword(String userId, String newHashedPassword) {
        String sql = "UPDATE users SET hashed_password = ? WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, newHashedPassword, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean updateToken(String userId, String token, LocalDateTime tokenExpiry) {
        String sql = "UPDATE users SET token = ?, token_expiry = ? WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, token,
                tokenExpiry != null ? Timestamp.valueOf(tokenExpiry) : null, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean updateLastLoginTime(String userId) {
        String sql = "UPDATE users SET last_login_time = ? WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, Timestamp.valueOf(LocalDateTime.now()), userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean activateUser(String userId) {
        String sql = "UPDATE users SET is_active = TRUE WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean deactivateUser(String userId) {
        String sql = "UPDATE users SET is_active = FALSE WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean promoteToAdmin(String userId) {
        String sql = "UPDATE users SET is_admin = TRUE WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean demoteFromAdmin(String userId) {
        String sql = "UPDATE users SET is_admin = FALSE WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteUser(Long slNo) {
        String sql = "DELETE FROM users WHERE sl_no = ?";
        int rowsAffected = jdbcTemplate.update(sql, slNo);
        return rowsAffected > 0;
    }

    @Override
    public boolean deleteByUserId(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean existsByUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByUserName(String userName) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userName);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public long getUserCount() {
        String sql = "SELECT COUNT(*) FROM users";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }

    @Override
    public boolean isTokenValid(String userId, String token) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ? AND token = ? AND token_expiry > ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                userId, token, Timestamp.valueOf(LocalDateTime.now()));
        return count != null && count > 0;
    }
}