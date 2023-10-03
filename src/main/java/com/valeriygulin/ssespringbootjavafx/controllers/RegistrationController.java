package com.valeriygulin.ssespringbootjavafx.controllers;

import com.valeriygulin.ssespringbootjavafx.App;
import com.valeriygulin.ssespringbootjavafx.model.User;
import com.valeriygulin.ssespringbootjavafx.retrofit.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        String login = textFieldLogin.getText();
        if (login.isEmpty()) {
            App.showAlert("Error!", "Enter login!", Alert.AlertType.ERROR);
            return;
        }
        String password = textFieldPassword.getText();
        if (password.isEmpty()) {
            App.showAlert("Error!", "Enter password!", Alert.AlertType.ERROR);
            return;
        }
        User user1 = new User(login, password);
        User post = new UserRepository().post(user1);
        App.showAlert("Info!", post.getLogin() + " was added!", Alert.AlertType.INFORMATION);
        App.closeWindow(actionEvent);
    }
}
