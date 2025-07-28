package mockito.com;


import java.sql.SQLException;
import java.util.List;

public interface EmployeeRepositoryInterface {
    void save(Employee emp) throws SQLException;
    List<Employee> findAll() throws SQLException;
    boolean update(Employee emp) throws SQLException;
    boolean delete(int id) throws SQLException;
}