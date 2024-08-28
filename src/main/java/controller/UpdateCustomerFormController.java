package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.Customer;
import thogakadePOS.DBConnection;

import java.util.List;

public class UpdateCustomerFormController {

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

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

    @FXML
    private JFXTextField txtSearchId;

    private List<Customer> customersList;
    private Customer selectedCustomer;

    public void initialize() {
        customersList = DBConnection.getInstance().getCustomers();
    }

    @FXML
    void btnSearch(ActionEvent ignoredEvent) {
        String searchId = txtSearchId.getText();
        selectedCustomer = customersList.stream()
                .filter(customer -> customer.getId().equals(searchId))
                .findFirst()
                .orElse(null);

        if (selectedCustomer != null) {
            populateFields(selectedCustomer);
        } else {
            showAlert("Error 1", "Customer not found.");
            clearFields();
        }
    }

    @FXML
    void btnUpdate(ActionEvent ignoredEvent) {
        if (selectedCustomer != null) {
            if (validateInputs()) {
                updateCustomer();
                int index = customersList.indexOf(selectedCustomer);
                if (index != -1) {
                    customersList.set(index, selectedCustomer);
                    DBConnection.getInstance().updateCustomer(selectedCustomer);
                    showAlert("Success", "Customer updated successfully.");
                    clearFields();
                } else {
                    showAlert("Error 2", "Failed to update customer in the list.");
                }
            }
        } else {
            showAlert("Error 3", "No customer selected. Please search for a customer first.");
        }
    }

    private void updateCustomer() {
        selectedCustomer.setName(txtName.getText());
        selectedCustomer.setAddress(txtAddress.getText());
        selectedCustomer.setCity(txtCity.getText());
        selectedCustomer.setProvince(txtProvince.getText());
        selectedCustomer.setPostalCode(txtPostalCode.getText());
        selectedCustomer.setSalary(Double.parseDouble(txtSalary.getText()));
        selectedCustomer.setDob(dateDob.getValue());

    }

    private void populateFields(Customer customer) {
        txtId.setText(customer.getId());
        txtName.setText(customer.getName());
        dateDob.setValue(customer.getDob());
        txtSalary.setText(String.valueOf(customer.getSalary()));
        txtAddress.setText(customer.getAddress());
        txtCity.setText(customer.getCity());
        txtProvince.setText(customer.getProvince());
        txtPostalCode.setText(customer.getPostalCode());
    }

    private boolean validateInputs() {
        if (txtName.getText().isEmpty() ||
                txtAddress.getText().isEmpty() ||
                txtCity.getText().isEmpty() ||
                txtPostalCode.getText().isEmpty() ||
                txtProvince.getText().isEmpty() ||
                txtSalary.getText().isEmpty() ||
                dateDob.getValue() == null ) {
            showAlert("Error", "All fields are required.");
            return false;
        }

        try {
            Double.parseDouble(txtSalary.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Salary must be a valid number.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtSearchId.clear();
        txtId.clear();
        txtName.clear();
        dateDob.setValue(null);
        txtSalary.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        selectedCustomer = null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}