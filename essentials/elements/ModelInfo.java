package essentials.elements;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ModelInfo implements Serializable {

    private Integer id;
    private String name;
    private Long x;
    private Integer y;
    private Integer area;
    private Integer population;
    private Integer seaLevel;
    private LocalDateTime creationDate;
    private Boolean capital;
    private String climate;
    private String government;
    private String nameG;
    private Long ageG;
    private Double heightG;
    private Integer client;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public void setSeaLevel(Integer seaLevel) {
        this.seaLevel = seaLevel;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public void setNameG(String nameG) {
        this.nameG = nameG;
    }

    public void setAgeG(Long ageG) {
        this.ageG = ageG;
    }

    public void setHeightG(Double heightG) {
        this.heightG = heightG;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getArea() {
        return area;
    }

    public Integer getPopulation() {
        return population;
    }

    public Integer getSeaLevel() {
        return seaLevel;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Boolean getCapital() {
        return capital;
    }

    public String getClimate() {
        return climate;
    }

    public String getGovernment() {
        return government;
    }

    public String getNameG() {
        return nameG;
    }

    public Long getAgeG() {
        return ageG;
    }

    public Double getHeightG() {
        return heightG;
    }

    public Integer getClient() {
        return client;
    }
}
