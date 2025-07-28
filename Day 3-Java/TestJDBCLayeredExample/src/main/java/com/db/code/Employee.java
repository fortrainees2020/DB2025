package com.db.code;

public class Employee {
        private int id;
        private String name;
        private double salary;

        public Employee() {}

        public Employee(int id, String name, double salary) {
            if (salary < 0) {
                throw new IllegalArgumentException("Salary cannot be negative");
            }
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public double getSalary() { return salary; }

        // Setters
        public void setSalary(double salary) {
            if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative");
            this.salary = salary;
        }
    }

