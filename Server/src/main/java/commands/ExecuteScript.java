package commands;

import collectionClasses.*;
import proga.CollectionManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class ExecuteScript extends AbstractCommand {
    public ExecuteScript(CollectionManager manager, Map<String, AbstractCommand> commandMap) {
        super(manager);
        this.commandMap = commandMap;
    }

    private Map<String, AbstractCommand> commandMap;
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
                                    builder.append(commandMap.get(finalUserCommand[0]).execute(execute(arr[0], arr[1], arr[2], arr[3], arr[4]
                                            , arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12]))).append("\n");
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
                                builder.append(commandMap.get(finalUserCommand[0]).execute()).append("\n");
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
                                builder.append(commandMap.get(finalUserCommand[0])
                                        .execute(finalUserCommand[1], execute(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6],
                                                arr[7], arr[8], arr[9], arr[10], arr[11], arr[12]))).append("\n");
                            } else if (finalUserCommand[0].equals("execute_script")) {
                                stackCount++;
                                if (stackCount == 1) {
                                    execute(finalUserCommand[1]);
                                    stackCount = 0;
                                } else {
                                    break;
                                }
                            } else switch (finalUserCommand[0]) {
                                case "remove_greater":
                                case "remove_by_id":
                                case "remove_any_by_students_count":
                                    builder.append(commandMap.get(finalUserCommand[0]).execute(finalUserCommand[1])).append("\n");
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

    /**
     * Метод проверяет значения int для перегруженного add
     *
     * @param str
     * @return
     */
    Integer checkInt(String str) {
        Integer values;
        if (str.equals("")) {
            return null;
        } else {
            try {
                values = Integer.parseInt(str);
                return values;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Метод проверяет значения double для перегруженного add
     *
     * @param str
     * @return
     */
    Double checkDouble(String str) {
        Double values;
        if (str.equals("")) {
            return null;
        } else {
            try {
                values = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return values;
    }

    /**
     * Метод проверяет значения String для перегруженного add
     *
     * @param str
     * @return
     */
    String checkName(String str) {
        String name;
        if (str.equals("")) {
            return null;
        } else {
            name = str;
            return name;
        }
    }

    /**
     * Метод проверяет значения int для перегруженного add с возможным null
     *
     * @param str
     * @return
     */
    Integer checkIntWithNull(String str) {
        Integer values;
        if (str.equals("")) {
            values = null;
            return values;
        } else {
            try {
                values = Integer.parseInt(str);
                return values;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Перегруженный метод add для execute_script
     *
     * @param str1
     * @param str2
     * @param str3
     * @param str4
     * @param str5
     * @param str6
     * @param str7
     * @param str8
     * @param str9
     * @param str10
     * @param str11
     * @param str12
     * @param str13
     * @return
     */
    @Override
    public StudyGroup execute(String str1, String str2, String str3, String str4, String str5, String str6,
                              String str7, String str8, String str9, String str10,
                              String str11, String str12, String str13) {
        StudyGroup studyGroup;
        FormOfEducation formOfEducation;
        Semester semesterEnum;
        Color hairColor;
        Country nationality;
        String name;
        int x;
        double y;
        int studentsCount;
        String per_name;
        Integer height;
        double loc_x;
        int loc_y;
        int loc_z;
        try {
            name = checkName(str1);
            x = checkInt(str2);
            y = checkDouble(str3);
            studentsCount = checkIntWithNull(str4);
            per_name = checkName(str7);
            height = checkIntWithNull(str8);
            loc_x = checkDouble(str11);
            loc_y = checkInt(str12);
            loc_z = checkInt(str13);
        } catch (NullPointerException e) {
            return null;
        }

        try {
            if (str5.equals("")) {
                formOfEducation = null;
            } else {
                formOfEducation = FormOfEducation.valueOf(str5);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            if (str6.equals("")) {
                semesterEnum = null;
            } else {
                semesterEnum = Semester.valueOf(str6);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            if (str9.equals("")) {
                hairColor = null;
            } else {
                hairColor = Color.valueOf(str9);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            if (str10.equals("")) {
                return null;
            } else {
                nationality = Country.valueOf(str10);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        long id = 0;
        studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
        return studyGroup;
    }
}