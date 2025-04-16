package org.ndungutse.ems.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.ndungutse.ems.models.Department;
import org.ndungutse.ems.models.Employee;

public class EmployeeCollection<T> {
    private HashMap<T, Employee<T>> employees = new HashMap<>();

    // Add employee
    public void addEmployee(Employee<T> employee) {
        // Check if employee already exists
        if (this.employees.get(employee.getEmployeeId()) != null)
            throw new RuntimeException("Employee already exists");

        // Save new employee
        this.employees.put(employee.getEmployeeId(), employee);
    }

    // Remove Employee
    public void removeEmployee(T employeeId) {
        // Check if employee with id exists
        if (this.employees.get(employeeId) == null)
            throw new RuntimeException("Employee with id: " + employeeId + "does not exists.");

        // Remove the employee
        employees.remove(employeeId);
    }

    // Update Employee Details
    public void updateEmployeeEmployeeDetails(T employeeId, String field, Object newValue) {
        // Find the employee
        Employee<T> employeeToUpdate = this.employees.get(employeeId);

        // Check if employee exists
        if (employeeToUpdate == null)
            throw new RuntimeException("Employee with id: \" + employeeId + \"does not exists.");

        // Restrict Id Update
        if (field == "employeeId")
            throw new RuntimeException("Employee Id cannot be update.");

        // Update employee field
        switch (field) {
            case "name":
                employeeToUpdate.setName((String) newValue);
                break;
            case "department":
                employeeToUpdate.setDepartment((Department) newValue);
                break;
            case "salary":
                double newSalary;
                if (newValue instanceof Integer) {
                    newSalary = ((Integer) newValue).doubleValue();
                } else if (newValue instanceof Double) {
                    newSalary = (double) newValue;
                } else {
                    throw new IllegalArgumentException("Unsupported type of salary.");
                }
                employeeToUpdate.setSalary(newSalary);
                break;
            case "performanceRating":
                employeeToUpdate.setPerformanceRating((double) newValue);
                break;
            case "yearsOfExperience":
                employeeToUpdate.setYearsOfExperience((Integer) newValue);
                break;
            case "isActive":
                employeeToUpdate.setActive((Boolean) newValue);
                break;
            default:
                throw new RuntimeException("Invalid field.");
        }
    }

    // Get All employees and display them
    public List<Employee<T>> getAllEmployees() {
        displayEmployees();
        return new ArrayList<>(this.employees.values());
    }

    // Get employees by department
    public List<Employee<T>> getEmployeesBuDepartment(Department department) {
        List<Employee<T>> departmentEmployees = this.employees.values().stream()
                .filter((employee) -> employee.getDepartment().equals(department)).collect(Collectors.toList());

        return departmentEmployees;
    }

    // Get Employees by name based on a search term
    public List<Employee<T>> getEmployeeByName(String name) {
        List<Employee<T>> employeesByName = this.employees.values().stream()
                .filter(employee -> employee.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return employeesByName;
    }

    // Get EMployees based on salary
    public List<Employee<T>> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        List<Employee<T>> emp = this.employees.values().stream()
                .filter(employee -> employee.getSalary() >= minSalary && employee.getSalary() <= maxSalary)
                .collect(Collectors.toList());

        return emp;
    }

    // Display Employees
    public void displayEmployees() {
        Iterator<Employee<T>> iterator = this.employees.values().iterator();
        while (iterator.hasNext()) {
            Employee<T> emp = iterator.next();
            System.out.println("==================================");
            System.out.println("Employee ID: " + emp.getEmployeeId());
            System.out.println("Name       : " + emp.getName());
            System.out.println("Department : " + emp.getDepartment());
            System.out.println("Salary     : $" + emp.getSalary());
            System.out.println("Rating     : " + emp.getPerformanceRating());
            System.out.println("Experience : " + emp.getYearsOfExperience() + " years");
            System.out.println("Active     : " + (emp.isActive() ? "Yes" : "No"));
        }
    }

    @Override
    public String toString() {
        return "EmployeeCollection [employees=" + employees + "]";
    }

}
