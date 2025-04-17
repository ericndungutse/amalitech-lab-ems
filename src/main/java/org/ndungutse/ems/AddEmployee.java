package org.ndungutse.ems;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.ndungutse.ems.models.Department;
import org.ndungutse.ems.models.Employee;
import org.ndungutse.ems.repository.EmployeeCollection;


import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployee implements Initializable {
    @FXML
    private ComboBox<Department> departmentComboBox;
    @FXML private TextField nameField;
    @FXML private TextField salaryField;
    @FXML private TextField performanceRatingField;
    @FXML private TextField yearsOfExperienceField;
    @FXML private CheckBox activeCheckBox;
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    private HelloController helloController;
    private final EmployeeCollection<Integer> employeeCollection = AppContext.getEmployeeCollection();

    private int idCounter = 11;
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing...");
        departmentComboBox.getItems().addAll(Department.values());
    }

    public void setHelloController(HelloController helloController) {
        this.helloController = helloController;
    }

    @FXML
    private void handleAddEmployee() {
            String name = nameField.getText().trim();
            Department department = departmentComboBox.getValue();
            double salary = Double.parseDouble(salaryField.getText().trim());
            double performanceRating = Double.parseDouble(performanceRatingField.getText().trim());
            int yearsOfExperience = Integer.parseInt(yearsOfExperienceField.getText().trim());
            boolean isActive = activeCheckBox.isSelected();


            Employee<Integer> employee = new Employee<>(idCounter++,name, department, salary, performanceRating, yearsOfExperience, isActive);
            employeeCollection.addEmployee(employee);

            // Example: print to console or add to a data store
            System.out.println("Added Employee: " + employee);

            employeeCollection.displayEmployees(employeeCollection.getAllEmployees(), "All Employees");


            this.closeModel();
            helloController.refreshTable();
            AppContext.showAlert( "New employee", "Employee added successful");
//            clearForm();




    }

    // CLose Add Employee Form
    @FXML
    private void closeModel() {
        cancelButton.getScene().getWindow().hide();
    }

    // Clear the form
    private void clearForm() {
        nameField.clear();
        departmentComboBox.getSelectionModel().clearSelection();
        salaryField.clear();
        performanceRatingField.clear();
        yearsOfExperienceField.clear();
        activeCheckBox.setSelected(false);
    }
}
