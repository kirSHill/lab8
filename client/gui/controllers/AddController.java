package client.gui.controllers;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import client.CityGenerator;
import client.gui.Authorize;
import client.gui.GuiModel;
import client.gui.Session;
import essentials.elements.City;
import essentials.interaction.Message;
import essentials.precommands.BasicPrecommand;
import essentials.precommands.ObjectPrecommand;
import essentials.precommands.Precommand;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddController {

    ObservableList<String> climates = FXCollections.observableArrayList("дождливый", "влажный_субтропический", "влажный_континентальный", "степенной", "субарктический");
    ObservableList<String> governments = FXCollections.observableArrayList("геронтократия", "талассократия", "теократия", "технократия");
    ObservableList<String> capitals = FXCollections.observableArrayList("true", "false");
    public GuiModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField ageGField;

    @FXML
    private Label ageGText;

    @FXML
    private TextField areaField;

    @FXML
    private Label areaText;

    @FXML
    private ChoiceBox<String> capitalButton;

    @FXML
    private Label capitalText;

    @FXML
    private Label clientIdField;

    @FXML
    private Label clientIdText;

    @FXML
    private ChoiceBox<String> climateButton;

    @FXML
    private Label climateText;

    @FXML
    private Label createNewCityLabel;

    @FXML
    private RadioButton addIfMinButton;

    @FXML
    private Button addButton;

    @FXML
    private Label governmentText;

    @FXML
    private TextField heightGField;

    @FXML
    private Label heightGText;

    @FXML
    private ChoiceBox<String> governmentButton;

    @FXML
    private Label idField;

    @FXML
    private Label idText;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nameGField;

    @FXML
    private Label nameGText;

    @FXML
    private Label nameText;

    @FXML
    private TextField populationField;

    @FXML
    private Label populationText;

    @FXML
    private TextField seaMetersField;

    @FXML
    private Label seaMetersText;

    @FXML
    private TextField xField;

    @FXML
    private Label xText;

    @FXML
    private TextField yField;

    @FXML
    private Label yText;

    @FXML
    private ImageView backButton;


    @FXML
    void initialize() {
        climateButton.setItems(climates);
        governmentButton.setItems(governments);
        capitalButton.setItems(capitals);
        createNewCityLabel.setText(Authorize.resourceBundle.getString("createNewCityLabel"));
        nameField.setPromptText(Authorize.resourceBundle.getString("enterName"));
        xField.setPromptText(Authorize.resourceBundle.getString("enterX"));
        yField.setPromptText(Authorize.resourceBundle.getString("enterY"));
        areaField.setPromptText(Authorize.resourceBundle.getString("enterArea"));
        populationField.setPromptText(Authorize.resourceBundle.getString("enterPopulation"));
        seaMetersField.setPromptText(Authorize.resourceBundle.getString("enterSeaMeters"));
        nameGField.setPromptText(Authorize.resourceBundle.getString("enterNameG"));
        ageGField.setPromptText(Authorize.resourceBundle.getString("enterAgeG"));
        heightGField.setPromptText(Authorize.resourceBundle.getString("enterHeightG"));
        addButton.setDefaultButton(true);
        addButton.setText(Authorize.resourceBundle.getString("addButton"));

        nameText.setText(Authorize.resourceBundle.getString("nameColumn"));
        xText.setText(Authorize.resourceBundle.getString("xColumn"));
        yText.setText(Authorize.resourceBundle.getString("yColumn"));
        areaText.setText(Authorize.resourceBundle.getString("areaColumn"));
        populationText.setText(Authorize.resourceBundle.getString("populationColumn"));
        seaMetersText.setText(Authorize.resourceBundle.getString("seaMetersColumn"));
        capitalText.setText(Authorize.resourceBundle.getString("capitalColumn"));
        climateText.setText(Authorize.resourceBundle.getString("climateColumn"));
        governmentText.setText(Authorize.resourceBundle.getString("governmentColumn"));
        nameGText.setText(Authorize.resourceBundle.getString("nameGColumn"));
        ageGText.setText(Authorize.resourceBundle.getString("ageGColumn"));
        heightGText.setText(Authorize.resourceBundle.getString("heightGColumn"));
        addIfMinButton.setText(Authorize.resourceBundle.getString("addIfMin"));
    }

    @FXML
    void backButtonOnAction(MouseEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void addButtonOnAction(ActionEvent event) {
        String mistake = "";
        GuiModel guiModel = new GuiModel();
        String name = nameField.getText();
        String x = xField.getText();
        String y = yField.getText();
        String area = areaField.getText();
        String population = populationField.getText();
        String seaMeters = seaMetersField.getText();
        String nameG = nameGField.getText();
        String ageG = ageGField.getText();
        String heightG = heightGField.getText();
        String climate = climateButton.getValue();
        String government = governmentButton.getValue();
        String capital = capitalButton.getValue();

        if (name == null || name.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("nameFillError");
        } else {
            guiModel.setName(name);
        }
        if (x == null || x.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("xFillError");
        } else {
            try {
                guiModel.setX(Long.parseLong(x));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("xFillError");
            }
        }
        if (y == null || y.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("yFillError");
        } else {
            try {
                guiModel.setY(Integer.parseInt(y));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("yFillError");
            }
        }
        if (area == null || area.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("areaFillError");
        } else {
            try {
                guiModel.setArea(Integer.parseInt(area));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("areaFillError");
            }
        }
        if (population == null || population.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("populationFillError");
        } else {
            try {
                guiModel.setPopulation(Integer.parseInt(population));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("populationFillError");
            }
        }
        if (seaMeters == null || seaMeters.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("seaMetersFillError");
        } else {
            try {
                guiModel.setSeaLevel(Integer.parseInt(seaMeters));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("seaMetersFillError");
            }
        }
        if (nameG == null || nameG.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("nameGFillError");
        } else {
            guiModel.setNameG(nameG);
        }
        if (ageG == null || ageG.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("ageGFillError");
        } else {
            try {
                guiModel.setAgeG(Long.parseLong(ageG));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("ageGFillError");
            }
        }
        if (heightG == null || heightG.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("heightGFillError");
        } else {
            try {
                guiModel.setHeightG(Double.parseDouble(heightG));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("heightGFillError");
            }
        }
        if (capital == null) {
            mistake += Authorize.resourceBundle.getString("capitalFillError");
        } else {
            guiModel.setCapital(Boolean.parseBoolean(capital));
        }
        if (climate == null || climate.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("climateFillError");
        } else {
            guiModel.setClimate(climate);
        }
        if (government == null || government.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("governmentFillError");
        } else {
            guiModel.setGovernment(government);
        }

        if (mistake.isEmpty()) {
            model = guiModel;
            if(addIfMinButton.isSelected()) {
                try {
                    ObjectPrecommand addIfMinPrecommand = new ObjectPrecommand("addIfMin");
                    City city = Authorize.session.getCity(model);
                    addIfMinPrecommand.preprocess(city, false);
                    Session.serverInteraction.sendData(addIfMinPrecommand);
                    Thread.sleep(500);
                    if (Session.added) {
                        CollectionController.showInformationAlert(Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("addIfMinSuccess"));
                        Session.added = false;
                    } else {
                        CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("addIfMinError"),Authorize.resourceBundle.getString("addIfMinAnswerNotMin"));
                    }
                } catch (Exception e) {
                    CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("addError"),Authorize.resourceBundle.getString("incorrectData"));                }
            } else {
                try {
                    ObjectPrecommand addPrecommand = new ObjectPrecommand("add");
                    City city = Authorize.session.getCity(model);
                    addPrecommand.preprocess(city, false);
                    Session.serverInteraction.sendData(addPrecommand);
                    Thread.sleep(500);
                    if (Session.added) {
                        CollectionController.showInformationAlert(Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("addSuccess"));
                        Session.added = false;
                    }
                    } catch (Exception e) {
                    CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("addError"),Authorize.resourceBundle.getString("incorrectData"));                }
            }
        } else CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("addError"),mistake);

    }
}
