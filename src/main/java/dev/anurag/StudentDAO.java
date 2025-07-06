package dev.anurag;

import java.sql.*;
import java.util.*;


public class StudentDAO {

    public void addStudent(Student s) {
        String sql = "INSERT INTO students(name, roll_no, department, email, phone, marks, subject1, subject2, subject3) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRollNo());
            ps.setString(3, s.getDepartment());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getPhone());
            ps.setInt(6, s.getMarks());
            ps.setInt(7, s.getSubject1());
            ps.setInt(8, s.getSubject2());
            ps.setInt(9, s.getSubject3());
            ps.executeUpdate();
            System.out.println("Student added!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStudent(Student s) {
        String sql = "UPDATE students SET name=?, department=?, email=?, phone=?, marks=?, subject1=?, subject2=?, subject3=? WHERE roll_no=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            ps.setInt(5, s.getMarks());
            ps.setInt(6, s.getSubject1());
            ps.setInt(7, s.getSubject2());
            ps.setInt(8, s.getSubject3());
            ps.setString(9, s.getRollNo());
            ps.executeUpdate();
            System.out.println("Student updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(String rollNo) {
        String sql = "DELETE FROM students WHERE roll_no=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rollNo);
            ps.executeUpdate();
            System.out.println("Student deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> searchByDepartment(String department) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE department=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> searchByMarksRange(int min, int max) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE marks BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, min);
            ps.setInt(2, max);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalStudents() {
        String sql = "SELECT COUNT(*) AS total FROM students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Integer> getDepartmentWiseCount() {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT department, COUNT(*) as count FROM students GROUP BY department";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                map.put(rs.getString("department"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void getHighestLowestMarks() {
        String sqlMax = "SELECT * FROM students ORDER BY marks DESC LIMIT 1";
        String sqlMin = "SELECT * FROM students ORDER BY marks ASC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rsMax = stmt.executeQuery(sqlMax);
            if (rsMax.next()) {
                System.out.println("🎯 Highest Marks: " + rsMax.getString("name") + " - " + rsMax.getInt("marks"));
            }
            ResultSet rsMin = stmt.executeQuery(sqlMin);
            if (rsMin.next()) {
                System.out.println("📉 Lowest Marks: " + rsMin.getString("name") + " - " + rsMin.getInt("marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Student mapResultSet(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("roll_no"),
                rs.getString("department"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getInt("marks"),
                rs.getInt("subject1"),
                rs.getInt("subject2"),
                rs.getInt("subject3")
        );
    }

}
