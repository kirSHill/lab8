package server.commands;

import client.gui.GuiModel;
import essentials.precommands.Precommand;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import server.repositories.CityRepository;

import java.io.Serializable;
import java.sql.Connection;
import java.util.PriorityQueue;

public class Show implements Command, Serializable {

    protected CityRepository cityRepository;

    public Show(Connection connection) {
        this.cityRepository = new CityRepository(connection);
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        PriorityQueue<City> collection1 =  cityRepository.meAll();
        Message message = new Message(true,"show success");
        message.setObject(collection1);
        message.setCommand("show");
        return message;
    }
}
