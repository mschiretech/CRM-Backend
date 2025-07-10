package core.networking.jsonEn;

import core.networking.jsonEn.JsonFormatter;
import org.json.JSONObject;

import java.util.*;

public class Task5 {
    static Scanner scanner = new Scanner(System.in);
    static List<Map<String, Object>> users = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n======= CRM User Manager =======");
            System.out.println("1. Add New User");
            System.out.println("2. View All Users (JSON)");
            System.out.println("3. Update Existing User");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewUsersJson();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    System.out.println("Exiting CRM...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void addUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("user_id", users.size() + 1);

        System.out.print("First Name: ");
        user.put("first_name", scanner.nextLine());

        System.out.print("Last Name: ");
        user.put("last_name", scanner.nextLine());

        System.out.print("Email: ");
        user.put("email", scanner.nextLine());

        System.out.print("Phone: ");
        user.put("phone", scanner.nextLine());

        System.out.print("Address: ");
        user.put("address", scanner.nextLine());

        System.out.print("Company: ");
        user.put("company", scanner.nextLine());

        System.out.print("Position: ");
        user.put("position", scanner.nextLine());

        user.put("created_at", new Date().toInstant().toString());

        System.out.print("Notes: ");
        user.put("notes", scanner.nextLine());

        users.add(user);
        System.out.println("✅ User added successfully.");
    }

    private static void viewUsersJson() {
        JSONObject json = JsonFormatter.formatUsers(users);
        System.out.println("\n📦 All Users (JSON):");
        System.out.println(json.toString(2));
    }

    private static void updateUser() {
        System.out.print("Enter User ID to update: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Map<String, Object> user = null;
        for (Map<String, Object> u : users) {
            if ((int) u.get("user_id") == userId) {
                user = u;
                break;
            }
        }

        if (user == null) {
            System.out.println("❌ User not found.");
            return;
        }

        System.out.println("Leave field blank to skip updating it.");

        System.out.print("New First Name: ");
        String first_name = scanner.nextLine();
        if (!first_name.isEmpty()) user.put("first_name", first_name);

        System.out.print("New Email: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) user.put("email", email);

        System.out.print("New Phone: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) user.put("phone", phone);

        System.out.print("New Notes: ");
        String notes = scanner.nextLine();
        if (!notes.isEmpty()) user.put("notes", notes);

        System.out.println("✅ User updated.");
    }
}
