package server.commands;

import essentials.precommands.IdPrecommand;
import essentials.precommands.ObjectIdPrecommand;
import essentials.precommands.Precommand;
import server.commands.interfaces.Changing;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import essentials.interaction.UserInteraction;
import server.repositories.CityRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class RemoveById implements Command, Changing {

    private final String argument;
    private Connection connection;

    public RemoveById(Precommand precommand, Connection connection) {
        this.connection = connection;
        IdPrecommand objectIdPrecommand = (IdPrecommand) precommand;
        this.argument = objectIdPrecommand.getId().toString();
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        CityRepository cityRepository = new CityRepository(connection);
        if (cityRepository.deleteById(Integer.parseInt(argument))) {
            Message message = new Message(true,"success");
            message.setObject(Integer.parseInt(argument));
            message.setCommand("removeById");
            return message;
        } else
            return new Message(false, "error");
    }
}
