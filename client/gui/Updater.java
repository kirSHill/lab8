package client.gui;

import client.gui.controllers.CollectionController;
import essentials.interaction.Message;
import essentials.interaction.ServerInteraction;

import java.io.IOException;

public class Updater implements Runnable {

    Session session;
    Thread sessionThread;

    public Updater(Session s, Thread t) {
        session = s;
        sessionThread = t;
    }


    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) Session.serverInteraction.readData();
                System.out.println(message);
                Authorize.session.updateCollection(message);
            } catch (IOException | ClassNotFoundException e) {
                CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
            }
        }
    }
}
