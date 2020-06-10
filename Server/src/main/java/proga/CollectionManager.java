package proga;

import collectionClasses.StudyGroup;
import commands.*;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionManager {
    public Map<String, AbstractCommand> commandMap;
    public static CollectionManager manager = new CollectionManager();
    private PriorityQueue<StudyGroup> col = new PriorityQueue<>();
    private Gson gson = new Gson();
    public long id = 0;
    private static final Logger logger = LoggerFactory.getLogger(CollectionManager.class);

    public PriorityQueue<StudyGroup> getCol() {
        return col;
    }

    {
        commandMap = new HashMap<>();
        commandMap.put("clear", new Clear(manager));
        commandMap.put("show", new Show(manager));
        commandMap.put("info", new Info(manager));
        commandMap.put("help", new Help(manager));
        commandMap.put("remove_any_by_students_count", new RemoveStudentsCount(manager));
        commandMap.put("print_field_ascending_students_count", new PrintStudentsCount(manager));
        commandMap.put("print_field_descending_form_of_education", new PrintFormOfEducation(manager));
        commandMap.put("remove_by_id", new RemoveId(manager));
        commandMap.put("add", new Add(manager));
        commandMap.put("remove_greater", new RemoveGreater(manager));
        commandMap.put("add_if_min", new AddIfMin(manager));
        commandMap.put("add_if_max", new AddIfMax(manager));
        commandMap.put("update", new Update(manager));
        commandMap.put("execute_script", new ExecuteScript(manager));
    }

    public static void main(String[] args) throws IOException {
        logger.debug("Запуск сервера");
        Connection connection = new Connection();
        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    logger.debug("Отключение сервера");
                    manager.save();
                }
            });
            manager.load(new File(args[0]));
            connection.connection();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Вы не ввели имя файла");
        } catch (NoSuchElementException e) {
            //Для ctrl+D
        }
    }

    /**
     * Метод сохраняет коллекцию
     */
    public void save() {
        try {
            File outfile = new File("Groups.json");
            FileOutputStream fileOutputStream = new FileOutputStream(outfile);
            String out = gson.toJson(col);
            fileOutputStream.write(out.getBytes());
            fileOutputStream.close();
            logger.debug("Коллекция сохранена");
        } catch (IOException e) {
            logger.error("Ошибка записи");
        }
    }

    /**
     * Метод загружает файл и проверяет поля
     *
     * @param file
     * @throws IOException
     */
    public void load(File file) throws IOException {
        int beginSize = col.size();
        if (!file.exists()) {
            logger.error("Файла по указанному пути не существует.");
            System.exit(1);
        }
        if (!file.canRead() || !file.canWrite()) {
            logger.error("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            System.exit(1);
        }
        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            logger.error("Файл загружен");
            StringBuilder result = new StringBuilder();
            String nextL;
            while ((nextL = inputStreamReader.readLine()) != null) {
                result.append(nextL);
            }
            Type collectionType = new TypeToken<PriorityQueue<StudyGroup>>() {
            }.getType();
            Type collectionType2 = new TypeToken<List<StudyGroup>>() {
            }.getType();
            try {
                List<StudyGroup> groupList = gson.fromJson(String.valueOf(result), collectionType2);
                try {
                    for (StudyGroup s : groupList) {
                        if (s.getName() == null) {
                            throw new BadArgument("name не может быть null");
                        }
                        if (s.getName().equals("")) {
                            throw new BadArgument("Строка name не может быть пустой");
                        }
                        if (s.getCoordinates() == null) {
                            throw new BadArgument("coordinates не может быть null");
                        }
                        if (s.getCoordinates().getX() == null) {
                            throw new BadArgument("x не может быть null");
                        }
                        if (s.getStudentsCount() < 0) {
                            throw new BadArgument("StudentsCount должен быть больше нуля");
                        }
                        if (s.getGroupAdmin() == null) {
                            throw new BadArgument("groupAdmin не может быть null");
                        }
                        if (s.getGroupAdmin().getName() == null) {
                            throw new BadArgument("name не может быть null");
                        }
                        if (s.getGroupAdmin().getName().equals("")) {
                            throw new BadArgument("Строка name не может быть пустой");
                        }
                        if (s.getGroupAdmin().getHeight() <= 0) {
                            throw new BadArgument("height должен быть больше 0");
                        }
                        if (s.getGroupAdmin().getNationality() == null) {
                            throw new BadArgument("nationality не может быть null");
                        }
                        if (s.getGroupAdmin().getLocation() == null) {
                            throw new BadArgument("location не может быть null");
                        }
                        if (s.getGroupAdmin().getLocation().getZ() == null) {
                            throw new BadArgument("z не может быть null");
                        }
                    }
                    PriorityQueue<StudyGroup> priorityQueue = gson.fromJson(result.toString(), collectionType);
                    for (StudyGroup s : priorityQueue) {
                        s.setCreationDate();
                        col.add(s);
                        if (s.getId() > id) {
                            id = s.getId();
                        }
                    }
                    logger.debug("Коллекция успешно загружена. Добавлено " + (col.size() - beginSize) + " элемента.");
                } catch (BadArgument badArgument) {
                    logger.error(badArgument.getMessage());
                    logger.error("Коллекция очищена. Загружена пустая коллекция");
                }
            } catch (JsonSyntaxException e) {
                logger.error("Ошибка синтаксиса Json. Коллекция не может быть загружена.");
                System.exit(1);
            } catch (NullPointerException e) {
                logger.error("Загружен пустой файл");
            }
        }
    }
}