package proga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    private ByteBuffer buffer = ByteBuffer.allocate(4096);

    /**
     * Метод получает команду от клиента
     *
     * @param key
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Command receiveCommand(SelectionKey key) throws IOException, ClassNotFoundException {
        Command command = null;
        SocketChannel channel = (SocketChannel) key.channel();
        int count = channel.read(buffer);
        if (count > -1) {
            byte[] buf = buffer.array();
            ObjectInputStream fromClient = new ObjectInputStream(new ByteArrayInputStream(buf));
            command = (Command) fromClient.readObject();
            fromClient.close();
            buffer.clear();
            logger.info("Выполнена команда " + command.getName());
            key.interestOps(SelectionKey.OP_WRITE);
        }
        if (count == -1) {
            key.cancel();
        }
        return command;
    }
}