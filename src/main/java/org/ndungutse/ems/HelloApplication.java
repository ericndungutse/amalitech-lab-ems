package org.ndungutse.ems;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import org.ndungutse.ems.models.Department;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;

public class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
                try {
                        // Load the FXML file
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

                        // Load the FXML and create the scene
                        Scene scene = new Scene(fxmlLoader.load());

                        // Set the title
                        stage.setTitle("Employee Management System");

                        // Maximize the window to fill the screen
                        stage.setMaximized(true);

                        // Set the scene and show the stage
                        stage.setScene(scene);
                        stage.show();

                }catch (IOException e ){
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

                // // Example test method calls
                // System.out.println("********************* Search by name:
                // 'ric'**************");
                // System.out.println(employeeCollection.getEmployeeByName("ric"));

                // System.out.println("\n ----------------- IT Department
                // Employees:--------------------]=");
                // System.out.println(employeeCollection.getEmployeesBuDepartment(Department.IT));

                // System.out.println("\n &&&&&&&&&&&&&&&&&&&&&& Employees with salary
                // between$1,000 and $5,000:");
                // System.out.println(employeeCollection.getEmployeesBySalaryRange(1000, 5000));

                // employeeCollection.getAllEmployees();

                // System.out.println(
                // "\n *********************** Employees with rating greater than 4.5:
                // ***********************");
                // System.out.println(employeeCollection.getEmployeesByPerformanceRating(4.5));

                // System.out.println("\n");
                // System.out.println("____________ BEFORE SORT BY YEAR OF EXPERIENCE
                // ___________________\n\n");
                // // employeeCollection.getAllEmployees();
                // System.out.println("____________ AFTER SORT BY YEAR OF EXPERIENCE
                // ___________________\n\n");
                // // employeeCollection.sortEmployeesBySalaryDescending();
                // // employeeCollection.sortEmployeesByPerformanceRatingDesc();
                // // employeeCollection.getAllEmployees();
                // // System.out.println("____________ AFTER SORT BY YEAR
                // // OF EXPERIENCE___________________");
                // // employeeCollection.sortEmployeesDescendingByExperience();

                // employeeCollection.displayEmployees(employeeCollection.getEmployeesByDepartment(Department.IT),
                // "IT Department");

                // System.out.println(employeeCollection.calculateAverageSalaryByDepartment(Department.IT));

                // // System.out.println("******************** TOP
                // 5***********************\n\n");

                // //
                // employeeCollection.displayEmployees(employeeCollection.getTop5HighestPaidEmployees());
                // // System.out.println("******************** BOTTOM
                // // 5***********************\n\n");
                // // employeeCollection.giveSalaryRaise(100, 4.6);

                // // employeeCollection.getAllEmployees();
                // employeeCollection.getAllEmployees();

                launch();
        }
}
