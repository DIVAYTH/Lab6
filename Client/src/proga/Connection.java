package proga;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private ClientWork client = new ClientWork();

    /**
     * Метод реализует соединение между клиентом и сервером
     */
    public void connection() {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите порт");
                int port = Integer.parseInt(scanner.nextLine());
                System.out.println("Введите хост");
                String host = scanner.nextLine();
                System.out.println("Соединение... Ожидайте");
                try (Socket socket = new Socket(host, port)) {
                    System.out.println("Соединение установлено");
                    while (true) {
                        client.work(socket);
                    }
                } catch (ConnectException e) {
                    System.out.println("Порт не найден или недоступен");
                } catch (IllegalArgumentException e) {
                    System.out.println("Порт должен принимать значения от 1 до 65535");
                } catch (IOException e) {
                    System.out.println("Нет связи с сервером. Подключиться ещё раз введите (да) или (нет)?");
                    String answer;
                    while (!(answer = scanner.nextLine()).equals("да")) {
                        switch (answer) {
                            case "":
                                break;
                            case "нет":
                                System.exit(0);
                                break;
                            default:
                                System.out.println("Введите корректное значение.");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Порт не число или выходит за пределы");
            }
        }
    }
}