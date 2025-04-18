package org.ndungutse.ems;

import java.io.IOException;

import org.ndungutse.ems.models.Department;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
                try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

                        Scene scene = new Scene(fxmlLoader.load());

                        stage.setTitle("Employee Management System");

                        stage.setMaximized(true);

                        stage.setScene(scene);
                        stage.show();

                } catch (IOException e) {
                        System.out.println("Here");
                        System.out.println(e);
                }
        }

        public static final EmployeeCollection<Integer> employeeCollection = AppContext.getEmployeeCollection();

        public static void main(String[] args) {
                employeeCollection.addEmployee(new Employee<>(1, "Eric", Department.IT, 1500, 4.5, 3, true));
                employeeCollection.addEmployee(new Employee<>(2, "Camariza", Department.FINANCE, 3200, 3.9, 5, true));
                employeeCollection.addEmployee(new Employee<>(3, "Lodrigues", Department.HR, 2700, 4.1, 4, true));
                employeeCollection.addEmployee(new Employee<>(4, "Elina", Department.IT, 8000, 4.8, 6, true));
                employeeCollection.addEmployee(new Employee<>(5, "Jean", Department.HR, 900, 3.3, 1, false));
                employeeCollection.addEmployee(new Employee<>(6, "Nadine", Department.FINANCE, 1200, 4.0, 2, true));
                employeeCollection.addEmployee(new Employee<>(7, "Patrick", Department.IT, 5500, 4.6, 7, true));
                employeeCollection.addEmployee(new Employee<>(8, "Sandrine", Department.HR, 2000, 3.6, 3, false));
                employeeCollection.addEmployee(new Employee<>(9, "Albert", Department.FINANCE, 10000, 4.9, 10, true));
                employeeCollection.addEmployee(new Employee<>(10, "Christine", Department.IT, 1500, 4.3, 3, true));
                launch();
        }
}
