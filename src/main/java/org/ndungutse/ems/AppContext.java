package org.ndungutse.ems;

import javafx.scene.control.Alert;
import org.ndungutse.ems.repository.EmployeeCollection;

public class AppContext {
    private final static EmployeeCollection<Integer> employeeCollection = new EmployeeCollection<>();

    public static EmployeeCollection<Integer> getEmployeeCollection() {
        return employeeCollection;
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
