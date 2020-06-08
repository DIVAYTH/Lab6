package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintStudentsCount extends AbstractCommand {
    public PrintStudentsCount(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод выводит students count в порядке возрастания
     *
     * @return
     */
    @Override
    public String execute() {
        if (!(manager.getCol().size() == 0)) {
            Stream<StudyGroup> stream = manager.getCol().stream();
            return stream.filter(col -> col.getStudentsCount() != null).sorted(new ComparatorByStudentCount())
                    .map(col -> "students count" + " - " + col.getStudentsCount()).collect(Collectors.joining("\n"));
        }
        return "Коллекция пустая";
    }
}