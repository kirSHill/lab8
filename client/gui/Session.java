package client.gui;

import client.gui.controllers.CollectionController;
import essentials.elements.City;
import essentials.elements.Climate;
import essentials.elements.Government;
import essentials.elements.Human;
import essentials.interaction.Message;
import essentials.interaction.ServerInteraction;
import essentials.precommands.BasicPrecommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public class Session {

    public static ServerInteraction serverInteraction;
    public String mistakeMessage = "";
    private int id = -1;
    public static String nickname;
    public static ArrayList<Integer> list = new ArrayList<>();
    public static boolean added = false;
    public static boolean removed = false;
    public static boolean edited = false;
    public static boolean incorrect = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private ObservableList<GuiModel> guiModels = FXCollections.observableArrayList();

    public ObservableList<GuiModel> getGuiModels() {return guiModels;}

    public void setGuiModels(PriorityQueue<City> collection) {
        ArrayList<GuiModel> guiModelss = new ArrayList<>();
        for (City model : collection) {
            GuiModel guiModel = new GuiModel();
            guiModel.setId(model.getId());
            guiModel.setName(model.getName());
            guiModel.setX(model.getCoordinates().getX());
            guiModel.setY(model.getCoordinates().getY());
            guiModel.setPopulation(model.getPopulation());
            guiModel.setArea(model.getArea());
            guiModel.setSeaLevel(model.getMetersAboveSeaLevel());
            guiModel.setClimate(model.getClimate().toString());
            guiModel.setGovernment(model.getGovernment().toString());
            guiModel.setCreationDate(model.getCreationDate());
            guiModel.setCapital(model.isCapital());
            guiModel.setNameG(model.getGovernor().getNameG());
            guiModel.setAgeG(model.getGovernor().getAge());
            guiModel.setHeightG(model.getGovernor().getHeight());
            guiModel.setClient(model.getClientId());
            guiModelss.add(guiModel);
        }
        this.guiModels = FXCollections.observableArrayList(guiModelss);
    }

    public City getCity(GuiModel model) throws Exception {
        City city = new City();
        Human governor = new Human();
        city.setName(model.getName());
        city.setCoordinates(model.getX(), model.getY());
        city.setArea(model.getArea());
        city.setPopulation(model.getPopulation());
        city.setMetersAboveSeaLevel(model.getSeaLevel());
        city.setCapital(model.isCapital());
        city.setClimate(Climate.getByName(model.getClimate()));
        city.setGovernment(Government.getByName(model.getGovernment()));
        governor.setNameG(model.getNameG());
        governor.setAge(model.getAgeG());
        governor.setHeight(model.getHeightG());
        city.setGovernor(governor);
        return city;
    }

    public GuiModel getModel(City model) {
        GuiModel guiModel = new GuiModel();
        guiModel.setId(model.getId());
        guiModel.setName(model.getName());
        guiModel.setX(model.getCoordinates().getX());
        guiModel.setY(model.getCoordinates().getY());
        guiModel.setPopulation(model.getPopulation());
        guiModel.setArea(model.getArea());
        guiModel.setSeaLevel(model.getMetersAboveSeaLevel());
        guiModel.setClimate(model.getClimate().toString());
        guiModel.setGovernment(model.getGovernment().toString());
        guiModel.setCreationDate(model.getCreationDate());
        guiModel.setCapital(model.isCapital());
        guiModel.setNameG(model.getGovernor().getNameG());
        guiModel.setAgeG(model.getGovernor().getAge());
        guiModel.setHeightG(model.getGovernor().getHeight());
        guiModel.setClient(model.getClientId());
        return guiModel;
    }

    public void setGuiModels() {
        try {
            BasicPrecommand showPrecommand = new BasicPrecommand("show");
            //showPrecommand.preprocess(null, false);
            serverInteraction.sendData(showPrecommand);
//            Message message = (Message) serverInteraction.readData();
//            PriorityQueue<City> collection = (PriorityQueue<City>) message.getObject();
//                for (City model : collection) {
//                    GuiModel guiModel = new GuiModel();
//                    guiModel.setId(model.getId());
//                    guiModel.setName(model.getName());
//                    guiModel.setX(model.getCoordinates().getX());
//                    guiModel.setY(model.getCoordinates().getY());
//                    guiModel.setPopulation(model.getPopulation());
//                    guiModel.setArea(model.getArea());
//                    guiModel.setSeaLevel(model.getMetersAboveSeaLevel());
//                    guiModel.setClimate(model.getClimate().toString());
//                    guiModel.setGovernment(model.getGovernment().toString());
//                    guiModel.setCreationDate(model.getCreationDate());
//                    guiModel.setCapital(model.isCapital());
//                    guiModel.setNameG(model.getGovernor().getNameG());
//                    guiModel.setAgeG(model.getGovernor().getAge());
//                    guiModel.setHeightG(model.getGovernor().getHeight());
//                    guiModel.setClient(model.getClientId());
//                    guiModels.add(guiModel);
//            }
        } catch (IOException | NullPointerException e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }

    public void connect() {
        try {
            Socket socket = new Socket("localhost", 7182);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            serverInteraction = new ServerInteraction(objectInputStream, objectOutputStream);
            Updater updater = new Updater(this, Thread.currentThread());
            Thread thread = new Thread(updater);
            thread.start();
        } catch (IOException e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }

    public void updateCollection(Message message) {
        try {
            String command = message.getCommand();
            switch (command) {
                case "show":
                    Authorize.session.setGuiModels((PriorityQueue<City>) message.getObject());
                    break;
                case "authorize":
                case "register":
                    try {
                        if (message.isSuccessful()) {
                            String cap = (String) message.getObject();
                            String[] cup = cap.split(" ");
                            id = Integer.parseInt(cup[0]);
                            nickname = cup[1];
                            Authorize.session.setId(id);
                        } else {
                            incorrect = true;
                        }
                    } catch (Exception e) {
                        mistakeMessage = message.getText();
                    }
                    break;
                case "add":
                    GuiModel guiModel = getModel((City)message.getObject());
                    Authorize.session.getGuiModels().add(guiModel);
                    added = true;
                    break;
                case "removeById": {
                    Integer id = (Integer)message.getObject();
                    GuiModel model = null;
                    for (GuiModel a : Authorize.session.getGuiModels()) {
                        if(Objects.equals(a.getId(), id)) {
                            model = a;
                            break;
                        }
                    }
                    Authorize.session.getGuiModels().remove(model);
                    removed = true;
                    break;
                }
                case "clear":
                    ArrayList<Integer> idToDelete = (ArrayList<Integer>) message.getObject();
                    list = idToDelete;
                    ArrayList<GuiModel> modelToDelete = new ArrayList<>();
                    for (GuiModel a : getGuiModels()) {
                        if (idToDelete.contains(a.getId())) {
                            modelToDelete.add(a);
                        }
                    }
                    getGuiModels().removeAll(modelToDelete);
                    break;
                case "updateId":
                    City city = (City) message.getObject();
                    int id = city.getId();
                    for (int i = 0; i < getGuiModels().size(); i++) {
                        if(getGuiModels().get(i).getId() == id) {
                            getGuiModels().remove(i);
                            getGuiModels().add(getModel(city));
                        }
                    }
                    edited = true;
                    break;
                case "addIfMin": {
                    if(message.isSuccessful()) {
                        guiModel = getModel((City)message.getObject());
                        Authorize.session.getGuiModels().add(guiModel);
                        added = true;
                        break;
                    } else {
                        mistakeMessage = message.getText();
                        added = false;
                    }
                    break;
                }
            }
        } catch (NullPointerException e) {}
    }
}
