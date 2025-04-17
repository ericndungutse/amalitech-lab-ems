package org.ndungutse.ems;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<Employee<Integer>> employeeTable;

    @FXML
    private TableColumn<Employee<Integer>, Integer> idColumn;

    @FXML
    private TableColumn<Employee<Integer>, String> nameColumn;

    @FXML
    private TableColumn<Employee<Integer>, String> departmentColumn;

    @FXML
    private TableColumn<Employee<Integer>, Double> salaryColumn;

    @FXML
    private TableColumn<Employee<Integer>, Double> ratingColumn;

    @FXML
    private TableColumn<Employee<Integer>, Integer> experienceColumn;

    @FXML
    private TableColumn<Employee<Integer>, Boolean> statusColumn;

    private final EmployeeCollection<Integer> employeeCollection = CollectionSelector.getEmployeeCollection();


    @FXML
    public void initialize() {
        // Set up column value factories
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEmployeeId()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        departmentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment().toString()));
        salaryColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalary()).asObject());
        ratingColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getPerformanceRating()).asObject());
        experienceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getYearsOfExperience()).asObject());
        statusColumn.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isActive()).asObject());

        // Load employees from your collection
        List<Employee<Integer>> employees = employeeCollection.getAllEmployees();

        // Add to the table
        employeeTable.getItems().setAll(employees);
}

    public void handleNewEmployee(ActionEvent event) throws IOException {
        try {
            // Load the FXML for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-employee.fxml"));
            Parent popupRoot = fxmlLoader.load();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.setTitle("Add New Employee");
            popupStage.setScene(new Scene(popupRoot));
            popupStage.initModality(Modality.APPLICATION_MODAL); // Block other windows until closed
            popupStage.setResizable(false); // Optional

            // Show the popup and wait
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}