package client.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.gui.Authorize;
import client.gui.Session;
import essentials.interaction.Message;
import essentials.precommands.ObjectPrecommand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label loadingLabel;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        welcomeLabel.setText(Authorize.resourceBundle.getString("welcomeLabel"));
        loadingLabel.setText(Authorize.resourceBundle.getString("loadingLabel"));
        nicknameLabel.setText(Session.nickname + "!");
    }
}

