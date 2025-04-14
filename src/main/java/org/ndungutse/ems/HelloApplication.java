package org.ndungutse.ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.ndungutse.ems.models.DepartementEnum;
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

        Employee<Integer> emp1 = new Employee<Integer>(1, "Eric", DepartementEnum.IT, 1500, 4.5, 3, true);

        EmployeeCollection employeeCollection = new EmployeeCollection<>();

        employeeCollection.addEmployee(emp1);

        System.out.println(employeeCollection.getEmployees());

    }
}
