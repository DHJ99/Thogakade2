package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import thogakadePOS.DBConnection;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {

    @FXML
    public JFXButton btnReload;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, String> colContact;

    @FXML
    private TableColumn<Customer, String> colDob;

    @FXML
    private TableColumn<Customer, String> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableView<Customer> tblCustomers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadCustomerData();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
    }

    private void loadCustomerData() {
        ObservableList<Customer> customerObservableList;
        List<Customer> customerList = DBConnection.getInstance().getConnection();
        customerObservableList = FXCollections.observableArrayList(customerList);
        tblCustomers.setItems(customerObservableList);
    }

    @FXML
    void btnReload(ActionEvent ignoredEvent) {
        loadCustomerData();
    }
}