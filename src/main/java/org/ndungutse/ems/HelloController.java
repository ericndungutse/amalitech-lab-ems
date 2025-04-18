package org.ndungutse.ems;

import java.io.IOException;
import java.util.List;

import org.ndungutse.ems.exceptions.AppException;
import org.ndungutse.ems.models.Department;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;
import org.ndungutse.ems.utils.DialogUtility;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelloController {
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML
    private ComboBox<Department> departmentAvgComboBox;
    @FXML
    private TextField minSalaryField;

    @FXML
    private Label averageSalaryLabel;

    @FXML
    private TextField maxSalaryField;

    @FXML
    private TextField minRatingField;

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
    private TextField searchField;
    @FXML
    private TableColumn<Employee<Integer>, Boolean> statusColumn;

    private final EmployeeCollection<Integer> employeeCollection = AppContext.getEmployeeCollection();

    @FXML
    public void initialize() {
        departmentAvgComboBox.getItems().addAll(Department.values());
        departmentComboBox.getItems().addAll(Department.values());
        employeeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getEmployeeId()).asObject());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        departmentColumn
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment().toString()));
        salaryColumn.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalary()).asObject());
        ratingColumn.setCellValueFactory(
                data -> new SimpleDoubleProperty(data.getValue().getPerformanceRating()).asObject());
        experienceColumn.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getYearsOfExperience()).asObject());
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
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setResizable(false);

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
            DialogUtility.showAlert("No employee selected", "Please select an employee to delete.");
            return;
        }

        boolean result = DialogUtility.showConfirmation("Confirm Deletion", "Are you sure you want to delete employee, "
                + selectedEmployee.getName() + "? This action cannot be undone.");
        if (result) {
            AppContext.getEmployeeCollection().removeEmployee(selectedEmployee.getEmployeeId());
            refreshTable();
        }
    }

    public void handleSearchByName(ActionEvent event) {
        String searchTerm = searchField.getText();
        if (searchTerm == null || searchTerm.isEmpty()) {
            return;
        }

        List<Employee<Integer>> results = AppContext.getEmployeeCollection().getEmployeeByName(searchTerm);
        employeeTable.getItems().setAll(results);
    }

    public void handleClearSearch(ActionEvent event) {
        searchField.clear();
        employeeTable.getItems().setAll(AppContext.getEmployeeCollection().getAllEmployees());
    }

    public void handleApplyFilters(ActionEvent event) {
        try {
            Department department = departmentComboBox.getValue();
            Double minSalary = parseDoubleOrNull(minSalaryField.getText());
            Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
            Double minRating = parseDoubleOrNull(minRatingField.getText());

            List<Employee<Integer>> filtered = HelloApplication.employeeCollection.filter(
                    department, minSalary, maxSalary, minRating);
            employeeTable.getItems().setAll(filtered);
        } catch (AppException e) {
            DialogUtility.showErrorAlert("Filtering Error", e.getMessage());
        }
    }

    private Double parseDoubleOrNull(String input) {
        try {
            if (input == null || input.isEmpty()) {
                return null;
            } else {
                return Double.parseDouble(input);
            }
        } catch (NumberFormatException e) {
            DialogUtility.showErrorAlert("Input Error", "Please enter valid numbers for filters.");
        }
        return null;
    }

    // CLear Filter Fields
    public void handleClearAllFilters(ActionEvent event) {
        departmentComboBox.getSelectionModel().clearSelection();
        minSalaryField.clear();
        maxSalaryField.clear();
        minRatingField.clear();
        this.refreshTable();
    }

    public void handleSortByExperience(ActionEvent event) {
        List<Employee<Integer>> employeesByExperience = AppContext.getEmployeeCollection()
                .sortEmployeesByExperienceDesc();
        employeeTable.getItems().setAll(employeesByExperience);
    }

    public void handleSortBySalary(ActionEvent event) {
        List<Employee<Integer>> employeesBySalary = AppContext.getEmployeeCollection().sortEmployeesBySalaryDesc();
        employeeTable.getItems().setAll(employeesBySalary);
    }

    public void handleSortByRating(ActionEvent event) {
        List<Employee<Integer>> employeesByRating = AppContext.getEmployeeCollection()
                .sortEmployeesByPerformanceRatingDesc();
        employeeTable.getItems().setAll(employeesByRating);
    }

    public void handleTop5HighestPaid(ActionEvent event) {
        List<Employee<Integer>> top5HighestPaid = AppContext.getEmployeeCollection().getTop5HighestPaidEmployees();
        employeeTable.getItems().setAll(top5HighestPaid);
    }

    public void handleAverageSalaryByDepartment(ActionEvent event) {
        Department selectedDepartment = departmentAvgComboBox.getValue();
        Double avg = AppContext.getEmployeeCollection().calculateAverageSalaryByDepartment(selectedDepartment);
        averageSalaryLabel.setText("$" + String.format("%.2f", avg));
    }
}