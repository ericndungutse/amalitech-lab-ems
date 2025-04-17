package org.ndungutse.ems;

import org.ndungutse.ems.repository.EmployeeCollection;

public class CollectionSelector {
    private final static EmployeeCollection<Integer> employeeCollection = new EmployeeCollection<>();

    public static EmployeeCollection<Integer> getEmployeeCollection() {
        return employeeCollection;
    }
}
