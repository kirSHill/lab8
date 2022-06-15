package server.repositories;

import client.gui.GuiModel;
import essentials.elements.City;
import essentials.elements.UserInfo;
import server.models.UserModel;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class UserRepository implements Repository<UserInfo>, Serializable {
    private final UserModel userModel = new UserModel();
    private final Connection connection;
    public UserRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(UserInfo user) {
        return userModel.add(connection,user);
    }

    @Override
    public PriorityQueue<UserInfo> getAll() {
        return new PriorityQueue<>();
    }

    @Override
    public UserInfo getById(Integer id) {
        return userModel.findById(connection,id);
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public boolean updateById(Integer id, UserInfo userInfo) {
        return false;
    }

    public UserInfo getByLogin(String login) {
        return userModel.findByLogin(connection,login);
    }

    @Override
    public PriorityQueue<City> meAll() {return null;}

}
