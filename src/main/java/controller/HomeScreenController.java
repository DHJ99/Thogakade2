package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnView;

    @FXML
    private void btnLogin(ActionEvent event) {
        openNewStage("../view/add_customer_form.fxml");
    }

    @FXML
    private void btnUpdate(ActionEvent event) {
        openNewStage("../view/update_customer_form.fxml");
    }

    @FXML
    private void btnView(ActionEvent event) {
        openNewStage("../view/view_customer_form.fxml");
    }

    @FXML
    private void btnDelete(ActionEvent event) {
        openNewStage("../view/delete_customer_form.fxml");
    }

    private void openNewStage(String fxmlPath) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}