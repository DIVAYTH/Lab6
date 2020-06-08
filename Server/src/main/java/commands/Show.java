package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;
import com.google.gson.Gson;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Show extends AbstractCommand {
    Gson gson = new Gson();

    public Show(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод выводит элементы коллекции
     *
     * @return
     */
    @Override
    public String execute() {
        if (manager.getCol().size() != 0) {
            Stream<StudyGroup> stream = manager.getCol().stream();
            return stream.map(col -> gson.toJson(col)).collect(Collectors.joining("\n"));
        }
        return "Коллекция пуста.";
    }
}