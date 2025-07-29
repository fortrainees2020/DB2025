    package mockito.com;
    import java.sql.SQLException;
    import java.util.List;

    public class EmployeeService {
        private EmployeeRepository repo;

        public EmployeeService(EmployeeRepository repo) {
            this.repo = repo;
        }

        public void addEmployee(Employee emp) throws SQLException {
            repo.save(emp);
        }

        public List<Employee> getAllEmployees() throws SQLException {
            return repo.findAll();
        }

        public boolean updateEmployee(Employee emp) throws SQLException {
            return repo.update(emp);
        }

        public boolean deleteEmployee(int id) throws SQLException {
            return repo.delete(id);
        }
    }
