package com.mschiretech.crm.core.feature;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

class Features{

    String name;
    private static ArrayList<String> customerlist = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);



        public void ViewAllCustomers () {
            if (customerlist.isEmpty()) {
                System.out.println("No Customer Available.");
            } else {
                System.out.println("Stored Data: ");
                for (int i = 0; i < customerlist.size(); i++) {
                    System.out.println((i + 1) + "." + customerlist.get(i));
                }
            }
        }
    public void AddNewCustomer(){
        System.out.println(" Enter the customer name: ");
        String name = scanner.nextLine();
        customerlist.add(name);
        System.out.println("Adding New Customers...");
    }
    public void GetCustomerByName(){
        if (customerlist.isEmpty()) {
            System.out.println("No data to search.");
            return;
        }
        System.out.println("Enter search customer: ");
        String keyword = scanner.nextLine();
        boolean found = false;
        System.out.println("Getting Customer By Name...");
        for (int i = 0; i < customerlist.size(); i++) {
            String item = customerlist.get(i);
            if (item.contains(keyword)) {
                System.out.println((i + 1) + "." + item);
                found = true;
            }
        }
    }
    public void EditCustomer(){
        ViewAllCustomers();
        if(customerlist.isEmpty()) return;

        System.out.println("Enter the customer name to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 1 && index <= customerlist.size()){
            System.out.println("Enter new name: ");
            String newValue = scanner.nextLine();
            customerlist.set(index - 1, newValue);
            System.out.println("Customer name updated successfully.");
        }else {
            System.out.println("Invalid index.");
        }
    }
    public void DeleteCustomer(){
        ViewAllCustomers();
        if (customerlist.isEmpty()) return;

        System.out.println("Enter the name of the customer to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 1 && index <= customerlist.size()) {
            customerlist.remove(index - 1);
            System.out.println("Customer deleted successfully.");
        }else{
            System.out.println("Invalid Customer.");
        }
    }

    public void MessageCustomer() {
        System.out.println("Messaging Customer ");
    }

    public void ShowAllMessagesOfCustomer(){
        System.out.print("Showing all messages ");
    }


    public void ExportToCSV(){
        System.out.println("Exporting TO CSV...");
    }
    public void ImportFromCSV(){
        System.out.println("Importing From CSV...");
    }
    public void help(){
        System.out.println("Help Section...");
    }
    public void systemStatsAndAnalysis() {
        System.out.println("System stats and analysis...");
    }

    public void apiCountsAndHealth() {
        System.out.println("API counts and health...");
    }

    public void createNewProjectWithCustomer() {
        System.out.println("Creating new project with customer...");
    }

    public void editProject() {
        System.out.println("Editing project...");
    }

    public void analysisOfProject() {
        System.out.println("Analyzing project...");
    }

    public void deleteProject() {
        System.out.println("Deleting project...");
    }

    public void listAllProjects() {
        System.out.println("Listing all projects...");
    }

    public void exit() {
        System.out.println("Exiting system...");
    }
}

public class Task2 extends Features {

    public void Menu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===============================");
            System.out.println("         CRM Software Menu     ");
            System.out.println("===============================");
            System.out.println("1. View All Customers");
            System.out.println("2. Add New Customer");
            System.out.println("3. Get Customer by Name");
            System.out.println("4. Edit Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Message Customer");
            System.out.println("7. Show All Messages of Customer");
            System.out.println("8. Export to CSV");
            System.out.println("9. Import from CSV");
            System.out.println("10. Help");
            System.out.println("11. System Stats and Analysis");
            System.out.println("12. API Counts and Health");
            System.out.println("13. Create New Project with Customer");
            System.out.println("14. Edit Project");
            System.out.println("15. Analysis of Project");
            System.out.println("16. Delete Project");
            System.out.println("17. List All Projects");
            System.out.println("18. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> ViewAllCustomers();
                case 2 -> AddNewCustomer();
                case 3 -> GetCustomerByName();
                case 4 -> EditCustomer();
                case 5 -> DeleteCustomer();
                case 6 -> MessageCustomer();
                case 7 -> ShowAllMessagesOfCustomer();
                case 8 -> ExportToCSV();
                case 9 -> ImportFromCSV();
                case 10 -> help();
                case 11 -> systemStatsAndAnalysis();
                case 12 -> apiCountsAndHealth();
                case 13 -> createNewProjectWithCustomer();
                case 14 -> editProject();
                case 15 -> analysisOfProject();
                case 16 -> deleteProject();
                case 17 -> listAllProjects();
                case 18 -> exit();
                default -> System.out.println("Invalid option. Try again.");
            }

        } while (choice != 18);

        sc.close();
    }

    public static void main(String[] args) {
        Task2 t2 = new Task2();
        t2.Menu();
    }
}