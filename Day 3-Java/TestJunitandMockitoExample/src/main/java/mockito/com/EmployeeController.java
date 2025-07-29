package mockito.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    static final String URL = "jdbc:postgresql://localhost:5432/testdb";
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            EmployeeRepository repo = new EmployeeRepository(conn);
            EmployeeService service = new EmployeeService(repo);

            int choice;
            do {
                System.out.println("\n=== Employee Management System ===");
                System.out.println("1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Salary: ");
                        double salary = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        service.addEmployee(new Employee(0, name, salary, email));
                        System.out.println("Employee added.");
                    }
                    case 2 -> {
                        List<Employee> employees = service.getAllEmployees();
                        employees.forEach(System.out::println);
                    }
                    case 3 -> {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("New Name: ");
                        String name = sc.nextLine();
                        System.out.print("New Salary: ");
                        double salary = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("New Email: ");
                        String email = sc.nextLine();
                        boolean updated = service.updateEmployee(new Employee(id, name, salary, email));
                        System.out.println(updated ? "Updated." : "Not Found.");
                    }
                    case 4 -> {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        boolean deleted = service.deleteEmployee(id);
                        System.out.println(deleted ? "Deleted." : "Not Found.");
                    }
                    case 5 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice.");
                }
            } while (choice != 5);

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
