package org.ndungutse.ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.ndungutse.ems.models.Department;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // launch();

        Employee<Integer> emp1 = new Employee<Integer>(1, "Eric", Department.IT, 1500, 4.5, 3, true);
        Employee<Integer> emp2 = new Employee<Integer>(2, "Emp2", Department.FINANCE, 1500, 4.5, 3, true);
        Employee<Integer> emp3 = new Employee<Integer>(3, "Emp3", Department.HR, 1500, 4.5, 3, true);

        EmployeeCollection<Integer> employeeCollection = new EmployeeCollection<>();

        employeeCollection.addEmployee(emp1);
        employeeCollection.addEmployee(emp2);
        employeeCollection.addEmployee(emp3);
        System.out.println("Before remove");
        System.out.println(employeeCollection.getEmployees());
        System.out.println("********************** After remove *********************");
        employeeCollection.removeEmployee(emp1.getEmployeeId());
        System.out.println(employeeCollection.getEmployees());

    }
}
