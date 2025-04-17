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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    private final EmployeeCollection<Integer> employeeCollection = AppContext.getEmployeeCollection();


    @FXML
    public void initialize() {
        employeeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

            // Get the AddEmployee controller
            AddEmployee addEmployeeController = fxmlLoader.getController();
            addEmployeeController.setHelloController(this);

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

    // Update table with new data
    public void refreshTable() {
        employeeTable.getItems().setAll(AppContext.getEmployeeCollection().getAllEmployees());
    }

    // Delete an employee
        @FXML
        private void handleDeleteEmployee() {
            Employee<Integer> selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();

            if (selectedEmployee == null) {
                AppContext.showAlert("No employee selected", "Please select an employee to delete.");
                return;
            }

            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirm Deletion");
            confirmation.setHeaderText("Are you sure you want to delete employee, " + selectedEmployee.getName() + "? This action cannot be undone.");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppContext.getEmployeeCollection().removeEmployee(selectedEmployee.getEmployeeId());
                refreshTable();
            }
        }

}