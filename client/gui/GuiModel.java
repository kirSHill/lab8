package client.gui;

import essentials.elements.ModelInfo;
import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GuiModel implements Serializable {

    private IntegerProperty id;
    private StringProperty name;
    private LongProperty x;
    private IntegerProperty y;
    private IntegerProperty area;
    private IntegerProperty population;
    private IntegerProperty seaLevel;
    private ObjectProperty<LocalDateTime> creationDate;
    private BooleanProperty capital;
    private StringProperty climate;
    private StringProperty government;
    private StringProperty nameG;
    private LongProperty ageG;
    private DoubleProperty heightG;
    private IntegerProperty client;

    public GuiModel(ModelInfo modelInfo) {
        setId(modelInfo.getId());
        setName(modelInfo.getName());
        setX(modelInfo.getX());
        setY(modelInfo.getY());
        setArea(modelInfo.getArea());
        setPopulation(modelInfo.getPopulation());
        setSeaLevel(modelInfo.getSeaLevel());
        setCreationDate(modelInfo.getCreationDate());
        setCapital(modelInfo.getCapital());
        setClimate(modelInfo.getClimate());
        setGovernment(modelInfo.getGovernment());
        setNameG(modelInfo.getNameG());
        setAgeG(modelInfo.getAgeG());
        setHeightG(modelInfo.getHeightG());
        setClient(modelInfo.getClient());
    }

    public GuiModel() {

    }

    public GuiModel toModel() {
        GuiModel model = new GuiModel();
        model.setName(name.get());
        model.setX(x.get());
        model.setY(y.get());
        model.setArea(area.get());
        model.setPopulation(population.get());
        model.setSeaLevel(seaLevel.get());
        model.setCapital(capital.get());
        model.setClimate(climate.get());
        model.setGovernment(government.get());
        model.setNameG(nameG.get());
        model.setAgeG(ageG.get());
        model.setHeightG(heightG.get());
        try {
            model.setId(id.get());
        } catch (NullPointerException e) {
        }
        try {
            model.setCreationDate(creationDate.get());
        } catch (NullPointerException e) {

        }
        return model;
    }

    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(Integer id) {
        this.id = new SimpleIntegerProperty(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public long getX() {
        return x.get();
    }

    public LongProperty xProperty() {
        return x;
    }

    public void setX(long x) {
        this.x = new SimpleLongProperty(x);
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public void setY(int y) {
        this.y = new SimpleIntegerProperty(y);
    }

    public int getArea() {
        return area.get();
    }

    public IntegerProperty areaProperty() {
        return area;
    }

    public void setArea(int area) {
        this.area = new SimpleIntegerProperty(area);
    }

    public int getPopulation() {
        return population.get();
    }

    public IntegerProperty populationProperty() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = new SimpleIntegerProperty(population);
    }

    public int getSeaLevel() {
        return seaLevel.get();
    }

    public IntegerProperty seaLevelProperty() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = new SimpleIntegerProperty(seaLevel);
    }

    public LocalDateTime getCreationDate() {
        return creationDate.get();
    }

    public ObjectProperty<LocalDateTime> creationDateProperty() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = new SimpleObjectProperty<LocalDateTime>(creationDate);
    }

    public boolean isCapital() {
        return capital.get();
    }

    public BooleanProperty capitalProperty() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = new SimpleBooleanProperty(capital);
    }

    public String getClimate() {
        return climate.get();
    }

    public StringProperty climateProperty() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = new SimpleStringProperty(climate);
    }

    public String getGovernment() {
        return government.get();
    }

    public StringProperty governmentProperty() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = new SimpleStringProperty(government);
    }

    public String getNameG() {
        return nameG.get();
    }

    public StringProperty nameGProperty() {
        return nameG;
    }

    public void setNameG(String nameG) {
        this.nameG = new SimpleStringProperty(nameG);
    }

    public long getAgeG() {
        return ageG.get();
    }

    public LongProperty ageGProperty() {
        return ageG;
    }

    public void setAgeG(long ageG) {
        this.ageG = new SimpleLongProperty(ageG);
    }

    public double getHeightG() {
        return heightG.get();
    }

    public DoubleProperty heightGProperty() {
        return heightG;
    }

    public void setHeightG(double heightG) {
        this.heightG = new SimpleDoubleProperty(heightG);
    }

    public int getClient() {
        return client.get();
    }

    public IntegerProperty clientProperty() {
        return client;
    }

    public void setClient(int client) {
        this.client = new SimpleIntegerProperty(client);
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + x + ", " + y +
                ", " + area + ", " +
                population + ", " + seaLevel + ", " + capital + ", "
                + climate.toString() + ", " + government.toString() + ", " + nameG + ", " + ageG + ", " + heightG + ", " + creationDate + ", " + client;
    }
}
