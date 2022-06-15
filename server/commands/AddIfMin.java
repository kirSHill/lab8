package server.commands;


import essentials.elements.City;
import essentials.interaction.Message;
import essentials.precommands.Precommand;
import server.commands.interfaces.Changing;
import server.repositories.CityRepository;
import server.repositories.UserRepository;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class AddIfMin extends Add implements Changing {

    private final boolean fromScript;
    private int update;
    protected City city;
    protected CityRepository cityRepository;
    private int count;

    public AddIfMin(Precommand precommand, Connection connection) {
        super(precommand, connection);
        this.fromScript = precommand.isFromScript();
        this.city = (City) precommand.getArgument();
        this.cityRepository = new CityRepository(connection);
        UserRepository userRepository = new UserRepository(connection);
        this.city.setClientId(userRepository.getByLogin(precommand.getClient()).getId());
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        this.city.setCreationDate(LocalDateTime.now());
        this.city.setId();
        collection = cityRepository.meAll();
        for (City city1 : collection) {
            if (this.city.getPopulation() >= city1.getPopulation()) {
                count++;
            }
        }
        if (count == 0) {
            this.city = cityRepository.saveGet(this.city);
            if (city != null) {
                collection.add(this.city);
                Message message = new Message(true, "AddSuccess");
                message.setObject(this.city);
                message.setCommand("addIfMin");
                return message;
            }
        } else {
            return new Message(false, "City is not minimal");
        }
        return new Message(false,"Error");
    }
}
