package com.db.code;

    public class EmployeeService {

        public double calculateBonus(Employee emp) {
            if (emp.getSalary() < 0) {
                throw new IllegalArgumentException("Salary must be non-negative");
            }
            return emp.getSalary() * 0.10;
        }


        public String getEmployeeLevel(Employee emp) {
            return emp.getSalary() >= 50000 ? "Senior" : "Junior";
        }


        public void slowOperation() throws InterruptedException {
            Thread.sleep(1000); // Simulate long-running task (within timeout)
        }
    }


