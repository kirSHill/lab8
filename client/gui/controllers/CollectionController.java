package client.gui.controllers;

import client.gui.Authorize;
import client.gui.GuiModel;
import client.gui.Session;
import essentials.elements.Climate;
import essentials.precommands.BasicPrecommand;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class CollectionController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private boolean editIsClosed = true;
    private Stage editStage;
    private GuiModel activeModel;
    private GraphicsContext graphicsContext;
    private final Vector<Color> colors = new Vector<>();
    private final ArrayList<Integer> drawnIDs = new ArrayList<>();
    public static ObservableList<Long> listOfX = FXCollections.observableArrayList();
    public static ObservableList<Integer> listOfY = FXCollections.observableArrayList();
    public static ObservableList<Integer> listOfId = FXCollections.observableArrayList();
    public static ObservableList<Integer> listOfArea = FXCollections.observableArrayList();
    public static ObservableList<Integer> listOfClients = FXCollections.observableArrayList();
    static Timeline timeline;

    @FXML
    private TextField idField, nameField, xField, yField, areaField, populationField, seaMetersField, capitalField,
            creationDateField, climateField, governmentField, nameGField, ageGField, heightGField, clientIdField;

    @FXML
    private Button addButton;

    @FXML
    private TableView<GuiModel> collectionTable;

    @FXML
    private TableColumn<GuiModel, Long> agegColumn;

    @FXML
    private TableColumn<GuiModel, Integer> areaColumn;

    @FXML
    private TableColumn<GuiModel, Integer> authorColumn;

    @FXML
    private TableColumn<GuiModel, Boolean> capitalColumn;

    @FXML
    private Label nicknameLabel;

    @FXML
    private Button clearButton;

    @FXML
    private ImageView changeAccountField;

    @FXML
    private TableColumn<GuiModel, String> climateColumn;

    @FXML
    private TableColumn<GuiModel, String> governmentColumn;

    @FXML
    private TableColumn<GuiModel, Double> heightgColumn;

    @FXML
    private TableColumn<GuiModel, Integer> idColumn;


    @FXML
    private TableColumn<GuiModel, String> nameColumn;

    @FXML
    private TableColumn<GuiModel, String> namegColumn;

    @FXML
    private TableColumn<GuiModel, Integer> populationColumn;

    @FXML
    private TableColumn<GuiModel, Integer> seaMetersColumn;

    @FXML
    private TableColumn<GuiModel, Long> xColumn;

    @FXML
    private TableColumn<GuiModel, Integer> yColumn;

    @FXML
    private TableColumn<GuiModel, LocalDateTime> dateColumn;

    @FXML
    private AnchorPane tabMap;

    @FXML
    private Canvas objectCanvas;

    @FXML
    private TextField searchButton;


    public void loadWindow(String title, String fxml, int width, int height) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
            Stage stage = new Stage();
            stage.setResizable(false);
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.show();
            stage.setTitle(Authorize.resourceBundle.getString(title));
        } catch (Exception e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }

    @FXML
    void addButtonOnAction(ActionEvent event) {
            loadWindow("add","client/gui/fxmls/add.fxml",400,700);
    }



    @FXML
    void clearButtonOnAction(ActionEvent event) {
        if (showConfirmationAlert(Authorize.resourceBundle.getString("confirmation"),Authorize.resourceBundle.getString("confirmationOfClear"),Authorize.resourceBundle.getString("confirmationOfClearMessage"))) {
            try {
                BasicPrecommand clearPrecommand = new BasicPrecommand("clear");
                clearPrecommand.preprocess(null, false);
                Session.serverInteraction.sendData(clearPrecommand);
                Thread.sleep(500);
                if (!Session.list.isEmpty()) {
                    CollectionController.showInformationAlert(Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("success"),Authorize.resourceBundle.getString("clearSuccess"));
                } else {
                    CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("clearError"),Authorize.resourceBundle.getString("noElementsToDelete"));
                }
            } catch (Exception e) {
                CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));            }
        }
    }


    public void editButtonOnAction(GuiModel model) {
        try {
            activeModel = model;
            Integer clientId = activeModel.getClient();
            if (Authorize.id == clientId) {
                EditController.setModel(activeModel);
                loadWindow("edit","client/gui/fxmls/edit.fxml",400,700);
            } else {
                ShowController.setModel(activeModel);
                loadWindow("information","client/gui/fxmls/show.fxml",400,700);
            }
        } catch (Exception e) {
            showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("editError"),Authorize.resourceBundle.getString("unknownError"));
        }
    }


    @FXML
    void initialize()  {

        nicknameLabel.setText(Session.nickname);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());
        areaColumn.setCellValueFactory(cellData -> cellData.getValue().areaProperty().asObject());
        populationColumn.setCellValueFactory(cellData -> cellData.getValue().populationProperty().asObject());
        seaMetersColumn.setCellValueFactory(cellData -> cellData.getValue().seaLevelProperty().asObject());
        climateColumn.setCellValueFactory(cellData -> cellData.getValue().climateProperty());
        governmentColumn.setCellValueFactory(cellData -> cellData.getValue().governmentProperty());
        namegColumn.setCellValueFactory(cellData -> cellData.getValue().nameGProperty());
        agegColumn.setCellValueFactory(cellData -> cellData.getValue().ageGProperty().asObject());
        heightgColumn.setCellValueFactory(cellData -> cellData.getValue().heightGProperty().asObject());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().clientProperty().asObject());
        capitalColumn.setCellValueFactory(cellData -> cellData.getValue().capitalProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().creationDateProperty());
        collectionTable.setItems(Authorize.session.getGuiModels());
        addButton.setText(Authorize.resourceBundle.getString("addButton"));
        clearButton.setText(Authorize.resourceBundle.getString("clearButton"));
        searchButton.setPromptText(Authorize.resourceBundle.getString("searchButton"));
        idColumn.setText(Authorize.resourceBundle.getString("idColumn"));
        nameColumn.setText(Authorize.resourceBundle.getString("nameColumn"));
        xColumn.setText(Authorize.resourceBundle.getString("xColumn"));
        yColumn.setText(Authorize.resourceBundle.getString("yColumn"));
        areaColumn.setText(Authorize.resourceBundle.getString("areaColumn"));
        populationColumn.setText(Authorize.resourceBundle.getString("populationColumn"));
        seaMetersColumn.setText(Authorize.resourceBundle.getString("seaMetersColumn"));
        climateColumn.setText(Authorize.resourceBundle.getString("climateColumn"));
        governmentColumn.setText(Authorize.resourceBundle.getString("governmentColumn"));
        namegColumn.setText(Authorize.resourceBundle.getString("nameGColumn"));
        agegColumn.setText(Authorize.resourceBundle.getString("ageGColumn"));
        heightgColumn.setText(Authorize.resourceBundle.getString("heightGColumn"));
        authorColumn.setText(Authorize.resourceBundle.getString("clientIdColumn"));
        capitalColumn.setText(Authorize.resourceBundle.getString("capitalColumn"));
        dateColumn.setText(Authorize.resourceBundle.getString("dateColumn"));

        collectionTable.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> editButtonOnAction(newValue));

        Authorize.session.getGuiModels().addListener((ListChangeListener<GuiModel>) c -> {
            while (c.next()) {
                if (c.wasRemoved()) {
                    for (GuiModel removedModel : c.getRemoved()) {
                        if (listOfX.contains(removedModel.getX()) && listOfY.contains(removedModel.getY()) && listOfId.contains(removedModel.getId())) {
                            int index = listOfId.indexOf(removedModel.getId());
                            listOfX.remove(index);
                            listOfY.remove(index);
                            listOfId.remove(index);
                            listOfArea.remove(index);
                            listOfClients.remove(index);
                        }
                    }
                }
                if (c.wasAdded()) {
                    for (GuiModel addedModel : c.getAddedSubList()) {
                        listOfX.add(addedModel.getX());
                        listOfY.add(addedModel.getY());
                        listOfArea.add(addedModel.getArea());
                        listOfId.add(addedModel.getId());
                        listOfClients.add(addedModel.getClient());
                    }
                }

                redrawObjects();
            }
        });

        for (GuiModel item : collectionTable.getItems()) {
            drawnIDs.add(item.getId());
            listOfX.add(item.getX());
            listOfY.add(item.getY());
            listOfId.add(item.getId());
            listOfArea.add(item.getArea());
            listOfClients.add(item.getClient());
        }
        tabMap.widthProperty().addListener((obs, oldVal, newVal) -> redrawObjects());
        tabMap.heightProperty().addListener((obs, oldVal, newVal) -> redrawObjects());
        graphicsContext = objectCanvas.getGraphicsContext2D();
        graphicsContext.setFont(Font.font("Arial"));

        Field[] colorsField = Color.class.getDeclaredFields();
        for (int i = 7; i < colorsField.length - 10; i++) {
            try {
                this.colors.add((Color) colorsField[i].get(Color.class));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        objectCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    ObservableList<GuiModel> list = collectionTable.getItems();
                    for (GuiModel model : list) {
                        if (e.getX() < tabMap.getWidth() / 2 + model.getX() + model.getArea() / 2 && e.getX() > tabMap.getWidth() / 2 + model.getX() - model.getArea() / 2)
                            if (e.getY() > tabMap.getHeight() / 2 - model.getY() - model.getArea() / 2 && e.getY() < tabMap.getHeight() / 2 - model.getY() + model.getArea() / 2) {
                                if (e.getButton().equals(MouseButton.PRIMARY)) {
                                    editButtonOnAction(model);
                                    break;
                                }
                            }
                    }
                });

        FilteredList<GuiModel> filteredList = new FilteredList<>(Authorize.session.getGuiModels(),b->true);

        searchButton.textProperty().addListener((observable,oldValue, newValue) -> {
           filteredList.setPredicate(guiModel -> {
               if (newValue == null || newValue.isEmpty()) {
                   return true;
               }
               String lowerCaseFilter = newValue.toLowerCase();

               if (guiModel.getClimate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                   return true;
               } else if (guiModel.getGovernment().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                   return true;
               } else return false;
           });
        } );

        SortedList<GuiModel> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(collectionTable.comparatorProperty());
        collectionTable.setItems(sortedList);
    }

    @FXML
    void changeAccountOnAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("client/gui/fxmls/authorize.fxml"));
        Stage stage = Authorize.stage;
        stage.setResizable(false);
        Scene scene = new Scene(root, 1600, 1000);
        stage.setScene(scene);
        stage.show();
        try {
            BasicPrecommand exitPrecommand = new BasicPrecommand("exit");
            exitPrecommand.preprocess(null,false);
            Session.serverInteraction.sendData(exitPrecommand);
        } catch (Exception e) {
            CollectionController.showErrorAlert(Authorize.resourceBundle.getString("error"),Authorize.resourceBundle.getString("serverDead"),Authorize.resourceBundle.getString("serverDeadMessage"));
        }
    }

    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(Authorize.stage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean showConfirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(Authorize.stage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> res = alert.showAndWait();
        return res.get() == ButtonType.OK;
    }

    public static void showInformationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(Authorize.stage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void redrawObjects() {
        redrawPlain();
        drawnBefore();
        Integer id = null;

        for (int i = 0; i < listOfX.size(); i++) {
            if (!drawnIDs.contains(listOfId.get(i))) {
                double sizeHalf = listOfArea.get(i) / 2;
                double x = tabMap.getWidth() / 2 + listOfX.get(i) - sizeHalf;
                double y = tabMap.getHeight() / 2 - listOfY.get(i) - sizeHalf;
                int creatorsID = listOfClients.get(i);
                id = listOfId.get(i);
                DoubleProperty xx = new SimpleDoubleProperty();
                DoubleProperty yy = new SimpleDoubleProperty();
                timeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(xx, tabMap.getWidth() / 2),
                                new KeyValue(yy, tabMap.getHeight() / 2)
                        ),
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(xx, x),
                                new KeyValue(yy, y)
                        ),
                        new KeyFrame(Duration.seconds(1),
                                new KeyValue(xx, 3000),
                                new KeyValue(yy, 3000))
                );
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        GraphicsContext gc = graphicsContext;
                        redrawPlain();

                        gc.setFill(colors.get(creatorsID % colors.size()));
                        gc.fillRect(xx.doubleValue(), yy.doubleValue(), sizeHalf * 2, sizeHalf * 2);
                        drawnBefore();
                    }
                };
                timer.start();
                timeline.play();
                graphicsContext.strokeText(String.valueOf(id), x, y + sizeHalf, sizeHalf * 2);
                break;
            }
        }

        try {
            Thread.sleep(3000);
        } catch (Exception e) {}
        if (id != null)
            drawnIDs.add(id);
        redrawPlain();
        drawnBefore();
    }

    private void drawnBefore() {
        for (int i = 0; i < listOfX.size(); i++) {
            if (drawnIDs.contains(listOfId.get(i))) {
                double sizeHalf = listOfArea.get(i) / 2;
                double x = tabMap.getWidth() / 2 + listOfX.get(i) - sizeHalf;
                double y = tabMap.getHeight() / 2 - listOfY.get(i) - sizeHalf;
                Rect a = new Rect(x, y, sizeHalf, listOfId.get(i), listOfClients.get(i));
                graphicsContext.setFill(colors.get(a.client % colors.size()));
                graphicsContext.fillRect(a.x, a.y, a.halfSize * 2, a.halfSize * 2);
                graphicsContext.strokeText(String.valueOf(a.id), a.x, a.y + a.halfSize, a.halfSize * 2);
            }
        }
    }

    private void redrawPlain() {
        objectCanvas.setHeight(tabMap.getHeight());
        objectCanvas.setWidth(tabMap.getWidth());
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, tabMap.getWidth(), tabMap.getHeight());
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.strokeLine(0, tabMap.getHeight() / 2, tabMap.getWidth(), tabMap.getHeight() / 2);
        graphicsContext.strokeLine(tabMap.getWidth() / 2, 0, tabMap.getWidth() / 2, tabMap.getHeight());
        graphicsContext.strokeLine(tabMap.getWidth() / 2 + 10, tabMap.getHeight() / 2 - 5, tabMap.getWidth() / 2 + 10, tabMap.getHeight() / 2 + 5);
        graphicsContext.strokeText("10", tabMap.getWidth() / 2 + 5, tabMap.getHeight() / 2 + 18, 10);
        graphicsContext.strokeLine(tabMap.getWidth() / 2 - 10, 10, tabMap.getWidth() / 2, 0);
        graphicsContext.strokeLine(tabMap.getWidth() / 2 + 10, 10, tabMap.getWidth() / 2, 0);
        graphicsContext.strokeLine(tabMap.getWidth() - 10, tabMap.getHeight() / 2 + 10, tabMap.getWidth(), tabMap.getHeight() / 2);
        graphicsContext.strokeLine(tabMap.getWidth() - 10, tabMap.getHeight() / 2 - 10, tabMap.getWidth(), tabMap.getHeight() / 2);
    }

    private class Rect {
        public Double x;
        public Double y;
        public Double halfSize;
        public int id;
        public int client;

        @Override
        public boolean equals(Object o) {
            Rect rect = (Rect) o;
            return id == rect.id && client == rect.client && x.equals(rect.x) && y.equals(rect.y) && halfSize.equals(rect.halfSize);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, halfSize, id, client);
        }

        public Rect(Double x, Double y, Double halfSize, int id, int client) {
            this.x = x;
            this.y = y;
            this.halfSize = halfSize;
            this.id = id;
            this.client = client;
        }
    }

}
