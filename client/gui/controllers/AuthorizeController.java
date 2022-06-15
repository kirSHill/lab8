package client.gui.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import client.gui.Authorize;
import client.gui.Session;
import client.gui.animations.Shake;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AuthorizeController {

        public static Session session = new Session();

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button loginButton;

        @FXML
        private TextField loginField;

        @FXML
        private PasswordField passwordField;

        @FXML
        private Button registerButton;

        @FXML
        private Label mistakeLabel;

        @FXML
        private Label signInLabel;

        @FXML
        private ChoiceBox<String> chooseLanguageButton;
        ObservableList<String> languages;
        String language;



        @FXML
        public void initialize() {
            languages = FXCollections.observableArrayList("Language","English", "Русский", "Română", "Española");
            chooseLanguageButton.setItems(languages);
            chooseLanguageButton.setValue("Language");
            chooseLanguageButton.setOnAction(this::changeLanguage);
            loginButton.setDefaultButton(true);
            loginField.setPromptText(Authorize.resourceBundle.getString("loginField"));
            signInLabel.setText(Authorize.resourceBundle.getString("signInLabel"));
            passwordField.setPromptText(Authorize.resourceBundle.getString("passwordField"));
            loginButton.setText(Authorize.resourceBundle.getString("loginButton"));
            registerButton.setText(Authorize.resourceBundle.getString("createNewAccountButton"));
        }

    public void shake(String reason) {
        Shake loginAnimation = new Shake(loginField);
        Shake passwordAnimation = new Shake(passwordField);
        loginAnimation.animate();
        passwordAnimation.animate();
        mistakeLabel.setText(Authorize.resourceBundle.getString(reason));
    }

    public void loginButtonOnAction(ActionEvent event) {
        Authorize.session.setId(-1);
        if (getAnswer(loginField.getText(),passwordField.getText())) {
            try {
                mistakeLabel.setText(Authorize.resourceBundle.getString(Authorize.session.mistakeMessage));

            } catch (Exception e) {

            }
        } else {
            shake("loginErrorEmptyField");
        }
    }

        public void registerButtonOnAction(ActionEvent event) {
            loadWindow("client/gui/fxmls/register.fxml");
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

        boolean getAnswer(String login, String password) {
            if (!loginField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
                Authorize.client.checkLogin(login + " " + password);
                try {
                    Thread.sleep(1000);
                    if (Authorize.session.getId() != -1) {
                        Authorize.id = Authorize.session.getId();
                        Authorize.session.setGuiModels();
//                        loadWindow("client/gui/fxmls/welcome.fxml");
//                        try {
//                            Thread.sleep(5000);
//                        } catch (Exception e){}
                        loadWindow("client/gui/fxmls/collection.fxml");
                    } else {
                        shake("loginErrorIncorrectField");
                    }
                } catch (Exception e) {
                    CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
                }
                return true;
            } else
            return false;
        }

        public void changeLanguage(ActionEvent event) {
            language = chooseLanguageButton.getValue();
            if(language.equals("Русский")) {
                Authorize.locale = new Locale("ru","RU");
                Authorize.resourceBundle = ResourceBundle.getBundle("client.gui.languages.Bundle",Authorize.locale);
            } else if(language.equals("English")) {
                Authorize.locale = new Locale("en", "AU");
                Authorize.resourceBundle = ResourceBundle.getBundle("client.gui.languages.Bundle", Authorize.locale);
            } else if(language.equals("Española")) {
                Authorize.locale = new Locale("es","SP");
                Authorize.resourceBundle = ResourceBundle.getBundle("client.gui.languages.Bundle",Authorize.locale);
            } else if(language.equals("Română")) {
                Authorize.locale = new Locale("ro","RO");
                Authorize.resourceBundle = ResourceBundle.getBundle("client.gui.languages.Bundle",Authorize.locale);
            }
            try{
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client/gui/fxmls/authorize.fxml"), Authorize.resourceBundle);
                Scene scene = new Scene(root,1600,1000);
                Authorize.stage.setScene(scene);
                Authorize.stage.setResizable(false);
                Authorize.stage.show();
            } catch (IOException e) {
                CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
            }
        }
}