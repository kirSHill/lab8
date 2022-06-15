package client.gui;

import essentials.interaction.Message;
import essentials.interaction.ServerInteraction;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;

public class Authorize extends Application {

    public static Stage stage;
    public static Client client;
    public static Integer id;
    public static Session session;
    public static Locale locale;
    public static ResourceBundle resourceBundle;



    public static void show(Client client, Session session) {
        Authorize.client = client;
        Authorize.session = session;
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        locale = new Locale("en","AU");
        resourceBundle = ResourceBundle.getBundle("client.gui.languages.Bundle",locale);
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/authorize.fxml"), resourceBundle);
        primaryStage.setTitle("Priority Queue Collection");
        primaryStage.setScene(new Scene(root, 1600, 1000));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
