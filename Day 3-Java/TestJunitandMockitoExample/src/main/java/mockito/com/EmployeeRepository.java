package mockito.com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements EmployeeRepositoryInterface {
    private Connection conn;

    public EmployeeRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Employee emp) throws SQLException {
        String sql = "INSERT INTO employee (name, salary, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setDouble(2, emp.getSalary());
            pstmt.setString(3, emp.getEmail());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"),
                        rs.getString("email")
                ));
            }
        }
        return list;
    }

    @Override
    public boolean update(Employee emp) throws SQLException {
        String sql = "UPDATE employee SET name=?, salary=?, email=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getName());
            pstmt.setDouble(2, emp.getSalary());
            pstmt.setString(3, emp.getEmail());
            pstmt.setInt(4, emp.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM employee WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}
