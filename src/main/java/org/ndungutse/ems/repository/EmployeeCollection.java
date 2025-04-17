package org.ndungutse.ems.repository;

import java.util.ArrayList;
import java.util.Collections;
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

    // Employees with minimum performance rating (e.g., rating >= 4.0).
    public List<Employee<T>> getEmployeesByPerformanceRating(double minRating) {
        List<Employee<T>> emp = this.employees.values().stream()
                .filter(employee -> employee.getPerformanceRating() >= minRating).collect(Collectors.toList());
        return emp;
    }

    // Sort employees by years of experience in descending order
    public List<Employee<T>> sortEmployeesByExperienceDesc() {
        List<Employee<T>> employeesList = this.employees.values().stream().collect(Collectors.toList());
        Collections.sort(employeesList);
        displayEmployees(employeesList);
        return employeesList;
    }

    // Sort employees by salary in descending order
    public List<Employee<T>> sortEmployeesBySalaryDesc() {
        List<Employee<T>> employeesList = this.employees.values().stream().collect(Collectors.toList());
        displayEmployees(employeesList);
        employeesList.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        displayEmployees(employeesList);
        return employeesList;
    }

    // Sorts employees by performance rating (best first).
    public List<Employee<T>> sortEmployeesByPerformanceRatingDesc() {
        List<Employee<T>> employeesList = this.employees.values().stream().collect(Collectors.toList());
        Collections.sort(employeesList,
                (e1, e2) -> Double.compare(e2.getPerformanceRating(), e1.getPerformanceRating()));
        displayEmployees(employeesList);
        return employeesList;
    }

    // Give a salary raise to employees with high performance ratings (e.g., rating
    // ≥ 4.5).
    public void giveSalaryRaise(double percentage, double minRating) {
        Iterator<Employee<T>> iterator = this.employees.values().iterator();

        while (iterator.hasNext()) {
            Employee<T> employee = iterator.next();
            if (employee.getPerformanceRating() >= minRating) {
                double newSalary = employee.getSalary() + (employee.getSalary() * percentage / 100);
                employee.setSalary(newSalary);
            }
        }
    }

    // Display All Employees
    public void displayEmployees() {
        // Print header
        System.out.println("======================================================================================");
        System.out.printf("%-12s %-15s %-15s %-10s %-10s %-12s %-8s%n",
                "Employee ID", "Name", "Department", "Salary", "Rating", "Experience", "Active");
        System.out.println("======================================================================================");

        // Iterate and print each employee
        Iterator<Employee<T>> iterator = this.employees.values().iterator();
        while (iterator.hasNext()) {
            Employee<T> emp = iterator.next();
            System.out.printf("%-12s %-15s %-15s $%-9.2f %-10.1f %-12s %-8s%n",
                    emp.getEmployeeId(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getSalary(),
                    emp.getPerformanceRating(),
                    emp.getYearsOfExperience() + " yrs",
                    emp.isActive() ? "Yes" : "No");
        }
    }

    // Display employees
    public void displayEmployees(List<Employee<T>> employees) {
        // Print header
        System.out.println("======================================================================================");

        System.out.printf("%-12s %-15s %-15s %-10s %-10s %-12s %-8s%n",
                "Employee ID", "Name", "Department", "Salary", "Rating", "Experience", "Active");
        System.out.println("======================================================================================");

        // Print each employee
        employees.stream().forEach(e -> System.out.printf("%-12s %-15s %-15s $%-9.2f %-10.1f %-12s %-8s%n",
                e.getEmployeeId(),
                e.getName(),
                e.getDepartment(),
                e.getSalary(),
                e.getPerformanceRating(),
                e.getYearsOfExperience() + " yrs",
                e.isActive() ? "Yes" : "No"));
    }

    @Override
    public String toString() {
        return "EmployeeCollection [employees=" + employees + "]";
    }

}
