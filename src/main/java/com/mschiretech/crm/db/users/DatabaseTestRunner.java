package com.mschiretech.crm.db.users;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class DatabaseTestRunner {

    private static UserDAOImpl userDAO;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== User Database Test Runner ===");
        System.out.println("Initializing database connection...");

        // Initialize database connection
        try {
            initializeDatabase();
            System.out.println("✓ Database connection established successfully!");

            // Run interactive menu
            runInteractiveMenu();

        } catch (Exception e) {
            System.err.println("✗ Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void initializeDatabase() {
        // Configure your database connection here
        DataSource dataSource = createDataSource();
        userDAO = UserDAOImpl.getInstance(dataSource);

        // Test connection by getting user count
        long userCount = userDAO.getUserCount();
        System.out.println("Current users in database: " + userCount);
    }

    private static DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // For H2 in-memory database (for testing)
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        // Uncomment and modify for MySQL
        /*
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/your_database");
        dataSource.setUsername("your_username");
        dataSource.setPassword("your_password");
        */

        return dataSource;
    }

    private static void runInteractiveMenu() {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createTestUser();
                    break;
                case 2:
                    createCustomUser();
                    break;
                case 3:
                    findUserById();
                    break;
                case 4:
                    findUserByUserId();
                    break;
                case 5:
                    findUserByEmail();
                    break;
                case 6:
                    displayAllUsers();
                    break;
                case 7:
                    displayActiveUsers();
                    break;
                case 8:
                    displayAdminUsers();
                    break;
                case 9:
                    updateUserStatus();
                    break;
                case 10:
                    deleteUser();
                    break;
                case 11:
                    runAutomatedTests();
                    break;
                case 12:
                    showDatabaseStats();
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           USER DATABASE MENU");
        System.out.println("=".repeat(50));
        System.out.println("1.  Create Test User (Auto-generated)");
        System.out.println("2.  Create Custom User");
        System.out.println("3.  Find User by Serial Number");
        System.out.println("4.  Find User by User ID");
        System.out.println("5.  Find User by Email");
        System.out.println("6.  Display All Users");
        System.out.println("7.  Display Active Users");
        System.out.println("8.  Display Admin Users");
        System.out.println("9.  Update User Status");
        System.out.println("10. Delete User");
        System.out.println("11. Run Automated Tests");
        System.out.println("12. Show Database Statistics");
        System.out.println("0.  Exit");
        System.out.println("=".repeat(50));
    }

    private static void createTestUser() {
        System.out.println("\n--- Creating Test User ---");

        long timestamp = System.currentTimeMillis();
        User user = new User(
                "user_" + timestamp,
                "testuser_" + timestamp,
                "John",
                "Michael",
                "Doe",
                "123 Main St, City, State 12345",
                "john.doe." + timestamp + "@example.com",
                "+1234567890",
                "hashed_password_" + timestamp
        );

        try {
            Long slNo = userDAO.createUser(user);
            System.out.println("✓ Test user created successfully!");
            System.out.println("Serial Number: " + slNo);
            System.out.println("User ID: " + user.getUserId());
            System.out.println("Email: " + user.getEmail());

            // Fetch and display the created user
            Optional<User> createdUser = userDAO.findById(slNo);
            if (createdUser.isPresent()) {
                displayUserInfo(createdUser.get());
            }

        } catch (Exception e) {
            System.err.println("✗ Failed to create test user: " + e.getMessage());
        }
    }

    private static void createCustomUser() {
        System.out.println("\n--- Creating Custom User ---");

        try {
            String userId = getStringInput("Enter User ID: ");
            String userName = getStringInput("Enter Username: ");
            String firstName = getStringInput("Enter First Name: ");
            String middleName = getStringInput("Enter Middle Name (optional): ");
            String lastName = getStringInput("Enter Last Name: ");
            String address = getStringInput("Enter Address: ");
            String email = getStringInput("Enter Email: ");
            String phone = getStringInput("Enter Phone: ");
            String password = getStringInput("Enter Password: ");

            // Check if user already exists
            if (userDAO.existsByUserId(userId)) {
                System.out.println("✗ User ID already exists!");
                return;
            }

            if (userDAO.existsByEmail(email)) {
                System.out.println("✗ Email already exists!");
                return;
            }

            // Generate values for other fields
            String hashedPassword = hashPassword(password); // replace with real hash function
            String token = UUID.randomUUID().toString();
            LocalDateTime tokenExpiry = LocalDateTime.now().plusDays(7); // token valid for 7 days
            LocalDateTime joiningDate = LocalDateTime.now();
            Boolean isActive = true;
            Boolean isAdmin = false;
            LocalDateTime lastLoginTime = null; // no login yet

            // Create user object and set all fields
            User user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            user.setFirstName(firstName);
            user.setMiddleName(middleName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setEmail(email);
            user.setPhone(phone);
            user.setHashedPassword(hashedPassword);
            user.setToken(token);
            user.setTokenExpiry(tokenExpiry);
            user.setJoiningDate(joiningDate);
            user.setIsActive(isActive);
            user.setIsAdmin(isAdmin);
            user.setLastLoginTime(lastLoginTime);

            // Insert user and get serial number
            Long slNo = userDAO.createUser(user);
            System.out.println("✓ Custom user created successfully!");
            System.out.println("Serial Number: " + slNo);

            // Fetch and display the created user
            Optional<User> createdUser = userDAO.findById(slNo);
            createdUser.ifPresent(DatabaseTestRunner::displayUserInfo);

        } catch (Exception e) {
            System.err.println("✗ Failed to create custom user: " + e.getMessage());
        }
    }

    private static String hashPassword(String password) {
        // For demo: this should be replaced with BCrypt or other secure method
        return "hashed_" + password;
    }


    private static void findUserById() {
        System.out.println("\n--- Find User by Serial Number ---");

        long slNo = getLongInput("Enter Serial Number: ");

        try {
            Optional<User> user = userDAO.findById(slNo);
            if (user.isPresent()) {
                System.out.println("✓ User found!");
                displayUserInfo(user.get());
            } else {
                System.out.println("✗ User not found with Serial Number: " + slNo);
            }
        } catch (Exception e) {
            System.err.println("✗ Error finding user: " + e.getMessage());
        }
    }

    private static void findUserByUserId() {
        System.out.println("\n--- Find User by User ID ---");

        String userId = getStringInput("Enter User ID: ");

        try {
            Optional<User> user = userDAO.findByUserId(userId);
            if (user.isPresent()) {
                System.out.println("✓ User found!");
                displayUserInfo(user.get());
            } else {
                System.out.println("✗ User not found with User ID: " + userId);
            }
        } catch (Exception e) {
            System.err.println("✗ Error finding user: " + e.getMessage());
        }
    }

    private static void findUserByEmail() {
        System.out.println("\n--- Find User by Email ---");

        String email = getStringInput("Enter Email: ");

        try {
            Optional<User> user = userDAO.findByEmail(email);
            if (user.isPresent()) {
                System.out.println("✓ User found!");
                displayUserInfo(user.get());
            } else {
                System.out.println("✗ User not found with Email: " + email);
            }
        } catch (Exception e) {
            System.err.println("✗ Error finding user: " + e.getMessage());
        }
    }

    private static void displayAllUsers() {
        System.out.println("\n--- All Users ---");

        try {
            List<User> users = userDAO.findAllUsers();
            if (users.isEmpty()) {
                System.out.println("No users found in database.");
            } else {
                System.out.println("Found " + users.size() + " users:");
                for (User user : users) {
                    displayUserSummary(user);
                }
            }
        } catch (Exception e) {
            System.err.println("✗ Error retrieving users: " + e.getMessage());
        }
    }

    private static void displayActiveUsers() {
        System.out.println("\n--- Active Users ---");

        try {
            List<User> users = userDAO.findActiveUsers();
            if (users.isEmpty()) {
                System.out.println("No active users found.");
            } else {
                System.out.println("Found " + users.size() + " active users:");
                for (User user : users) {
                    displayUserSummary(user);
                }
            }
        } catch (Exception e) {
            System.err.println("✗ Error retrieving active users: " + e.getMessage());
        }
    }

    private static void displayAdminUsers() {
        System.out.println("\n--- Admin Users ---");

        try {
            List<User> users = userDAO.findAdminUsers();
            if (users.isEmpty()) {
                System.out.println("No admin users found.");
            } else {
                System.out.println("Found " + users.size() + " admin users:");
                for (User user : users) {
                    displayUserSummary(user);
                }
            }
        } catch (Exception e) {
            System.err.println("✗ Error retrieving admin users: " + e.getMessage());
        }
    }

    private static void updateUserStatus() {
        System.out.println("\n--- Update User Status ---");

        String userId = getStringInput("Enter User ID: ");

        // Check if user exists
        if (!userDAO.existsByUserId(userId)) {
            System.out.println("✗ User not found with ID: " + userId);
            return;
        }

        System.out.println("1. Activate User");
        System.out.println("2. Deactivate User");
        System.out.println("3. Promote to Admin");
        System.out.println("4. Demote from Admin");
        System.out.println("5. Update Last Login Time");

        int choice = getIntInput("Enter choice: ");

        try {
            boolean success = false;
            String action = "";

            switch (choice) {
                case 1:
                    success = userDAO.activateUser(userId);
                    action = "activated";
                    break;
                case 2:
                    success = userDAO.deactivateUser(userId);
                    action = "deactivated";
                    break;
                case 3:
                    success = userDAO.promoteToAdmin(userId);
                    action = "promoted to admin";
                    break;
                case 4:
                    success = userDAO.demoteFromAdmin(userId);
                    action = "demoted from admin";
                    break;
                case 5:
                    success = userDAO.updateLastLoginTime(userId);
                    action = "last login time updated";
                    break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            if (success) {
                System.out.println("✓ User successfully " + action + "!");
            } else {
                System.out.println("✗ Failed to update user status.");
            }

        } catch (Exception e) {
            System.err.println("✗ Error updating user: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        System.out.println("\n--- Delete User ---");

        String userId = getStringInput("Enter User ID to delete: ");

        // Check if user exists
        if (!userDAO.existsByUserId(userId)) {
            System.out.println("✗ User not found with ID: " + userId);
            return;
        }

        String confirm = getStringInput("Are you sure you want to delete this user? (yes/no): ");

        if (confirm.equalsIgnoreCase("yes")) {
            try {
                boolean success = userDAO.deleteByUserId(userId);
                if (success) {
                    System.out.println("✓ User deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete user.");
                }
            } catch (Exception e) {
                System.err.println("✗ Error deleting user: " + e.getMessage());
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    private static void runAutomatedTests() {
        System.out.println("\n--- Running Automated Tests ---");

        try {
            // Test 1: Create multiple users
            System.out.println("Test 1: Creating multiple test users...");
            for (int i = 1; i <= 5; i++) {
                User user = new User(
                        "auto_user_" + i,
                        "autouser" + i,
                        "FirstName" + i,
                        "MiddleName" + i,
                        "LastName" + i,
                        "Address " + i,
                        "autouser" + i + "@test.com",
                        "+123456789" + i,
                        "hashed_password_" + i
                );

                if (i == 3) user.setIsAdmin(true); // Make one user admin

                userDAO.createUser(user);
            }
            System.out.println("✓ Created 5 test users");

            // Test 2: Count users
            System.out.println("Test 2: Counting users...");
            long totalUsers = userDAO.getUserCount();
            System.out.println("✓ Total users: " + totalUsers);

            // Test 3: Find active users
            System.out.println("Test 3: Finding active users...");
            List<User> activeUsers = userDAO.findActiveUsers();
            System.out.println("✓ Active users: " + activeUsers.size());

            // Test 4: Find admin users
            System.out.println("Test 4: Finding admin users...");
            List<User> adminUsers = userDAO.findAdminUsers();
            System.out.println("✓ Admin users: " + adminUsers.size());

            // Test 5: Update user status
            System.out.println("Test 5: Testing user status updates...");
            boolean deactivated = userDAO.deactivateUser("auto_user_1");
            boolean promoted = userDAO.promoteToAdmin("auto_user_2");
            System.out.println("✓ Deactivation: " + (deactivated ? "SUCCESS" : "FAILED"));
            System.out.println("✓ Promotion: " + (promoted ? "SUCCESS" : "FAILED"));

            // Test 6: Token management
            System.out.println("Test 6: Testing token management...");
            String token = "test_token_" + System.currentTimeMillis();
            LocalDateTime expiry = LocalDateTime.now().plusHours(1);
            boolean tokenSet = userDAO.updateToken("auto_user_3", token, expiry);
            boolean tokenValid = userDAO.isTokenValid("auto_user_3", token);
            System.out.println("✓ Token set: " + (tokenSet ? "SUCCESS" : "FAILED"));
            System.out.println("✓ Token valid: " + (tokenValid ? "SUCCESS" : "FAILED"));

            System.out.println("\n✓ All automated tests completed!");

        } catch (Exception e) {
            System.err.println("✗ Automated test failed: " + e.getMessage());
        }
    }

    private static void showDatabaseStats() {
        System.out.println("\n--- Database Statistics ---");

        try {
            long totalUsers = userDAO.getUserCount();
            List<User> activeUsers = userDAO.findActiveUsers();
            List<User> adminUsers = userDAO.findAdminUsers();

            System.out.println("Total Users: " + totalUsers);
            System.out.println("Active Users: " + activeUsers.size());
            System.out.println("Inactive Users: " + (totalUsers - activeUsers.size()));
            System.out.println("Admin Users: " + adminUsers.size());
            System.out.println("Regular Users: " + (totalUsers - adminUsers.size()));

        } catch (Exception e) {
            System.err.println("✗ Error retrieving statistics: " + e.getMessage());
        }
    }

    private static void displayUserInfo(User user) {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("Serial No: " + user.getSlNo());
        System.out.println("User ID: " + user.getUserId());
        System.out.println("Username: " + user.getUserName());
        System.out.println("Name: " + user.getFirstName() + " " +
                (user.getMiddleName() != null ? user.getMiddleName() + " " : "") +
                user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Address: " + user.getAddress());
        System.out.println("Joining Date: " + user.getJoiningDate());
        System.out.println("Active: " + (user.getIsActive() ? "Yes" : "No"));
        System.out.println("Admin: " + (user.getIsAdmin() ? "Yes" : "No"));
        System.out.println("Last Login: " + user.getLastLoginTime());
        System.out.println("Token: " + (user.getToken() != null ? "Present" : "Not set"));
        System.out.println("-".repeat(40));
    }

    private static void displayUserSummary(User user) {
        System.out.printf("%-3d | %-12s | %-20s | %-30s | %-8s | %-5s%n",
                user.getSlNo(),
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getIsActive() ? "Active" : "Inactive",
                user.getIsAdmin() ? "Admin" : "User"
        );
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private static long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}