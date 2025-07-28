package com.db.code.test;
import com.db.code.Employee;
import com.db.code.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {
    private EmployeeService service = new EmployeeService();

        @BeforeEach
        void setUp() {
            service = new EmployeeService();
        }

        @Test
        void testBonusCalculation() {
            Employee emp = new Employee(101, "Ashu", 40000);
            double bonus = service.calculateBonus(emp);

            assertEquals(4000, bonus);
            assertNotEquals(0, bonus);
            assertTrue(bonus > 0);
            assertFalse(bonus < 0);
        }

        @Test
        void testLevelCheck() {
            Employee junior = new Employee(102, "Mini", 30000);
            Employee senior = new Employee(103, "Max", 70000);

            assertEquals("Junior", service.getEmployeeLevel(junior));
            assertEquals("Senior", service.getEmployeeLevel(senior));
        }

        @Test
        void testNegativeSalaryThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> new Employee(104, "Tom", -5000));
        }

    @Test
    void testBonusThrowsForNegativeSalary() {
        Employee emp = new Employee(105, "Jerry", 20000);

        assertThrows(IllegalArgumentException.class, () -> {
            emp.setSalary(-100); // now this is inside assertThrows
            service.calculateBonus(emp); // optional, this won’t be reached if setter fails
        });
    }

    @Test
        void testAllAssertions() {
            Employee emp = new Employee(106, "Lucky", 50000);

            assertAll("Employee Bonus and Level Checks",
                    () -> assertEquals(5000, service.calculateBonus(emp)),
                    () -> assertTrue(service.getEmployeeLevel(emp).equals("Senior") || service.getEmployeeLevel(emp).equals("Junior")),
                    () -> assertNotNull(emp.getName()),
                    () -> assertNotEquals("", emp.getName())
            );
        }

        @Test
        void testTimeout() {
            assertTimeout(Duration.ofMillis(2000), () -> {
                service.slowOperation(); // Simulate a delay method in service
            });
        }
    }