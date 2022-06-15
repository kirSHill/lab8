package server.commands;

import essentials.elements.City;
import essentials.elements.UserInfo;
import essentials.interaction.Message;
import server.commands.interfaces.Command;
import server.repositories.UserRepository;

import java.sql.Connection;
import java.util.PriorityQueue;

public class Register implements Command {

    private UserInfo userInfo;
    private Connection connection;
    private UserRepository userRepository;

    public Register(UserInfo userInfo, Connection connection){
        this.userInfo = userInfo;
        this.connection = connection;
        this.userRepository = new UserRepository(connection);
    }

    @Override
    public Message execute(PriorityQueue<City> collection) {
        UserInfo newUser = userRepository.getByLogin(userInfo.getLogin());
        if (newUser == null){
            if (userRepository.save(userInfo)){
                UserInfo newUser1 = userRepository.getByLogin(userInfo.getLogin());
                Message message = new Message(true,"Регистрация пройдена!");
                message.setObject(newUser1.getId() + " " + userInfo.getNickname());
                message.setCommand("register");
                System.out.println(message.getObject());
                return message;
            } else {
                return new Message(false,"Возникла ошибка при регистрации.");
            }
        } else {
            return new Message(false,"Пользователь с таким логином уже существует.");
        }
    }
}
