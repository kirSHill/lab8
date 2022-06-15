package client.gui;

import client.gui.controllers.CollectionController;
import essentials.elements.UserInfo;
import essentials.precommands.ObjectPrecommand;
import javafx.scene.control.Alert;

import java.io.IOException;

public class Client {

    private Session session = new Session();

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
        System.exit(0);
    }

    public void start() {
        try {
            session.connect();
            Authorize.show(this, session);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(Authorize.stage);
            alert.setTitle("error");
            alert.setHeaderText("serverDead");
            alert.setContentText("serverDeadMessage");
            alert.showAndWait();
        }
    }

    public void checkLogin(String auth) {
        String[] cup = auth.split(" ");
        String login = cup[0];
        String password = cup[1];
        try {
            ObjectPrecommand loginPrecommand = new ObjectPrecommand("authorize");
            UserInfo userInfo = new UserInfo(login, password);
            loginPrecommand.preprocess(userInfo, false);
            Session.serverInteraction.sendData(loginPrecommand);
        } catch (Exception e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }

    public void checkRegister(String auth) {
        String[] cup = auth.split(" ");
        String login = cup[0];
        String password = cup[1];
        String nickname = cup[2];
        try {
            ObjectPrecommand registerPrecommand = new ObjectPrecommand("register");
            UserInfo userInfo = new UserInfo(login, password, nickname);
            registerPrecommand.preprocess(userInfo, false);
            Session.serverInteraction.sendData(registerPrecommand);
        } catch (Exception e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }
}
