package server.commands;

import essentials.elements.City;
import essentials.interaction.Message;
import essentials.precommands.Precommand;
import server.commands.interfaces.Command;
import server.repositories.CityRepository;
import server.repositories.UserRepository;

import java.sql.Connection;
import java.util.PriorityQueue;

public class GetNickname implements Command {

    private String client;
    private Connection connection;

    public GetNickname(Precommand precommand, Connection connection) {
        this.client = precommand.getClient();
        this.connection = connection;
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        UserRepository userRepository = new UserRepository(connection);
        String message = userRepository.getByLogin(client).getNickname();
        return new Message(true, message);
    }
}
