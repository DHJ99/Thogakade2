package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import model.Customer;
import db.DBConnection;

import java.util.List;

public class DeleteCustomerFormController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSearch;

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

    List<Customer> customersList = DBConnection.getInstance().getConnection();
    private Customer selectedCustomer;

    @FXML
    void btnSearch(ActionEvent event) {

        for(Customer customer : customersList){
            String searchId=txtSearchId.getText();

            if(customer.getId().equals(searchId)){
                selectedCustomer = customer;

                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                dateDob.setValue(customer.getDob());
                txtSalary.setText(String.valueOf(customer.getSalary()));
                txtAddress.setText(customer.getAddress());
                txtCity.setText(customer.getCity());
                txtProvince.setText(customer.getProvince());
                txtPostalCode.setText(customer.getPostalCode());

                return;
            }
        }
        clearFields();
        selectedCustomer = null;
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
    @FXML
    void btnDelete(ActionEvent event) {
        if (selectedCustomer != null) {
            boolean removed = customersList.remove(selectedCustomer);
            if (removed) {
                showAlert("Success", "Customer deleted successfully.");
                clearFields();
                selectedCustomer = null;
            } else {
                showAlert("Error", "Failed to delete customer.");
                clearFields();
            }
        } else {
            showAlert("Error", "No customer selected. Please search for a customer first.");
            clearFields();
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
