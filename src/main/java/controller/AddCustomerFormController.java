package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.Customer;
import thogakadePOS.DBConnection;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    private JFXButton btnSign;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> title = FXCollections.observableArrayList("Mr", "Mrs", "Miss");
        cmbTitle.setItems(title);
    }

    @FXML
    void btnSign(ActionEvent ignoredEvent) {
        if(validateInputs()){
            List<Customer> customersList = DBConnection.getInstance().getConnection();

            showAlert("Success", "Customer added successfully.");
            clearFields();
        }
    }

    private boolean validateInputs() {
        if (txtId.getText().isEmpty() ||
                cmbTitle.getValue() == null ||
                txtName.getText().isEmpty() ||
                txtAddress.getText().isEmpty() ||
                txtCity.getText().isEmpty() ||
                txtPostalCode.getText().isEmpty() ||
                txtProvince.getText().isEmpty()  ||
                txtSalary.getText().isEmpty()  ||
                dateDob.getValue() == null) {
            showAlert("Error", "All fields are required.");
            return false;
        }

        if (!txtName.getText().matches("[a-zA-Z\\s]+")) {
            showAlert("Name Error", "Name should contain only letters and spaces.");
            return false;
        }

        if (txtAddress.getText().length() < 5) {
            showAlert("Address Error", "Address should be at least 5 characters long.");
            return false;
        }

        if (!isOver18(dateDob.getValue())) {
            showAlert("Birthday Error", "Customer must be at least 18 years old.");
            return false;
        }

        try {
            Double.parseDouble(txtSalary.getText());
        } catch (NumberFormatException e) {
            showAlert("Salary Error", "Salary must be a valid number.");
            return false;
        }

        return true;
    }

    private boolean isOver18(LocalDate birthDate) {
        if (birthDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears() >= 18;
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        dateDob.setValue(null);
        txtSalary.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}