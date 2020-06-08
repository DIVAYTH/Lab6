package commands;

import proga.CollectionManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends AbstractCommand {
    public ExecuteScript(CollectionManager manager) {
        super(manager);
    }

    private int stackCount = 0;

    /**
     * Метод выполнеят скрипт из файла
     *
     * @param str
     * @return
     */
    @Override
    public String execute(String str) {
        StringBuilder builder = new StringBuilder();
        String userCommand = "";
        String[] finalUserCommand;
        try {
            BufferedInputStream script = new BufferedInputStream(new FileInputStream(str));
            try (Scanner commandReader = new Scanner(script)) {
                while (commandReader.hasNextLine() && !userCommand.equals("exit")) {
                    userCommand = commandReader.nextLine();
                    finalUserCommand = userCommand.trim().split(" ", 2);
                    if (finalUserCommand.length == 1) {
                        switch (finalUserCommand[0]) {
                            case "add_if_max":
                            case "add_if_min":
                            case "add": {
                                try {
                                    String[] arr = new String[13];
                                    for (int i = 0; i < arr.length; i++) {
                                        userCommand = commandReader.nextLine();
                                        arr[i] = userCommand;
                                    }
                                    builder.append(manager.commandMap.get(finalUserCommand[0]).execute(manager.commandMap.get("AddOverloaded")
                                            .execute(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8]
                                                    , arr[9], arr[10], arr[11], arr[12]))).append("\n");
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    builder.append("Ошибка при добавлении элемента. Поля в файле указаны не верно").append("\n");
                                }
                            }
                            break;
                            case "exit":
                                return "exit";
                            case "help":
                            case "clear":
                            case "show":
                            case "info":
                            case "print_field_ascending_students_count":
                            case "print_field_descending_form_of_education":
                                builder.append(manager.commandMap.get(finalUserCommand[0]).execute()).append("\n");
                                break;
                            default:
                                builder.append("Неизвестная команда.").append("\n");
                        }
                    } else if (finalUserCommand.length == 2) {
                        try {
                            if (finalUserCommand[0].equals("update")) {
                                String[] arr = new String[13];
                                for (int i = 0; i < arr.length; i++) {
                                    userCommand = commandReader.nextLine();
                                    arr[i] = userCommand;
                                }
                                builder.append(manager.commandMap.get(finalUserCommand[0])
                                        .execute(finalUserCommand[1], manager.commandMap.get("AddOverloaded")
                                                .execute(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6],
                                                        arr[7], arr[8], arr[9], arr[10], arr[11], arr[12]))).append("\n");
                            } else if (finalUserCommand[0].equals("execute_script")) {
                                stackCount++;
                                if (stackCount == 2) {
                                    stackCount = 0;
                                    builder.append("На вызов execute_script из execute_script наложено ограничение").append("\n");
                                } else {
                                    execute(finalUserCommand[1]);
                                }
                            } else switch (finalUserCommand[0]) {
                                case "remove_greater":
                                case "remove_by_id":
                                case "remove_any_by_students_count":
                                    builder.append(manager.commandMap.get(finalUserCommand[0]).execute(finalUserCommand[1])).append("\n");
                                    break;
                                default:
                                    builder.append("Неизвестная команда или не указан аргумент").append("\n");
                            }
                        } catch (NumberFormatException e) {
                            builder.append("Введен неверный аргумент").append("\n");
                        } catch (NullPointerException e) {
                            builder.append("Ошибка при добавлении элемента. Поля в файле указаны не верно").append("\n");
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return "Файл не найден";
        }
        return String.valueOf(builder).trim();
    }
}