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
import essentials.precommands.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditController {

    ObservableList<String> climates = FXCollections.observableArrayList("дождливый", "влажный_субтропический", "влажный_континентальный", "степенной", "субарктический");
    ObservableList<String> governments = FXCollections.observableArrayList("геронтократия", "талассократия", "теократия", "технократия");
    ObservableList<String> capitals = FXCollections.observableArrayList("true", "false");
    public GuiModel enteredModel;
    private static GuiModel model;

    @FXML
    private ImageView backButton;

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
    private Label creationDateField;

    @FXML
    private Label creationDateText;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private ChoiceBox<String> governmentButton;

    @FXML
    private Label governmentText;

    @FXML
    private TextField heightGField;

    @FXML
    private Label heightGText;

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
    void initialize() {
        climateButton.setItems(climates);
        governmentButton.setItems(governments);
        capitalButton.setItems(capitals);
        createNewCityLabel.setText(Authorize.resourceBundle.getString("editOrDelete"));
        idField.setText(String.valueOf(model.getId()));
        nameField.setText(model.getName());
        xField.setText(String.valueOf(model.getX()));
        yField.setText(String.valueOf(model.getY()));
        areaField.setText(String.valueOf(model.getArea()));
        populationField.setText(String.valueOf(model.getPopulation()));
        seaMetersField.setText(String.valueOf(model.getSeaLevel()));
        capitalButton.setValue(String.valueOf(model.isCapital()));
        creationDateField.setText(String.valueOf(model.getCreationDate()));
        climateButton.setValue(model.getClimate());
        governmentButton.setValue(model.getGovernment());
        nameGField.setText(model.getNameG());
        ageGField.setText(String.valueOf(model.getAgeG()));
        heightGField.setText(String.valueOf(model.getHeightG()));
        clientIdField.setText(String.valueOf(model.getClient()));

        clientIdText.setText(Authorize.resourceBundle.getString("clientIdColumn"));
        nameText.setText(Authorize.resourceBundle.getString("nameColumn"));
        idText.setText(Authorize.resourceBundle.getString("idColumn"));
        xText.setText(Authorize.resourceBundle.getString("xColumn"));
        yText.setText(Authorize.resourceBundle.getString("yColumn"));
        areaText.setText(Authorize.resourceBundle.getString("areaColumn"));
        populationText.setText(Authorize.resourceBundle.getString("populationColumn"));
        seaMetersText.setText(Authorize.resourceBundle.getString("seaMetersColumn"));
        capitalText.setText(Authorize.resourceBundle.getString("capitalColumn"));
        climateText.setText(Authorize.resourceBundle.getString("climateColumn"));
        governmentText.setText(Authorize.resourceBundle.getString("governmentColumn"));
        creationDateText.setText(Authorize.resourceBundle.getString("dateColumn"));
        nameGText.setText(Authorize.resourceBundle.getString("nameGColumn"));
        ageGText.setText(Authorize.resourceBundle.getString("ageGColumn"));
        heightGText.setText(Authorize.resourceBundle.getString("heightGColumn"));
        editButton.setDefaultButton(true);
    }

    public static void setModel(GuiModel gm) {model = gm;}


    @FXML
    void backButtonOnAction(MouseEvent event) {
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    void deleteButtonOnAction(ActionEvent event) {
        if (CollectionController.showConfirmationAlert(Authorize.resourceBundle.getString("confirmation"),Authorize.resourceBundle.getString("confirmationOfDelete"),Authorize.resourceBundle.getString("confirmationOfDeleteMessage"))) {
            try {
                IdPrecommand deletePrecommand = new IdPrecommand("removeById", Integer.parseInt(idField.getText()));
                deletePrecommand.preprocess(null, false);
                Session.serverInteraction.sendData(deletePrecommand);
                Thread.sleep(500);
                if (Session.removed) {
                    deleteButton.getScene().getWindow().hide();
                    CollectionController.showInformationAlert(Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("deleteSuccess"),Authorize.resourceBundle.getString("deleteSuccessMessage"));
                    Session.removed = false;
                }
            } catch (Exception e) {
                CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));            }
        }
    }


    @FXML
    void editButtonOnAction(ActionEvent event) {

        String mistake = "";
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
            model.setName(name);
        }
        if (x == null || x.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("xFillError");
        } else {
            try {
                model.setX(Long.parseLong(x));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("xFillError");
            }
        }
        if (y == null || y.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("yFillError");
        } else {
            try {
                model.setY(Integer.parseInt(y));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("yFillError");
            }
        }
        if (area == null || area.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("areaFillError");
        } else {
            try {
                model.setArea(Integer.parseInt(area));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("areaFillError");
            }
        }
        if (population == null || population.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("populationFillError");
        } else {
            try {
                model.setPopulation(Integer.parseInt(population));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("populationFillError");
            }
        }
        if (seaMeters == null || seaMeters.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("seaMetersFillError");
        } else {
            try {
                model.setSeaLevel(Integer.parseInt(seaMeters));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("seaMetersFillError");
            }
        }
        if (nameG == null || nameG.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("nameGFillError");
        } else {
            model.setNameG(nameG);
        }
        if (ageG == null || ageG.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("ageGFillError");
        } else {
            try {
                model.setAgeG(Long.parseLong(ageG));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("ageGFillError");
            }
        }
        if (heightG == null || heightG.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("heightGFillError");
        } else {
            try {
                model.setHeightG(Double.parseDouble(heightG));
            } catch (NumberFormatException e) {
                mistake += Authorize.resourceBundle.getString("heightGFillError");
            }
        }
        if (capital == null) {
            mistake += Authorize.resourceBundle.getString("capitalFillError");
        } else {
            model.setCapital(Boolean.parseBoolean(capital));
        }
        if (climate == null || climate.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("climateFillError");
        } else {
            model.setClimate(climate);
        }
        if (government == null || government.isEmpty()) {
            mistake += Authorize.resourceBundle.getString("governmentFillError");
        } else {
            model.setGovernment(government);
        }

        if (mistake.isEmpty()) {
            enteredModel = model;
            try {
                ObjectIdPrecommand editPrecommand = new ObjectIdPrecommand("updateId",enteredModel.getId().toString());
                City city = Authorize.session.getCity(model);
                editPrecommand.preprocess(city,false);
                Session.serverInteraction.sendData(editPrecommand);
                Thread.sleep(500);
                if (Session.edited) {
                    CollectionController.showInformationAlert(Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("successOfEdit"),Authorize.resourceBundle.getString("successOfEditMessage"));
                    Session.edited = false;
                }
            } catch (Exception e) {
                CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("editError"),Authorize.resourceBundle.getString("incorrectData"));
            }
        } else CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("editError"),mistake);
    }
}
