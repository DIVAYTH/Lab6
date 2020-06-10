package proga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class Connection {
    private static final Logger logger = LoggerFactory.getLogger(Connection.class);
    private CollectionManager manager = new CollectionManager();
    private ServerWork server = new ServerWork(manager.commandMap);
    private Scanner scanner = new Scanner(System.in);
    private Command command;

    /**
     * Метод реализует соединение и работу с клиентом
     *
     * @throws IOException
     */
    public void connection() throws IOException {
        while (true) {
            try {
                System.out.println("Введите порт");
                int port = Integer.parseInt(scanner.nextLine());
                Selector selector = Selector.open();
                try (ServerSocketChannel socketChannel = ServerSocketChannel.open()) {
                    socketChannel.bind(new InetSocketAddress(port));
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    logger.debug("Сервер запущен");
                    logger.debug("Сервер ожидает подключения");
                    while (selector.isOpen()) {
                        int count = selector.select();
                        if (count == 0) {
                            continue;
                        }
                        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                        while (iter.hasNext()) {
                            SelectionKey key = iter.next();
                            try {
                                if (key.isAcceptable()) {
                                    SocketChannel channel = socketChannel.accept();
                                    logger.debug("К серверу подключился клиент");
                                    channel.configureBlocking(false);
                                    channel.register(selector, SelectionKey.OP_READ);
                                }
                                if (key.isReadable()) {
                                    command = server.receiveCommand(key);
                                }
                                if (key.isWritable()) {
                                    server.send(key, command);
                                }
                                iter.remove();
                            } catch (Exception e) {
                                logger.error("Клиент отключился");
                                key.cancel();
                            }
                        }
                    }
                }
            } catch (BindException e) {
                logger.error("Такой порт уже используется");
            } catch (NumberFormatException e) {
                logger.error("Порт не число или выходит за пределы");
            } catch (IllegalArgumentException e) {
                logger.error("Порт должен принимать значения от 1 до 65535");
            } catch (SocketException e) {
                logger.error("Недопустимый порт");
            }
        }
    }
}