package dev.anurag;


import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class AdminService {
    private StudentDAO dao = new StudentDAO();

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";
        try (var conn = DatabaseConnection.getConnection();
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            var rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addStudent(Student s) {
        dao.addStudent(s);
    }

    public void showAllStudents() {
        List<Student> list = dao.getAllStudents();
        for (Student s : list) {
            printStudent(s);
        }
    }

    public void updateStudent(Student s) {
        dao.updateStudent(s);
    }

    public void deleteStudent(String rollNo) {
        dao.deleteStudent(rollNo);
    }

    public void searchByDepartment(String department) {
        List<Student> list = dao.searchByDepartment(department);
        list.forEach(this::printStudent);
    }

    public void searchByMarks(int min, int max) {
        List<Student> list = dao.searchByMarksRange(min, max);
        list.forEach(this::printStudent);
    }

    public void showStatistics() {
        System.out.println("📊 Total Students: " + dao.getTotalStudents());
        dao.getHighestLowestMarks();
        System.out.println("🏫 Department-wise Count:");
        Map<String, Integer> map = dao.getDepartmentWiseCount();
        map.forEach((dept, count) -> System.out.println("  " + dept + ": " + count));
    }

    public void exportToCSV(String fileName) {
        List<Student> list = dao.getAllStudents();
        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.println("ID,Name,RollNo,Dept,Email,Phone,Marks,Subject1,Subject2,Subject3,Grade");
            for (Student s : list) {
                pw.printf("%d,%s,%s,%s,%s,%s,%d,%d,%d,%d,%s%n",
                        s.getId(), s.getName(), s.getRollNo(), s.getDepartment(),
                        s.getEmail(), s.getPhone(), s.getMarks(),
                        s.getSubject1(), s.getSubject2(), s.getSubject3(), s.calculateGrade());
            }
            System.out.println("✅ Exported to " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printStudent(Student s) {
        System.out.println("--------------------------------------------------");
        System.out.println("ID       : " + s.getId());
        System.out.println("Name     : " + s.getName());
        System.out.println("Roll No  : " + s.getRollNo());
        System.out.println("Dept     : " + s.getDepartment());
        System.out.println("Email    : " + s.getEmail());
        System.out.println("Phone    : " + s.getPhone());
        System.out.println("Marks    : " + s.getMarks());
        System.out.println("Subjects : " + s.getSubject1() + ", " + s.getSubject2() + ", " + s.getSubject3());
        System.out.println("Average  : " + s.calculateAverage());
        System.out.println("Grade    : " + s.calculateGrade());
    }
}
