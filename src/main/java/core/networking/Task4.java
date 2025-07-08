package core.networking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.networking.NetworkClient;

import java.io.*;
import java.net.*;


class Customer {
    public int id;
    public String name;
    public String email;
    public String phone;

    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String toJson() {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"phone\":\"%s\"}", id, name, email, phone);
    }

    public String toString(){
        return id + " | " + name + " | " + email + " | " + phone;
    }
}

class Database {
    public List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void viewAllCustomers(){
        System.out.println("\n---- ALL CUSTOMERS ----");
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    public void updateCustomerName(int id, String newName) {
        for (Customer c : customers) {
            if (c.id == id) {
                c.name = newName;
            }
        }
    }

    public void deleteCustomer(int id) {
        customers.removeIf(c -> c.id == id);
    }
}

class DummyDataInjector {
    public static void inject(Database db) {
        db.addCustomer(new Customer(1, "Shreesthi", "shreesthi@gmail.com", "9862345720"));
        db.addCustomer(new Customer(2, "meow", "meow@gmail.com", "9861422538"));
    }
}

class TerminalUI {
    private final Database db;
    private final NetworkClient client;
    private final Scanner scanner;

    public TerminalUI(Database db){
        this.db = db;
        this.client = new NetworkClient();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do{
            showMenu();
            System.out.println("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: db.viewAllCustomers(); break;
                case 2: addCustomer(); break;
                case 3: updateCustomer(); break;
                case 4: deleteCustomer(); break;
                case 5: sendAllUsersToServer(); break;
                case 6: getAllUsersFromServer(); break;
                case 7: getServerStats(); break;
                case 8: postUpdateUser(); break;
                case 9:
                    System.out.println("Exiting....."); break;
                default:
                    System.out.println("Invalid choice.Try Again.");

            }
        }while(choice != 9);
    }

    private void showMenu(){
        System.out.println("\n======= CRM TERMINAL MENU ========");
        System.out.println("1. View All Customers ");
        System.out.println("2. Add New Customer");
        System.out.println("3. Update Customer");
        System.out.println("4. Delete Customer");
        System.out.println("5. Send All Users to Server");
        System.out.println("6. Get All Users From Server");
        System.out.println("7. Get Server Stats");
        System.out.println("8. Post Update User");
        System.out.println("9. Exit");
        System.out.println("=================================");
    }

    private void addCustomer(){
        System.out.println("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Phone: ");
        String phone = scanner.nextLine();

        db.addCustomer(new Customer(id, name, email, phone));
        System.out.println("Customer added.");
    }

    private void updateCustomer(){
        System.out.println("Enter Customer ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new name: ");
        String newName = scanner.nextLine();

        db.updateCustomerName(id, newName);
        System.out.println("Customer updated.");
    }

    private void deleteCustomer(){
        System.out.println("Enter Customer ID to delete: ");
        int id = scanner.nextInt();
        db.deleteCustomer(id);
        System.out.println("Customer deleted.");
    }

    private void sendAllUsersToServer() {
        List<String> usersJson = new ArrayList<>();
        for (Customer c : db.customers) {
            usersJson.add(c.toJson());
        }
        client.sendAllUsersToServer(usersJson);
    }


    private void getAllUsersFromServer(){
        client.getAllUsersFromServer();
    }

    private void getServerStats(){
        client.getServerStats();
    }

    private void postUpdateUser() {
        System.out.println("Enter Customer ID to Update on server: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter updated name; ");
        String name = scanner.nextLine();
        System.out.println("Enter updated email: ");
        String email = scanner.nextLine();
        System.out.println("Enter updated phone: ");
        String phone = scanner.nextLine();

        String json = String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\":\"%s\",\"phone\":\"%s\"}", id, name, email, phone);
        client.postUpdateUser(json);
    }
}

class NetworkClient {
    private static final String BASE_URL = "https://reqres.in/api";

    public void sendAllUsersToServer(List<String> userJson) {
        try{
            URL url = new URL(BASE_URL + "/users");
            HttpURLConnection conn = (HttpURLConnection)  url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String payload = "{" + String.join(",", userJson) + "]";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes());
                os.flush();
            }
            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Response: " + readResponse(conn));
        } catch(IOException e) {
            System.out.println("Error in sendAllUsersToServer: " + e.getMessage());
        }
    }


    public void getAllUsersFromServer() {
        try{
            URL url = new URL(BASE_URL +"/users?page=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Users: " + readResponse(conn));
        } catch(IOException e) {
            System.out.println("Error in getAllUsersFromServer: " + e.getMessage());
        }
    }

    public void getServerStats() {
        try {
            URL url = new URL(BASE_URL + "/unknown");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            System.out.println("Stats: " + readResponse(conn));
        } catch (IOException e) {
            System.out.println("Error in getServerStats: " + e.getMessage());
        }
    }

    public void postUpdateUser(String userJson) {
        try{
            URL url = new URL(BASE_URL + "/users/2");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            try(OutputStream os = conn.getOutputStream()) {
                os.write(userJson.getBytes());
                os.flush();
            }

            System.out.println("Update Response: " + readResponse(conn));
        } catch (IOException e) {
            System.out.println("Error in postUpdateUser: " + e.getMessage());
        }
    }

    private String readResponse(HttpURLConnection conn) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder out = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            out.append(line);
        }
        return out.toString();
    }
}


public class Task4 {
    public static void main(String[] args) {
        Database db = new Database();
        DummyDataInjector.inject(db);

        TerminalUI terminal = new TerminalUI(db);
        terminal.start();
    }
}