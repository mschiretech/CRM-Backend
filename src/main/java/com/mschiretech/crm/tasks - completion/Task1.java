package com.mschiretech.crm.core.feature;

import java.util.ArrayList;
import java.util.Scanner;

class Terminal {
    private static ArrayList<String> dataList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    // Terminal GUI Menu
    public void showMainMenu() {
        int choice;
        do {
            System.out.println("================================");
            System.out.println("         CRM Software Menu      ");
            System.out.println("================================");
            System.out.println("1. View Customers");
            System.out.println("2. Add New Customer");
            System.out.println("3. Search Customer by Name");
            System.out.println("4. Edit Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. List All Customers");
            System.out.println("7. Export to CSV");
            System.out.println("8. Import from CSV");
            System.out.println("9. Help");
            System.out.println("10. Exit");
            System.out.print("\nPlease enter your choice (1-10): ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewCustomer();
                case 2 -> addCustomers();
                case 3 -> searchCustomers();
                case 4 -> editCustomers();
                case 5 -> deleteCustomers();
                case 6 -> listAllCustomers();
                case 7 -> exportToCSV();
                case 8 -> importFromCSV();
                case 9 -> help();
                case 10 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }

    // Sub-option Methods

    public static void addCustomers(){
        System.out.println(" Enter the customer name: ");
        String name = scanner.nextLine();
        dataList.add(name);
        System.out.println("Added successfully...");
    }

    public static void viewCustomer(){
        if (dataList.isEmpty()) {
            System.out.println("No Customer Available.");
        }else {
            System.out.println("Stored Data: ");
            for (int i = 0; i< dataList.size(); i++){
                System.out.println((i+1) + "." + dataList.get(i));
            }
        }
    }
    public static void searchCustomers() {
        if (dataList.isEmpty()) {
            System.out.println("No data to search.");
            return;
        }
        System.out.println("Enter search customer: ");
        String keyword = scanner.nextLine();
        boolean found = false;

        System.out.println("Search customer: ");
        for (int i = 0; i < dataList.size(); i++) {
            String item = dataList.get(i);
            if (item.contains(keyword)) {
                System.out.println((i + 1) + "." + item);
                found = true;
            }
        }
    }
    public static void editCustomers(){
        viewCustomer();
        if(dataList.isEmpty()) return;

        System.out.println("Enter the customer name to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 1 && index <= dataList.size()){
            System.out.println("Enter new name: ");
            String newValue = scanner.nextLine();
            dataList.set(index - 1, newValue);
            System.out.println("Customer name updated successfully.");
        }else{
            System.out.println("Invalid index.");
        }
    }
    public static void deleteCustomers(){
        viewCustomer();
        if (dataList.isEmpty()) return;

        System.out.println("Enter the name of the customer to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 1 && index <= dataList.size()) {
            dataList.remove(index - 1);
            System.out.println("Customer deleted successfully.");
        }else{
            System.out.println("Invalid Customer.");
        }
    }


    public void listAllCustomers() {
            if (dataList.isEmpty()) {
                System.out.println("⚠ No data found.");
            } else {
                System.out.println("📋 List of All Data:" + dataList.size());

            }

    }

    public void exportToCSV() {
        System.out.println("Exporting customers to customers.csv...");
    }

    public void importFromCSV() {
        System.out.println("Importing customers from customers.csv...");
    }

    public void help() {
        System.out.println("Help: Use numbers 1-10 to interact with the system.");
    }
}
public class Task1 extends Terminal {
    public static void main(String[] args) {
        Task1 ts = new Task1();
        ts.showMainMenu();
    }
}

