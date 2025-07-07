package core.tempdb;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

 class CustomerDB {
     public int id;
     public String name;
     public String email;
     public String phone;

     public CustomerDB(int id, String name, String email, String phone) {
         this.id = id;
         this.name = name;
         this.email = email;
         this.phone = phone;
     }

     public String toString() {
         return id + " | " + name + " | " + email + " | " + phone;
     }
 }

 class MessageDB {
     public int messageId;
     public int customerId;
     public String content;

     public MessageDB(int messageId, int customerId, String content) {
         this.messageId = messageId;
         this.customerId = customerId;
         this.content = content;
     }

     public String toString(){
         return messageId + " | To Customer: " + customerId + " | " + content;
     }
 }

 class ProjectDB {
     public int id;
     public String name;
     public String owner;


     public ProjectDB(int id, String name, String owner) {
         this.id = id;
         this.name = name;
         this.owner = owner;
     }

     public String toString(){
         return id + " | " + name + " | Owner: " + owner;
     }
 }

 class LogDB {
     public int id;
     public String action;
     public String time;

     public LogDB(int id, String action, String time) {
         this.id = id;
         this.action = action;
         this.time = time;
     }

     public String toString(){
         return id + " | " + action + " | " + time;
     }
 }

 class StatDB {
     public int id;
     public String metric;
     public double value;

     public StatDB(int id, String metric, double value) {
         this.id = id;
         this.metric = metric;
         this.value = value;
     }

     public String toString() {
         return id + " | " + metric + " = " + value;
     }
 }

 class API {
     public int id;
     public String name;
     public String url;

     public API(int id, String name, String url) {
         this.id = id;
         this.name = name;
         this.url = url;
     }

     public String toString(){
         return id + " | " + name + " | " + url;
     }
 }

 class Database {

     public List<CustomerDB> customers = new ArrayList<>();
     public List<MessageDB> messages = new ArrayList<>();
     public List<ProjectDB> projects = new ArrayList<>();
     public List<LogDB> logs = new ArrayList<>();
     public List<StatDB> stats = new ArrayList<>();
     public List<API> apis = new ArrayList<>();

     public void addCustomer(CustomerDB c) {
         customers.add(c);
 }

     public void viewAllCustomers(){
         System.out.println("\n----ALL CUSTOMERS------");
         for (CustomerDB c : customers) System.out.println(c);
     }

     public void updateCustomer(int id, String newName) {
         for (CustomerDB c : customers) {
             if (c.id == id) c.name = newName;
         }
     }

     public void deleteCustomer(int id) {
         customers.removeIf(c -> c.id == id );
     }

     public void addMessage(MessageDB m) {messages.add(m);}

     public void addProject(ProjectDB p) {projects.add(p);}

     public void addLog(LogDB l) { logs.add(l); }

     public void addStat(StatDB s) { stats.add(s); }

     public void addAPI(API a) { apis.add(a); }

     public void viewALLMessages(){
         System.out.println("\n---- ALL MESSAGES ----");
         for (MessageDB m : messages) System.out.println(m);
     }

     public void viewAllProjects() {
         System.out.println("\n--- ALL PROJECTS ---");
         for (ProjectDB p : projects) System.out.println(p);
     }
 }

 class DummyDataInjector {
     public static void inject(Database db) {
         db.addCustomer(new CustomerDB(1, "Shreesthi", "shreesthi@gmail.com", "8093835392"));
         db.addCustomer(new CustomerDB(2, "Sakshi", "sakshi@gmail.com", "9843209334"));
         db.addCustomer(new CustomerDB(3, "Krishna", "krishna@gmail.com", "3420879933"));
         db.addCustomer(new CustomerDB(4,"Riya", "riya@gmail.com","8684488932"));
         db.addCustomer(new CustomerDB(5, "Seslly", "seslly@gmail.com", "9874053976"));

         db.addMessage(new MessageDB(101, 1, "Hello Shreesthi!, Have a great day"));
         db.addMessage(new MessageDB(102, 2, "Hello Sakshi!, Have a great day"));
         db.addMessage(new MessageDB(103, 3, "Hello Krishna!, Have a great day"));
         db.addMessage(new MessageDB(104, 4, "Hello Riya!, Have a great day"));
         db.addMessage(new MessageDB(105, 5, "Hello Seslly!, Have a great day"));

         db.addProject(new ProjectDB(1, "Portfolio", "Shreesthi"));
         db.addProject(new ProjectDB(2, "Inventory APP", "Sakshi"));
         db.addProject(new ProjectDB(3, "QR scanner", "Krishna"));
         db.addProject(new ProjectDB(4, "Authentication", "Riya"));
         db.addProject(new ProjectDB(5, "Amazon webpage", "Seslly"));

         db.addLog(new LogDB(1, "created Successfully", "2025-06-23 11:33 AM"));
         db.addLog(new LogDB(2, "login Success", "2025-06-20 10:43 AM"));
         db.addLog(new LogDB(3, "created Project", "2025-06-22 11:39 AM"));
         db.addLog(new LogDB(4, "Sign up", "2025-06-29 8:33 AM"));
         db.addLog(new LogDB(5, "completed Successfully", "2025-06-28 9:33 AM"));

         db.addStat(new StatDB(1,"Total users", 200));
         db.addStat(new StatDB(2,"Active Sessions", 10));
         db.addStat(new StatDB(3,"Total Clients", 90));
         db.addStat(new StatDB(4,"Active users", 78));
         db.addStat(new StatDB(5,"Total Sessions", 20));

         db.addAPI(new API(1, "LoginAPI", "/api/login"));
         db.addAPI(new API(1, "UserAPI", "/api/user"));
         db.addAPI(new API(1, "ClientAPI", "/api/client"));
         db.addAPI(new API(1, "UserAPI", "/api/user"));
         db.addAPI(new API(1, "LoginAPI", "/api/login"));
     }
 }

 class TerminalUI {
     private final Database db;
     private final Scanner scanner;

     public TerminalUI(Database db) {
         this.db = db;
         this.scanner = new Scanner(System.in);
     }

     public void start(){
         int choice;
         do{
             showMenu();
             System.out.println("Enter choice: ");
             choice = scanner.nextInt();
             scanner.nextLine();

             switch (choice) {
                 case 1: db.viewAllCustomers();break;
                 case 2: addCustomer();break;
                 case 3: updateCustomer();break;
                 case 4: deleteCustomer();break;
                 case 5: db.viewALLMessages();break;
                 case 6: addMessage();break;
                 case 7: db.viewAllProjects();break;
                 case 8: addProject();break;
                 case 9: viewLogs();break;
                 case 10: addLog();break;
                 case 11: viewStats();break;
                 case 12: addStat();break;
                 case 13: viewAPIs();break;
                 case 14: addAPI();break;
                 case 15:
                     System.out.println("Exiting.....");
                 default:
                     System.out.println("Invalid choice. Try again.");
             }
         }while (choice != 15);
     }

     private void showMenu(){
         System.out.println("\n========== CRM Terminal Menu ==========");
         System.out.println("1. View All Customers");
         System.out.println("2. Add New Customer");
         System.out.println("3. Update Customer");
         System.out.println("4. Delete Customer");
         System.out.println("5. View All Messages");
         System.out.println("6. Add New Message");
         System.out.println("7. View All Projects");
         System.out.println("8. Add New Project");
         System.out.println("9. View Logs");
         System.out.println("10. Add Log Entry");
         System.out.println("11. View Stats");
         System.out.println("12. Add Stat");
         System.out.println("13. View APIs");
         System.out.println("14. Add New API");
         System.out.println("15. Exit");
         System.out.println("=======================================");
     }

     private void addCustomer() {
         System.out.println("\n-- Add New Customer --");
         System.out.print("Enter ID: ");
         int id = scanner.nextInt();
         scanner.nextLine();  // consume newline
         System.out.print("Enter Name: ");
         String name = scanner.nextLine();
         System.out.print("Enter Email: ");
         String email = scanner.nextLine();
         System.out.print("Enter Phone: ");
         String phone = scanner.nextLine();

         db.addCustomer(new CustomerDB(id, name, email, phone));
         System.out.println("Customer added successfully.");
     }

     private void updateCustomer() {
         System.out.print("Enter Customer ID to update: ");
         int id = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter new name: ");
         String newName = scanner.nextLine();

         db.updateCustomer(id, newName);
         System.out.println("Customer updated.");
     }

     private void deleteCustomer() {
         System.out.print("Enter Customer ID to delete: ");
         int id = scanner.nextInt();
         db.deleteCustomer(id);
         System.out.println("Customer deleted.");
     }

     private void addMessage() {
         System.out.println("\n-- Add New Message --");
         System.out.print("Enter Message ID: ");
         int messageId = scanner.nextInt();
         System.out.print("Enter Customer ID: ");
         int customerId = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter Message Content: ");
         String content = scanner.nextLine();

         db.addMessage(new MessageDB(messageId, customerId, content));
         System.out.println("Message added.");
     }

     private void addProject() {
         System.out.println("\n-- Add New Project --");
         System.out.print("Enter Project ID: ");
         int id = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter Project Name: ");
         String name = scanner.nextLine();
         System.out.print("Enter Owner Name: ");
         String owner = scanner.nextLine();

         db.addProject(new ProjectDB(id, name, owner));
         System.out.println("Project added.");
     }

     private void viewLogs() {
         System.out.println("\n-- All Logs --");
         for (LogDB l : db.logs) System.out.println(l);
     }

     private void addLog() {
         System.out.println("\n-- Add Log Entry --");
         System.out.print("Enter Log ID: ");
         int id = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter Action: ");
         String action = scanner.nextLine();
         System.out.print("Enter Time: ");
         String time = scanner.nextLine();

         db.addLog(new LogDB(id, action, time));
         System.out.println("Log added.");
     }

     private void viewStats() {
         System.out.println("\n-- All Stats --");
         for (StatDB s : db.stats) System.out.println(s);
     }

     private void addStat() {
         System.out.println("\n-- Add Stat --");
         System.out.print("Enter Stat ID: ");
         int id = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter Metric Name: ");
         String metric = scanner.nextLine();
         System.out.print("Enter Value: ");
         double value = scanner.nextDouble();

         db.addStat(new StatDB(id, metric, value));
         System.out.println("Stat added.");
     }

     private void viewAPIs() {
         System.out.println("\n-- All APIs --");
         for (API a : db.apis) System.out.println(a);
     }

     private void addAPI() {
         System.out.println("\n-- Add API --");
         System.out.print("Enter API ID: ");
         int id = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter API Name: ");
         String name = scanner.nextLine();
         System.out.print("Enter API URL: ");
         String url = scanner.nextLine();

         db.addAPI(new API(id, name, url));
         System.out.println("API added.");
     }
 }

 public class Task3 {
     public static void main(String[] args) {
         Database db = new Database();
         DummyDataInjector.inject(db);

         TerminalUI terminal = new TerminalUI(db);
         terminal.start();

         db.viewAllCustomers();
         db.viewALLMessages();
         db.viewAllProjects();
         db.updateCustomer(3, "Madhusmita");
         db.viewAllCustomers();
         db.deleteCustomer(5);
     }
}