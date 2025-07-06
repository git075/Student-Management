package dev.anurag;


import java.util.Scanner;

public class UI {
    private final AdminService service = new AdminService();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        System.out.println("🔐 Admin Login");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        if (!service.login(username, password)) {
            System.out.println("❌ Invalid credentials!");
            return;
        }

        while (true) {
            System.out.println("\n📚 Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search by Department");
            System.out.println("6. Search by Marks Range");
            System.out.println("7. Show Statistics");
            System.out.println("8. Export to CSV");
            System.out.println("9. Exit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> service.addStudent(readStudent());
                case 2 -> service.showAllStudents();
                case 3 -> service.updateStudent(readStudent());
                case 4 -> {
                    System.out.print("Enter Roll No to delete: ");
                    service.deleteStudent(sc.nextLine());
                }
                case 5 -> {
                    System.out.print("Enter Department: ");
                    service.searchByDepartment(sc.nextLine());
                }
                case 6 -> {
                    System.out.print("Min Marks: ");
                    int min = Integer.parseInt(sc.nextLine());
                    System.out.print("Max Marks: ");
                    int max = Integer.parseInt(sc.nextLine());
                    service.searchByMarks(min, max);
                }
                case 7 -> service.showStatistics();
                case 8 -> {
                    System.out.print("Enter file name (e.g., students.csv): ");
                    service.exportToCSV(sc.nextLine());
                }
                case 9 -> {
                    System.out.println("👋 Goodbye!");
                    return;
                }
                default -> System.out.println("❌ Invalid option!");
            }
        }
    }

    private Student readStudent() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Roll No: ");
        String roll = sc.nextLine();
        System.out.print("Department: ");
        String dept = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Marks: ");
        int marks = Integer.parseInt(sc.nextLine());
        System.out.print("Subject1: ");
        int s1 = Integer.parseInt(sc.nextLine());
        System.out.print("Subject2: ");
        int s2 = Integer.parseInt(sc.nextLine());
        System.out.print("Subject3: ");
        int s3 = Integer.parseInt(sc.nextLine());

        return new Student(0, name, roll, dept, email, phone, marks, s1, s2, s3);
    }
}
