package server.commands;

import essentials.precommands.ObjectIdPrecommand;
import essentials.precommands.Precommand;
import server.commands.interfaces.Changing;
import server.commands.interfaces.Command;
import essentials.elements.City;
import essentials.interaction.Message;
import server.repositories.CityRepository;
import java.sql.Connection;
import java.util.PriorityQueue;

public class UpdateId extends Add implements Command, Changing {

    private final String argument;
    private Connection connection;

    public UpdateId(Precommand precommand, Connection connection) {
        super(precommand, connection);
        this.connection = connection;
        ObjectIdPrecommand objectIdPrecommand = (ObjectIdPrecommand) precommand;
        this.argument = objectIdPrecommand.getId();
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        CityRepository cityRepository = new CityRepository(connection);
        if (cityRepository.updateById(Integer.parseInt(argument), city)) {
            City city = cityRepository.getById(Integer.parseInt(argument));
            Message message = new Message(true,"success");
            message.setObject(city);
            message.setCommand("updateId");
            return message;
        } else
        return new Message(false, "error");
    }
}