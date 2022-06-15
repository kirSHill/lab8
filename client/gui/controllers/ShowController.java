package client.gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.gui.Authorize;
import client.gui.GuiModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ShowController {

    private static GuiModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ageGField;

    @FXML
    private Label ageGText;

    @FXML
    private Label areaField;

    @FXML
    private Label areaText;

    @FXML
    private ImageView backButton;

    @FXML
    private Label capitalField;

    @FXML
    private Label capitalText;

    @FXML
    private Label clientIdField;

    @FXML
    private Label clientIdText;

    @FXML
    private Label climateField;

    @FXML
    private Label climateText;

    @FXML
    private Label createNewCityLabel;

    @FXML
    private Label creationDateField;

    @FXML
    private Label creationDateText;

    @FXML
    private Button OKButton;

    @FXML
    private Label governmentField;

    @FXML
    private Label governmentText;

    @FXML
    private Label heightGField;

    @FXML
    private Label heightGText;

    @FXML
    private Label idField;

    @FXML
    private Label idText;

    @FXML
    private Label nameField;

    @FXML
    private Label nameGField;

    @FXML
    private Label nameGText;

    @FXML
    private Label nameText;

    @FXML
    private Label populationField;

    @FXML
    private Label populationText;

    @FXML
    private Label seaMetersField;

    @FXML
    private Label seaMetersText;

    @FXML
    private Label xField;

    @FXML
    private Label xText;

    @FXML
    private Label yField;

    @FXML
    private Label yText;

    @FXML
    void OKButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void backButtonOnAction(MouseEvent event) {
        Stage stage = (Stage) OKButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
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
        OKButton.setDefaultButton(true);

        createNewCityLabel.setText(Authorize.resourceBundle.getString("information"));
        idField.setText(String.valueOf(model.getId()));
        nameField.setText(model.getName());
        xField.setText(String.valueOf(model.getX()));
        yField.setText(String.valueOf(model.getY()));
        areaField.setText(String.valueOf(model.getArea()));
        populationField.setText(String.valueOf(model.getPopulation()));
        seaMetersField.setText(String.valueOf(model.getSeaLevel()));
        capitalField.setText(String.valueOf(model.isCapital()));
        creationDateField.setText(String.valueOf(model.getCreationDate()));
        climateField.setText(model.getClimate());
        governmentField.setText(model.getGovernment());
        nameGField.setText(model.getNameG());
        ageGField.setText(String.valueOf(model.getAgeG()));
        heightGField.setText(String.valueOf(model.getHeightG()));
        clientIdField.setText(String.valueOf(model.getClient()));
    }

    public static void setModel(GuiModel gm) {model = gm;}

}
