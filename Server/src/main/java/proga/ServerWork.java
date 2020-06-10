package proga;

import commands.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class ServerWork {
    public ServerWork(Map<String, AbstractCommand> commandMap) {
        this.commandMap = commandMap;
    }

    private Map<String, AbstractCommand> commandMap;
    private static final Logger logger = LoggerFactory.getLogger(ServerWork.class);

    /**
     * Метод получает команду от клиента
     *
     * @param key
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Command receiveCommand(SelectionKey key) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        Command command;
        SocketChannel channel = (SocketChannel) key.channel();
        int available = channel.read(buffer);
        System.out.println(available);
        while (available > 0) {
            available = channel.read(buffer);
        }
        byte[] buf = buffer.array();
        ObjectInputStream serialize = new ObjectInputStream(new ByteArrayInputStream(buf));
        command = (Command) serialize.readObject();
        serialize.close();
        buffer.clear();
        logger.info("Выполнена команда " + command.getName());
        key.interestOps(SelectionKey.OP_WRITE);
        return command;
    }

    /**
     * Метод отправляет результат выполнения команды на клиент
     *
     * @param command
     * @throws IOException
     */
    public void send(SelectionKey key, Command command) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream toClient = new ObjectOutputStream(baos);
        switch (command.getName()) {
            case "clear":
            case "show":
            case "info":
            case "help":
            case "print_field_ascending_students_count":
            case "print_field_descending_form_of_education": {
                toClient.writeObject(commandMap.get(command.getName()).execute());
            }
            break;
            case "remove_greater":
            case "remove_by_id":
            case "remove_any_by_students_count":
            case "execute_script": {
                toClient.writeObject(commandMap.get(command.getName()).execute(command.getArgs()));
            }
            break;
            case "add_if_max":
            case "add_if_min":
            case "add": {
                toClient.writeObject(commandMap.get(command.getName()).execute(command.getStudyGroup()));
            }
            break;
            case "update": {
                toClient.writeObject(commandMap.get(command.getName()).execute(command.getArgs(), command.getStudyGroup()));
            }
            break;
        }
        ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
        int available = channel.write(buffer);
        while (available > 0) {
            available = channel.write(buffer);
        }
        baos.close();
        toClient.close();
        key.interestOps(SelectionKey.OP_READ);
    }
}