package org.ndungutse.ems.models;

public class Employee<T> implements Comparable<Employee<T>> {

    private T employeeId;
    private String name;
    private DepartementEnum department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeId, String name, DepartementEnum department, double salary, double performanceRating,
            int yearsOfExperience, boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }

    @Override
    public int compareTo(Employee<T> o) {
        return Integer.compare(o.yearsOfExperience, this.yearsOfExperience);
    }

    public T getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(T employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartementEnum getDepartment() {
        return department;
    }

    public void setDepartment(DepartementEnum department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", name=" + name + ", department=" + department + ", salary="
                + salary + ", performanceRating=" + performanceRating + ", yearsOfExperience=" + yearsOfExperience
                + ", isActive=" + isActive + "]";
    }
}
