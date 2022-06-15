package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.gui.Authorize;
import client.gui.Session;
import client.gui.animations.Shake;
import essentials.elements.UserInfo;
import essentials.interaction.Message;
import essentials.precommands.ObjectPrecommand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createNewAccountButton;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nicknameField;

    @FXML
    private ImageView backButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label signUpLabel;

    @FXML
    private Label mistakeLabel;

    @FXML
    public void initialize() {
        createNewAccountButton.setDefaultButton(true);
        loginField.setPromptText(Authorize.resourceBundle.getString("loginField"));
        signUpLabel.setText(Authorize.resourceBundle.getString("signUpLabel"));
        passwordField.setPromptText(Authorize.resourceBundle.getString("passwordField"));
        createNewAccountButton.setText(Authorize.resourceBundle.getString("registerButton"));
        nicknameField.setPromptText(Authorize.resourceBundle.getString("nicknameField"));
    }

    @FXML
    void backButtonOnAction(MouseEvent event) {
        loadWindow("client/gui/fxmls/authorize.fxml");
    }


    public void createNewAccountButtonOnAction(ActionEvent event) {
        Authorize.session.setId(-1);
        if (getAnswer(loginField.getText(),passwordField.getText(),nicknameField.getText())) {
            try {
                mistakeLabel.setText(Authorize.resourceBundle.getString(Authorize.session.mistakeMessage));
            } catch (Exception e) {
            }
        } else {
            Shake nicknameAnimation = new Shake(nicknameField);
            Shake loginAnimation = new Shake(loginField);
            Shake passwordAnimation = new Shake(passwordField);
            nicknameAnimation.animate();
            loginAnimation.animate();
            passwordAnimation.animate();
            mistakeLabel.setText(Authorize.resourceBundle.getString("loginErrorEmptyField"));
        }
    }

    boolean getAnswer(String login, String password, String nickname) {
        if (!loginField.getText().isEmpty() && !passwordField.getText().isEmpty() && !nicknameField.getText().isEmpty()) {
            Authorize.client.checkRegister(login + " " + password + " " + nickname);
            try {
                Thread.sleep(500);
                if (Authorize.session.getId() != -1) {
                    Authorize.id = Authorize.session.getId();
//                    loadWindow("client/gui/fxmls/welcome.fxml");
//                    try {
//                        Thread.sleep(5000);
//
//                    } catch (Exception e){}
                    loadWindow("client/gui/fxmls/authorize.fxml");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else
            return false;
    }

    public void loadWindow(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            Stage stage = Authorize.stage;
            stage.setResizable(false);
            Scene scene = new Scene(root, 1600, 1000);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }
}
