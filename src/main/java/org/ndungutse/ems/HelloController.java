package org.ndungutse.ems;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.*;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelloController {
    private int currentPage = 1;
    private boolean lastPage = false;
    @FXML
    Label pageNumberLabel;

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

    private final EmployeeCollection<Integer> employeeCollection = AppContext
            .getEmployeeCollection();

    @FXML
    public void initialize() {
        employeeTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        departmentAvgComboBox.getItems().addAll(Department.values());
        departmentComboBox.getItems().addAll(Department.values());
        idColumn.setCellValueFactory(data -> new SimpleIntegerProperty(
                data.getValue().getEmployeeId()).asObject());
        nameColumn.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getName()));
        departmentColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getDepartment().toString()));
        salaryColumn.setCellValueFactory(
                data -> new SimpleDoubleProperty(data.getValue().getSalary())
                        .asObject());
        ratingColumn.setCellValueFactory(data -> new SimpleDoubleProperty(
                data.getValue().getPerformanceRating()).asObject());
        experienceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(
                data.getValue().getYearsOfExperience()).asObject());
        statusColumn.setCellValueFactory(
                data -> new SimpleBooleanProperty(data.getValue().isActive())
                        .asObject());

        // Load employees from your collection
        Department department = departmentComboBox.getValue();
        Double minSalary = parseDoubleOrNull(minSalaryField.getText());
        Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
        Double minRating = parseDoubleOrNull(minRatingField.getText());
        List<Employee<Integer>> employees = getEmployees(department, minSalary,
                maxSalary, minRating, 1);
        displayEmployees(employees);
    }

    public void handleNewEmployee(ActionEvent event) throws IOException {
        try {
            // Load the FXML for the popup
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("add-employee.fxml"));
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

    // Delete an employee
    @FXML
    private void handleDeleteEmployee() {
        Employee<Integer> selectedEmployee = employeeTable.getSelectionModel()
                .getSelectedItem();

        if (selectedEmployee == null) {
            DialogUtility.showAlert("No employee selected",
                    "Please select an employee to delete.");
            return;
        }

        boolean result = DialogUtility.showConfirmation("Confirm Deletion",
                "Are you sure you want to delete employee, "
                        + selectedEmployee.getName()
                        + "? This action cannot be undone.");

        if (result) {
            AppContext.getEmployeeCollection()
                    .removeEmployee(selectedEmployee.getEmployeeId());

            // Reset pagination state
            currentPage = 1;
            lastPage = false;
            pageNumberLabel.setText("Page: " + currentPage);

            // Apply current filters and refresh the table
            Department department = departmentComboBox.getValue();
            Double minSalary = parseDoubleOrNull(minSalaryField.getText());
            Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
            Double minRating = parseDoubleOrNull(minRatingField.getText());

            List<Employee<Integer>> employees = getEmployees(department,
                    minSalary, maxSalary, minRating, currentPage);
            displayEmployees(employees);
        }
    }

    public void handleSearchByName(ActionEvent event) {
        currentPage = 1;
        lastPage = false;
        pageNumberLabel.setText("Page: " + currentPage);
        String searchTerm = searchField.getText();
        if (searchTerm == null || searchTerm.isEmpty()) {
            return;
        }

        List<Employee<Integer>> results = AppContext.getEmployeeCollection()
                .getEmployeeByName(searchTerm);
        employeeTable.getItems().setAll(results);
    }

    public void handleClearSearch(ActionEvent event) {
        searchField.clear();

        // Reset pagination
        currentPage = 1;
        lastPage = false;
        pageNumberLabel.setText("Page: " + currentPage);

        Department department = departmentComboBox.getValue();
        Double minSalary = parseDoubleOrNull(minSalaryField.getText());
        Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
        Double minRating = parseDoubleOrNull(minRatingField.getText());

        List<Employee<Integer>> employees = getEmployees(department, minSalary,
                maxSalary, minRating, currentPage);
        displayEmployees(employees);
    }

    // Apply Filters
    public void handleApplyFilters(ActionEvent event) {
        try {
            if (currentPage > 1) {
                currentPage = 1;
                pageNumberLabel.setText("Page: " + currentPage);
            }
            Department department = departmentComboBox.getValue();
            Double minSalary = parseDoubleOrNull(minSalaryField.getText());
            Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
            Double minRating = parseDoubleOrNull(minRatingField.getText());

            List<Employee<Integer>> filtered = getEmployees(department,
                    minSalary, maxSalary, minRating, currentPage);

            displayEmployees(filtered);
        } catch (AppException e) {
            DialogUtility.showErrorAlert("Filtering Error", e.getMessage());
        }
    }

    // Parse Filter Fields
    private Double parseDoubleOrNull(String input) {
        try {
            if (input == null || input.isEmpty()) {
                return null;
            } else {
                return Double.parseDouble(input);
            }
        } catch (NumberFormatException e) {
            DialogUtility.showErrorAlert("Input Error",
                    "Please enter valid numeric values for salary and rating filters.\n"
                            + "Ensure 'Min Salary', 'Max Salary', and 'Min Rating' are numbers.");
        }
        return null;
    }

    // CLear Filter Fields
    public void handleClearAllFilters(ActionEvent event) {
        departmentComboBox.getSelectionModel().clearSelection();
        minSalaryField.clear();
        maxSalaryField.clear();
        minRatingField.clear();
        searchField.clear();

        // Reset the table to show all employees
        currentPage = 1;
        lastPage = false;
        List<Employee<Integer>> employees = getEmployees(null, null, null, null,
                currentPage);
        displayEmployees(employees);
    }

    public void handleSortByExperience(ActionEvent event) {
        List<Employee<Integer>> employeesByExperience = AppContext
                .getEmployeeCollection().sortEmployeesByExperienceDesc();
        employeeTable.getItems().setAll(employeesByExperience);
    }

    public void handleSortBySalary(ActionEvent event) {
        List<Employee<Integer>> employeesBySalary = AppContext
                .getEmployeeCollection().sortEmployeesBySalaryDesc();
        employeeTable.getItems().setAll(employeesBySalary);
    }

    public void handleSortByRating(ActionEvent event) {
        List<Employee<Integer>> employeesByRating = AppContext
                .getEmployeeCollection().sortEmployeesByPerformanceRatingDesc();
        employeeTable.getItems().setAll(employeesByRating);
    }

    public void handleTop5HighestPaid(ActionEvent event) {
        List<Employee<Integer>> top5HighestPaid = AppContext
                .getEmployeeCollection().getTop5HighestPaidEmployees();
        employeeTable.getItems().setAll(top5HighestPaid);
    }

    public void handleAverageSalaryByDepartment(ActionEvent event) {
        Department selectedDepartment = departmentAvgComboBox.getValue();
        Double avg = AppContext.getEmployeeCollection()
                .calculateAverageSalaryByDepartment(selectedDepartment);
        averageSalaryLabel.setText("$" + String.format("%.2f", avg));
    }

    // Previous Page
    public void handlePreviousPage(ActionEvent event) {
        if (currentPage > 1) {
            currentPage--;
            pageNumberLabel.setText("Page: " + currentPage);
            if (lastPage)
                lastPage = false;
            Department department = departmentComboBox.getValue();
            Double minSalary = parseDoubleOrNull(minSalaryField.getText());
            Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
            Double minRating = parseDoubleOrNull(minRatingField.getText());
            List<Employee<Integer>> employees = getEmployees(department,
                    minSalary, maxSalary, minRating, currentPage);
            displayEmployees(employees);
        }
    }

    // Next Page
    public void handleNextPage(ActionEvent event) {
        System.out.println(
                "Next Page: " + currentPage + " Last Page: " + lastPage);
        try {
            if (lastPage) {
                return;
            }
            currentPage += 1;
            pageNumberLabel.setText("Page: " + currentPage);
            Department department = departmentComboBox.getValue();
            Double minSalary = parseDoubleOrNull(minSalaryField.getText());
            Double maxSalary = parseDoubleOrNull(maxSalaryField.getText());
            Double minRating = parseDoubleOrNull(minRatingField.getText());
            List<Employee<Integer>> employees = getEmployees(department,
                    minSalary, maxSalary, minRating, currentPage);
            if (employees == null) {
                currentPage -= 1;
                pageNumberLabel.setText("Page: " + currentPage);
            }
            displayEmployees(employees);
        } catch (NumberFormatException e) {
            DialogUtility.showErrorAlert("Filtering Error", e.getMessage());
        } catch (AppException e) {
            DialogUtility.showErrorAlert("Application Error", e.getMessage());
        }
    }

    // Get Employees from collections based on filters and page number
    public List<Employee<Integer>> getEmployees(Department department,
            Double minSalary, Double maxSalary, Double minRating,
            int currentPage) {

        try {
            List<Employee<Integer>> filtered = employeeCollection.filter(
                    department, minSalary, maxSalary, minRating, currentPage);

            // Mark last page if no more employees to display to prevent
            // increasing page numbers
            if (filtered.isEmpty()) {
                lastPage = true;
                return null;
            }

            return filtered;
        } catch (AppException e) {
            DialogUtility.showErrorAlert("Filtering Error", e.getMessage());
            return null;
        }
    }

    // Display employees in a table
    public void displayEmployees(List<Employee<Integer>> employees) {
        System.out.println(
                "Display Employees: " + " " + currentPage + " " + lastPage);
        if (employees == null)
            return;
        employeeTable.getItems().setAll(employees);
    }
}