package mockito.com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest1 {

    @Mock
    private EmployeeRepository repo;

    @InjectMocks
    private EmployeeService service;

    private Employee sampleEmp;

    @BeforeEach
    void setUp() {
        /* It tells Mockito to:
           - Create mock instances for fields annotated with @Mock
           - Inject those mocks into fields annotated with @InjectMocks
           - Do this for this test class instance
        */
        MockitoAnnotations.openMocks(this);
        sampleEmp = new Employee(101, "Ashu", 50000.0, "ashu@example.com");
    }

    @Test
    void testAddEmployee() throws SQLException {
        // Given
        // No setup needed, mock method save() returns void

        // When
        service.addEmployee(sampleEmp);

        // Then
        verify(repo, times(1)).save(sampleEmp);
    }

    @Test
    void testGetAllEmployees() throws SQLException {
        // Given
        List<Employee> mockList = Arrays.asList(
                new Employee(1, "John", 40000, "john@example.com"),
                new Employee(2, "Jane", 45000, "jane@example.com")
        );
        when(repo.findAll()).thenReturn(mockList);

        // When
        List<Employee> result = service.getAllEmployees();

        // Then
        assertEquals(2, result.size());
        assertEquals("Jane", result.get(1).getName());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testUpdateEmployee() throws SQLException {
        // Given
        when(repo.update(sampleEmp)).thenReturn(true);

        // When
        boolean updated = service.updateEmployee(sampleEmp);

        // Then
        assertTrue(updated);
        verify(repo).update(sampleEmp);
    }

    @Test
    void testDeleteEmployee() throws SQLException {
        // Given
        when(repo.delete(101)).thenReturn(true);

        // When
        boolean deleted = service.deleteEmployee(101);

        // Then
        assertTrue(deleted);
        verify(repo).delete(101);
    }
}
