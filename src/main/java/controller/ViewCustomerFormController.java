package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import db.DBConnection;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ViewCustomerFormController implements Initializable {

    @FXML
    private JFXButton btnReload;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TableColumn<Customer, String> colCity;

    @FXML
    private TableColumn<Customer, LocalDate> colDOB;

    @FXML
    private TableColumn<Customer, String> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colPostalCode;

    @FXML
    private TableColumn<Customer, String> colProvince;

    @FXML
    private TableColumn<Customer, Double> colSalary;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTableColumns();
        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setTextToValues(newValue);
        });
        loadCustomers();
    }

    private void initializeTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    private void setTextToValues(Customer customer) {
        if (customer != null) {
            txtId.setText(customer.getId());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
        } else {
            txtId.clear();
            txtName.clear();
            txtAddress.clear();
        }
    }

    @FXML
    public void btnReload(ActionEvent event) {
        loadCustomers();
        loadCustomersFromDatabase();
    }

    private void loadCustomers() {
        List<Customer> customerList = DBConnection.getInstance().getCustomers();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList(customerList);
        tblCustomers.setItems(customerObservableList);
    }

    private void loadCustomersFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/thogakade";
        String user = "root";
        String password = "12345";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement psTm = connection.prepareStatement("SELECT * FROM customer");
             ResultSet resultSet = psTm.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("postalCode")
                );

                DBConnection.getInstance().addCustomer(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadCustomers();
    }

}