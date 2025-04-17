package org.ndungutse.ems;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.ndungutse.ems.models.Department;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployee implements Initializable {
    @FXML
    private ComboBox<Department> departmentComboBox;


    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initializing...");
        departmentComboBox.getItems().addAll(Department.values());
    }
}
