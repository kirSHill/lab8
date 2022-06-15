package server;

import client.Client;
import server.commands.*;
import essentials.Xml;
import server.commands.interfaces.Changing;
import server.commands.interfaces.Command;
import server.commands.interfaces.Date;
import essentials.elements.QueueInfo;
import essentials.interaction.ConsoleInteraction;
import essentials.interaction.Message;
import essentials.elements.City;
import essentials.interaction.UserInteraction;
import essentials.precommands.Precommand;
import server.repositories.CityRepository;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Server implements Serializable{

    private static final UserInteraction interaction = new ConsoleInteraction();
    private static PriorityQueue<City> collection = new PriorityQueue<>();
    public static File file;
    public static final int port = 7182;
    private static LocalDateTime creationDate = LocalDateTime.now();
    private static LocalDateTime initDate = LocalDateTime.now();
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private static ServerSocket serverSocket;
    private static HashMap<String,String> users = new HashMap<>();
    private static final int maxConnections = 15;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final ForkJoinPool requestPool = new ForkJoinPool(maxConnections);
    private static final ExecutorService processingPool = Executors.newFixedThreadPool(maxConnections);
    private static final ExecutorService answerPool = Executors.newCachedThreadPool();
    private static Connection connection;
    private static final HashMap<String, UserConnection> connectionMap = new HashMap<>();


    public static void main(String[] args) {
        if (System.getenv("FILE_LOC") != null && !System.getenv("FILE_LOC").trim().isEmpty()) {
            file = new File(System.getenv("FILE_LOC"));
            log.info("Переменная окружения установлена!\n\nПодготовка к запуску.");
        }
        if(!prepare()) {
            log.info("Остановка запуска.");
            return;
        }
        if (start()) {
            InetAddress inetAddress = serverSocket.getInetAddress();
            interaction.print(true, "Сервер запущен по адресу: " + inetAddress.getHostAddress() + ":" + port);
        } else {
            return;
        }
        work();
    }

    private static boolean uploadInformation() {
        File file = new File("db.cfg");
        if (!Setup.createConnection(interaction,file)) {
            return false;
        }
        Setup.createTables(interaction);
        connection = Setup.getConnection();
        CityRepository cityRepository = new CityRepository(connection);
        collection = cityRepository.getAll();
        if (collection == null) {
            log.info("Коллекция пуста.\n");
        } else {
            log.info("Загружено " + (long)collection.size() + " элементов коллекции.\n");
        }
        return true;
    }

    private static boolean prepare() {
        if (!uploadInformation()) {
            return false;
        }
        return createShutDownHook();
    }

    private static void work() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                String user = socket.getInetAddress().toString() + ":" + socket.getPort();
                synchronized (connectionMap) {
                    connectionMap.put(user, new UserConnection(socket));
                }
                users.put(user,"");
                log.info(String.format("Клиент %s присоединился!\n", user));
                requestPool.execute(new RequestService(user));
            } catch (IOException e) {
                log.info("Ошибка при соеднинении.\n");
            }
        }
    }

    private static boolean createShutDownHook() {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.info("Остановка сервера.\n");
            }));
            return true;
        } catch (Exception e) {
            log.info("Не удалось настроить условие выхода.\n");
            return false;
        }
    }

    private static boolean start() {
        try {
            serverSocket = new ServerSocket(port);
            return true;
        } catch (IOException e) {
            interaction.print(true,"Невозможно запустить сервер. " + e.getMessage());
            return false;
        }
    }

