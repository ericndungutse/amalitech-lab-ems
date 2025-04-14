package org.ndungutse.ems.repository;

import java.util.HashMap;

import org.ndungutse.ems.models.Employee;

public class EmployeeCollection<T> {
    private HashMap<T, Employee<T>> employees = new HashMap<>();

    // Add employee
    public void addEmployee(Employee<T> employee) {
        // Check if employee aleady exists
        if (employees.get(employee.getEmployeeId()) != null)
            throw new RuntimeException("Employee already exists");

        // Save new employee
        this.employees.put(employee.getEmployeeId(), employee);
    }

    // Remove Employee
    public void removeEmployee(T employeeId) {
        // Check if employee with id exists
        if (employees.get(employeeId) == null)
            throw new RuntimeException("Employee with id: " + employeeId + "does not exists.");

        // Remove the employee
        employees.remove(employeeId);
    }

    public HashMap<T, Employee<T>> getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "EmployeeCollection [employees=" + employees + "]";
    }

}
