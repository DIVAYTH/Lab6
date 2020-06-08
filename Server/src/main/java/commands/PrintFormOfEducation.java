package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintFormOfEducation extends AbstractCommand {
    public PrintFormOfEducation(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод выводит FormOfEducation в порядке убывания
     *
     * @return
     */
    @Override
    public String execute() {
        if (manager.getCol().size() != 0) {
            Stream<StudyGroup> stream = manager.getCol().stream();
            return stream.filter(col -> col.getFormOfEducation() != null).sorted(new ComparatorByFormOfEducation())
                    .map(col -> "formOfEducation" + " - " + col.getFormOfEducation()).collect(Collectors.joining("\n"));
        }
        return "Коллекция пустая";
    }
}