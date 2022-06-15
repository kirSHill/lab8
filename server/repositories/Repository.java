package server.repositories;

import client.gui.GuiModel;
import essentials.elements.City;
import essentials.elements.UserInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

public interface Repository<T> extends Serializable {

    boolean save(T obj);

    PriorityQueue<T> getAll();

    T getById(Integer id);

    boolean deleteById(Integer id);

    boolean updateById(Integer id, T obj);

    PriorityQueue<City> meAll();
}