//    static class AnswerService implements Runnable {
//
//        ObjectOutputStream out;
//        Message message;
//
//        public AnswerService(ObjectOutputStream outputStream, Message message) {
//            this.message = message;
//            this.out = outputStream;
//            interaction.print(true, message.getText());
//        }
//
//
//        @Override
//        public void run() {
//            try {
//                out.writeObject(message);
//                out.flush();
//            } catch (IOException e) {
//                log.info("Клиент отсоединился.\n");
//            }
//        }
//    }
//
//    static class ProcessingService implements Runnable {
//
//        private final Precommand precommand;
//        private String user = "";
//
//        public ProcessingService(Precommand precommand, String user) {
//            this.precommand = precommand;
//            this.user = user;
//        }
//
//        @Override
//        public void  run() {
//            lock.lock();
//            Message message;
//            Command command = Commander.getCommand(precommand, connection);
//            interaction.print(true, command.toString());
//            if (command instanceof Register) {
//                if (!users.get(user).isEmpty()) {
//                    message = new Message(false, "Пользователь уже авторизован!");
//                } else {
//                    message = command.execute(collection);
//                }
//            } else if (command instanceof Authorize) {
//                if (!users.get(user).isEmpty()) {
//                    message = new Message(false, "Пользователь уже авторизован!");
//                } else {
//                    message = command.execute(collection);
//                    if (message.isSuccessful()) {
//                        if (users.containsValue(message.getText().substring(40))) {
//                            message = new Message(false, " пользователь с таким именем уже авторизован!");
//                        } else {
//                            users.put(user,message.getText().substring(40));
//                            log.info("Пользователь " + users.get(user) + " авторизован.");
//                        }
//                    }
//                }
//            } else if (command instanceof Date && !users.get(user).isEmpty()) {
//                message = ((Date) command).execute(collection, initDate);
//            } else {
//                if (command != null && !users.get(user).isEmpty()) {
//                    try {
//                        message = command.execute(collection);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        message = new Message(true,"Ошибка.");
//                    }
//                } else if (users.get(user).isEmpty()) {
//                    message = new Message(true, " Выполнять команды могут исключительно авторизованные пользователи!");
//                } else {
//                    message = new Message(false, "Ошибка при выполнении команды.");
//                }
//                    if (command instanceof Changing && message.isSuccessful()) {
//                        ObjectOutputStream outputStream;
//                        for (String name : connectionMap.keySet()) {
//                            answerPool.submit(new AnswerService(name, message));
//                        }
//                        lock.unlock();
//                        return;
//                    }
//            }
//            lock.unlock();
//            answerPool.submit(new AnswerService(this.user, message));
//        }
//    }
//
//    static class RequestService extends ForkJoinTask {
//
//        private final String user;
//
//        public RequestService(String user) {
//            this.user = user;
//        }
//
//        @Override
//        public Object getRawResult() {
//            return null;
//        }
//
//        @Override
//        public void setRawResult(Object value) {
//        }
//
//        @Override
//        public boolean exec() {
//            try {
//                while(connectionMap.get(user).isSocketConnected()) {
//                    Precommand precommand = null;
//                    try {
//                        precommand = (Precommand) connectionMap.get(user).read();
//                    } catch (ClassNotFoundException e) {
//                        answerPool.submit(new AnswerService(this.user, new Message(false,"Ошибка при выполнении данной команды.")));
//                    } catch (IOException e) {
//                        break;
//                    }
//                    processingPool.submit(new ProcessingService(this.user, precommand));
//                }
//                log.info(String.format("Клиент %s:%s отключился!", this.user));
//                synchronized (connectionMap) {
//                    connectionMap.remove(this.user);
//                }
//                users.remove(this.user);
//            } catch (Exception e) {
//            }
//            synchronized (connectionMap) {
//                connectionMap.remove(this.user);
//            }
//            users.remove(this.user);
//            return false;
//        }
//    }

    static class AnswerService implements Runnable {

        private final String user;
        private final Message message;

        public AnswerService(String user, Message message) {
            this.message = message;
            this.user = user;
            interaction.print(true, message.getText());
        }


        @Override
        public void run() {
            try {
                synchronized (connectionMap) {
                    connectionMap.get(this.user).send(message);
                }
            } catch (IOException e) {
                log.info("Клиент отсоединился.\n");
            }
        }
    }

    static class ProcessingService implements Runnable {

        private final Precommand precommand;
        private String user = "";

        public ProcessingService(String user, Precommand precommand) {
            this.precommand = precommand;
            this.user = user;
        }

        @Override
        public void  run() {
            Message message;
            lock.lock();
            precommand.setClient(users.get(user));
            System.out.println(precommand.getCommandName());
            Command command = Commander.getCommand(precommand, connection);
            interaction.print(true, command.toString());
            if (command instanceof Register) {
                if (!users.get(user).isEmpty()) {
                    message = new Message(false, "Пользователь уже авторизован!");
                } else {
                    message = command.execute(collection);
                }
            } else if (command instanceof Authorize) {
                if (!users.get(user).isEmpty()) {
                    message = new Message(false, "Пользователь уже авторизован!");
                } else {
                    message = command.execute(collection);
                    if (message.isSuccessful()) {
                        if (users.containsValue(message.getText().substring(40))) {
                            message = new Message(false, " пользователь с таким именем уже авторизован!");
                        } else {
                            users.put(user, message.getText().substring(40));
                            log.info("Пользователь " + users.get(user) + " авторизован.");
                        }
                    }
                }
            } else if (command instanceof Exit) {
                message = null;
                log.info(String.format("Клиент %s отключился!", this.user));
                synchronized (connectionMap) {
                    connectionMap.remove(this.user);
                }
                users.remove(this.user);
            }
            else if (command instanceof Date && !users.get(user).isEmpty()) {
                message = ((Date) command).execute(collection, initDate);
            } else {
                if (command != null && !users.get(user).isEmpty()) {
                    try {
                        message = command.execute(collection);
                    } catch (Exception e) {
                        e.printStackTrace();
                        message = new Message(true,"Ошибка.");
                    }
                } else if (users.get(user).isEmpty()) {
                    message = new Message(true, " Выполнять команды могут исключительно авторизованные пользователи!");
                } else {
                    message = new Message(false, "Ошибка при выполнении команды.");
                }
                synchronized (connectionMap) {
                    if (command instanceof Changing && message.isSuccessful()) {
                        for (String name : connectionMap.keySet()) {
                            answerPool.submit(new AnswerService(name, message));
                        }
                        lock.unlock();
                        return;
                    }
                }
            }
            lock.unlock();
            answerPool.submit(new AnswerService(this.user, message));
        }
    }

    static class RequestService extends ForkJoinTask {

        private final String user;

        public RequestService(String user) {
            this.user = user;
        }

        @Override
        public Object getRawResult() {
            return null;
        }

        @Override
        public void setRawResult(Object value) {
        }

        @Override
        public boolean exec() {
            try {
                while(connectionMap.get(user).isSocketConnected()) {
                    Precommand precommand = null;
                    try {
                        precommand = (Precommand) connectionMap.get(user).read();
                        System.out.println(precommand);
                    } catch (ClassNotFoundException e) {
                        answerPool.submit(new AnswerService(this.user, new Message(false,"Ошибка при выполнении данной команды.")));
                    } catch (IOException e) {
                        break;
                    }
                    processingPool.submit(new ProcessingService(this.user, precommand));
                }
                log.info(String.format("Клиент %s отключился!", this.user));
                synchronized (connectionMap) {
                    connectionMap.remove(this.user);
                }
                users.remove(this.user);
            } catch (Exception e) {
            }
            synchronized (connectionMap) {
                connectionMap.remove(this.user);
            }
            users.remove(this.user);
            return false;
        }
    }

    static class UserConnection {
        Socket socket;
        ObjectOutputStream outputStream;
        ObjectInputStream inputStream;
        boolean isAuthorize = false;

    public UserConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public boolean isSocketConnected() {
        return this.socket.isConnected();
    }

    public void authorize() {
        this.isAuthorize = true;
    }

    public Object read() throws IOException, ClassNotFoundException {
        return this.inputStream.readObject();
    }

    public void send(Object o) throws IOException {
        this.outputStream.writeObject(o);
    }





    }
}


