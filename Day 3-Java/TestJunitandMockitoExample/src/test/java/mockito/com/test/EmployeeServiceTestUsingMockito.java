package mockito.com.test;
import mockito.com.Employee;
import mockito.com.EmployeeRepository;
import mockito.com.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

public class EmployeeServiceTestUsingMockito {
    private EmployeeRepository repoMock;
    private EmployeeService service;
    @BeforeEach
    void setUp() {
        repoMock = mock(EmployeeRepository.class);
        service = new EmployeeService(repoMock);
    }
    // Positive Test - Add Employee
    @Test
    void testAddEmployeeSuccess() throws SQLException {
        Employee emp = new Employee(1, "Ashu", 50000, "ashu@example.com");

        service.addEmployee(emp);

        verify(repoMock, times(1)).save(emp);
    }

    //  Negative Test - Add Employee throws SQLException
    @Test
    void testAddEmployeeFailure() throws SQLException {
        Employee emp = new Employee(2, "Mini", 60000, "mini@example.com");

        doThrow(new SQLException("DB Error")).when(repoMock).save(emp);

        assertThrows(SQLException.class, () -> service.addEmployee(emp));
        verify(repoMock).save(emp);
    }

    //  Positive Test - Get All Employees
    @Test
    void testGetAllEmployeesSuccess() throws SQLException {
        List<Employee> mockList = Arrays.asList(
                new Employee(1, "Ashu", 50000, "ashu@example.com"),
                new Employee(2, "Mini", 60000, "mini@example.com")
        );

        when(repoMock.findAll()).thenReturn(mockList);

        List<Employee> result = service.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("Mini", result.get(1).getName());
        verify(repoMock).findAll();
    }

    //  Negative Test - Get All Employees returns empty list
    @Test
    void testGetAllEmployeesEmpty() throws SQLException {
        when(repoMock.findAll()).thenReturn(Collections.emptyList());

        List<Employee> result = service.getAllEmployees();

        assertTrue(result.isEmpty());
        verify(repoMock).findAll();
    }

    //  Negative Test - Get All Employees throws SQLException
    @Test
    void testGetAllEmployeesThrowsException() throws SQLException {
        when(repoMock.findAll()).thenThrow(new SQLException("Database connection failed"));

        assertThrows(SQLException.class, () -> service.getAllEmployees());
        verify(repoMock).findAll();
    }

    //  Positive Test - Update Employee
    @Test
    void testUpdateEmployeeSuccess() throws SQLException {
        Employee emp = new Employee(3, "Raj", 45000, "raj@example.com");

        when(repoMock.update(emp)).thenReturn(true);

        assertTrue(service.updateEmployee(emp));
        verify(repoMock).update(emp);
    }

    //  Negative Test - Update Employee fails
    @Test
    void testUpdateEmployeeFailure() throws SQLException {
        Employee emp = new Employee(3, "Raj", 45000, "raj@example.com");

        when(repoMock.update(emp)).thenReturn(false);

        assertFalse(service.updateEmployee(emp));
        verify(repoMock).update(emp);
    }

    //  Negative Test - Update Employee throws SQLException
    @Test
    void testUpdateEmployeeThrowsException() throws SQLException {
        Employee emp = new Employee(3, "Raj", 45000, "raj@example.com");

        when(repoMock.update(emp)).thenThrow(new SQLException("Update failed"));

        assertThrows(SQLException.class, () -> service.updateEmployee(emp));
        verify(repoMock).update(emp);
    }

    // ✅ Positive Test - Delete Employee
    @Test
    void testDeleteEmployeeSuccess() throws SQLException {
        when(repoMock.delete(1)).thenReturn(true);

        assertTrue(service.deleteEmployee(1));
        verify(repoMock).delete(1);
    }

    //  Negative Test - Delete Employee fails
    @Test
    void testDeleteEmployeeFailure() throws SQLException {
        when(repoMock.delete(999)).thenReturn(false); // assume ID 999 doesn't exist

        assertFalse(service.deleteEmployee(999));
        verify(repoMock).delete(999);
    }

    //  Negative Test - Delete Employee throws SQLException
    @Test
    void testDeleteEmployeeThrowsException() throws SQLException {
        when(repoMock.delete(1)).thenThrow(new SQLException("Delete failed"));

        assertThrows(SQLException.class, () -> service.deleteEmployee(1));
        verify(repoMock).delete(1);
    }
}

