package db;

import model.Customer;
import java.util.*;

public class DBConnection {
    private static DBConnection instance;
    private List<Customer> customers;

    private DBConnection() {
        customers = new ArrayList<>();
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(updatedCustomer.getId())) {
                customers.set(i, updatedCustomer);
                return;
            }
        }
    }

    public void deleteCustomer(String customerId) {
        customers.removeIf(customer -> customer.getId().equals(customerId));
    }

    public Customer getCustomerById(String customerId) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    public List<Customer> getConnection() {
        return customers;
    }


}