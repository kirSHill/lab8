package server.repositories;

import client.gui.GuiModel;
import essentials.elements.City;
import server.models.CityModel;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class CityRepository implements Repository<City>, Serializable {

    private final CityModel cityModel = new CityModel();
    private final Connection connection;

    public CityRepository(Connection connection){
        this.connection = connection;
    }

    public boolean save(City obj) {
        return cityModel.add(connection, obj);
    }

    public City saveGet(City obj) {
        return cityModel.addGet(connection, obj);
    }

    @Override
    public PriorityQueue<City> getAll() {
        return cityModel.findAll(connection);
    }

    @Override
    public PriorityQueue<City> meAll() {
        return cityModel.searchAll(connection);
    }

    @Override
    public City getById(Integer id) {
        return cityModel.findById(connection,id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return cityModel.delete(id, connection);
    }

    public boolean clear(Integer id) {return cityModel.clear(id, connection);}

    @Override
    public boolean updateById(Integer id, City city) {
        return cityModel.updateById(id, city, connection);
    }

    public int getMaxId(){
        return cityModel.findMaxId(connection);
    }
}
