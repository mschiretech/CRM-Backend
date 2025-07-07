package core.tempdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

 class CustomerDB {
    public static class Customer implements Serializable {
       public int id;
       public String name;
       public String email;

        public Customer(int id, String name, String email){
            this.id = id;
            this.name = name;
            this.email = email;
        }
        public String toString(){
            return id + " | " + name + " | " + email;
        }
    }

    private List<Customer>customers = new ArrayList<>();
    private final String filePath = "customers.db";

    public void addCustomer(Customer c) {
        customers.add(c);
        saveToFile();
    }

    public void view() {
        customers.forEach(System.out::println);
    }

    public Customer getById(int id) {
        return customers.stream().filter(c -> c.id == id).findFirst().orElse(null);
    }

    public void updateCustomer(int id, String name, String email) {
        Customer c = getById(id);
        if (c != null) {
            c.name = name;
            c.email = email;
            saveToFile();
        }
    }

    public void deleteCustomer(int id) {
        customers.removeIf(c -> c.id == id);
        saveToFile();
    }

    public void injectDummyData(){
        customers.add(new Customer(1, "Shreesthi", "shreesthi@gmail.com"));
        customers.add(new Customer(2, "Pranati", "pranati@gmail.com"));
        saveToFile();
    }

    public void saveToFile() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(customers);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadFromFile() {
        File file = new File(filePath);
        if(file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
                customers = (List<Customer>) in.readObject();
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

class MessageDB {
    public static class Message implements Serializable {
        public int messageId;
        public int senderId;
        public int receiverId;
        public String content;

        public Message(int messageId, int senderId, int receiverId, String content) {
            this.messageId = messageId;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.content = content;
        }

        public String toString() {
            return messageId + " | From: " + senderId + " -> To: " + receiverId + " | " + content;
        }
    }

    private List<Message> messages = new ArrayList<>();
    private final String filePath = "message.db";

    public void addMessage(Message m) {
        messages.add(m);
        saveToFile();
    }

    public void view() {
        messages.forEach(System.out::println);
    }

    public Message getById(int id) {
        return messages.stream().filter(m -> m.messageId == id).findFirst().orElse(null);
    }

    public void deleteMessage(int id) {
        messages.removeIf(m -> m.messageId == id);
        saveToFile();
    }

    public void injectDummyData() {
        messages.add(new MessageDB.Message(1, 102,101, "HELLO! SHREESTHI...." ));
        messages.add(new MessageDB.Message(2, 101, 102, "HELLO PRANATI..."));
        saveToFile();
    }

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                messages = (List<Message>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}



class ProjectDB {
     public static class Project implements Serializable {
         public int projectId;
         public String title;
         public String description;
         public int customerId;

         public Project(int projectId, String title, String description, int customerId) {
             this.projectId = projectId;
             this.title = title;
             this.description = description;
             this.customerId = customerId;
         }

         public String toString() {
             return projectId + " | " + title + " | " + description + " | Customer ID: " + customerId;
         }
     }

     private List<Project> projects = new ArrayList<>();
     private final String filePath = "projects.db";

     public void addProject(Project p) {
         projects.add(p);
         saveToFile();
     }

     public void view(){
         projects.forEach(System.out::println);
     }

     public Project getById(int id) {
         return projects.stream().filter(p -> p.projectId == id).findFirst().orElse(null);
     }

     public void deleteProject(int id) {
         projects.removeIf(p -> p.projectId == id);
         saveToFile();
     }

     public void injectDummyData() {
         projects.add(new Project(1, "CRM System", "Developing CRM backend", 1));
         projects.add(new Project(2, "Chat App", "Real-time messaging", 2));
         saveToFile();
     }

     public void saveToFile(){
         try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
             out.writeObject(projects);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void loadFromFile(){
         File file = new File(filePath);
         if (file.exists()) {
             try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                 projects = (List<Project>) in.readObject();
             } catch (IOException | ClassNotFoundException e) {
                 e.printStackTrace();
             }
         }
     }
 }

class LogDB {
    public static class Log implements Serializable {
        public int logId;
        public String timestamp;
        public String message;

        public Log(int logId, String timestamp, String message) {
            this.logId = logId;
            this.timestamp = timestamp;
            this.message = message;
        }

        public String toString() {
            return logId + " | " + timestamp + " | " + message;
        }
    }

    private List<Log> logs = new ArrayList<>();
    private final String filePath = "logs.db";

    public void addLog(Log l) {
        logs.add(l);
        saveToFile();
    }

    public void view() {
        logs.forEach(System.out::println);
    }

    public void injectDummyData() {
        logs.add(new Log(1, "2025-07-06 1:01", "System started"));
        logs.add(new Log(2, "2025-07-06 1:02", "Customer added"));
        saveToFile();
    }

    public void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                logs = (List<Log>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}


class StatsDB{
     public static class Stat implements Serializable {
         public int statId;
         public String metricName;
         public double value;

         public Stat(int statId , String metricName, double value) {
             this.statId = statId;
             this.metricName = metricName;
             this.value = value;
         }

         public String toString(){
             return statId + " | " + metricName + " | " + value;
         }
     }

     private List<Stat> stats = new ArrayList<>();
     private final String filePath = "stats.db";

     public void addStat(Stat s) {
         stats.add(s);
         saveToFile();
     }

     public void view(){
         stats.forEach(System.out::println);
     }

     public void injectDummyData(){
         stats.add(new Stat(1, "Daily Users", 145.5));
         stats.add(new Stat(2, "Messages Sent", 3432));
         saveToFile();
     }

     public void saveToFile(){
         try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
             out.writeObject(stats);
         }
         catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void loadFromFile(){
         File file = new File(filePath);
         if (file.exists()) {
             try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                 stats = (List<Stat>) in.readObject();
             }
             catch (IOException | ClassNotFoundException e) {
                 e.printStackTrace();
             }
         }
     }
}


class APIsDB {
     public static class API implements Serializable {
         public int apiId;
         public String endpoint;
         public String method;
         public String description;

         public API(int apiId, String endpoint, String method, String description) {
             this.apiId = apiId;
             this.endpoint = endpoint;
             this.method = method;
             this.description = description;
         }

         public String toString() {
             return apiId + " | " + method +" " + endpoint + " | " + description;
         }
     }

     private List<API> apis = new ArrayList<>();
     private final String filePath = "apis.db";

     public void addAPI(API a) {
         apis.add(a);
         saveToFile();
     }

     public void view(){
         apis.forEach(System.out::println);
     }

     public void injectDummyData() {
         apis.add(new API(1, "/login", "POST", "User login"));
         apis.add(new API(2, "/signup", "POST", "User registration"));
         saveToFile();
     }

     public void saveToFile() {
         try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
             out.writeObject(apis);
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void loadFromFile(){
         File file = new File(filePath);
         if (file.exists()) {
             try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
                 apis = (List<API>) in.readObject();
             } catch (IOException | ClassNotFoundException e) {
                 e.printStackTrace();
             }
         }
     }
}

class TempManager {
     public static CustomerDB customerDB = new CustomerDB();
     public static MessageDB messageDB = new MessageDB();
     public static ProjectDB projectDB = new ProjectDB();
     public static LogDB logDB = new LogDB();
     public static StatsDB statsDB = new StatsDB();
     public static APIsDB apisDB = new APIsDB();

     public static void initAll() {
         customerDB.loadFromFile();
         messageDB.loadFromFile();
         projectDB.loadFromFile();
         logDB.loadFromFile();
         statsDB.loadFromFile();
         apisDB.loadFromFile();
     }

     public static void injectDummyData(){
         customerDB.injectDummyData();
         messageDB.injectDummyData();
         projectDB.injectDummyData();
         logDB.injectDummyData();
         statsDB.injectDummyData();
         apisDB.injectDummyData();
     }
}

public class Task3 {
     private static final Scanner sc = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(Task3.class);

    public static void main(String[] args) {
         TempManager.initAll();
         boolean running = true;

         while (running) {
             showMainMenu();
             int choice = getInt("Enter your choice: ");

             switch (choice) {
                 case 1:
                     TempManager.customerDB.view();
                     break;
                 case 2:
                     TempManager.messageDB.view();
                     break;
                 case 3:
                     TempManager.projectDB.view();
                     break;
                 case 4:
                     TempManager.logDB.view();
                     break;
                 case 5:
                     TempManager.statsDB.view();
                     break;
                 case 6:
                     TempManager.apisDB.view();
                     break;
                 case 7:{
                     TempManager.injectDummyData();
                     System.out.println("Dummy Data Injected.");
                     break;
                 }
                 case 0:{
                     System.out.println("Exiting....");
                     running = false;
                     break;
                 }
                 default:
                     System.out.println("Invalid choice.");
                     break;
             }
         }
     }

     private static void showMainMenu(){
         System.out.println("""
                 \n ============= TEMPDB TERMINAL ===============
                 1. View Customers
                 2. View Messages
                 3. View Projects
                 4. View Lgs
                 5. View Stats
                 6. View APIs
                 7. Inject Dummy Data
                 0. Exit
                 """);
     }

     private static int getInt(String prompt) {
         System.out.println(prompt);
         return Integer.parseInt(sc.nextLine());
     }
}