package com.mschiretech.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CrmBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmBackendApplication.class, args);
		Scanner sc = new Scanner(System.in);
		boolean running = true;

		while(running){
			System.out.println("\n--------- Lists of Endpoints--------");
			System.out.println("1. Sign up");
			System.out.println("2.  Login");
			System.out.println("3. Manage Leads");
			System.out.println("4. Manage Tasks");
			System.out.println("5. Manage Employees");
			System.out.println("6. Message & Comments");
			System.out.println("7. Logout");
			System.out.println("8. Exit");
			System.out.print("Select an option: ");

			int list = sc.nextInt();
			sc.nextLine();

			switch (list) {
				case 1:
					System.out.println("Calling/ Sign up");
					break;
				case 2:
					System.out.println("Calling/ Login");
					break;
				case 3:
					System.out.println("Calling/ Leads");
					break;
				case 4:
					System.out.println("Calling/ Tasks");
					break;
				case 5:
					System.out.println("Calling/ Employees");
					break;
				case 6:
					System.out.println("Calling/ Messages");
					break;
				case 7:
					System.out.println("Logout");
					break;
				case 8:
					System.out.println("Exiting");
					running = false;
					break;
				default:
					System.out.println("Invalid choice. Try Again");
			}
		}
		sc.close();
	}


}

// the spring application main point !
// hello world !