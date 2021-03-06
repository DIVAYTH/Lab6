package proga;

import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    System.out.println("Отключение клиента");
                }
            });
            System.out.println("Запуск клиента...");
            Connection client = new Connection();
            client.connection();
        } catch (NoSuchElementException e) {
            // для ctrl+D
        }
    }
}