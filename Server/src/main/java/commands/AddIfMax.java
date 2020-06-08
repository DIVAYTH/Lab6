package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;

import java.util.Comparator;
import java.util.stream.Stream;

public class AddIfMax extends AbstractCommand {
    public AddIfMax(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод добавляет жлемент в коллекцию, если его height больше максимально
     *
     * @param studyGroup
     * @return
     */
    @Override
    public String execute(StudyGroup studyGroup) {
        if (!(manager.getCol().size() == 0)) {
            Stream<StudyGroup> stream = manager.getCol().stream();
            Integer heightMAX = stream.filter(col -> col.getGroupAdmin().getHeight() != null)
                    .max(Comparator.comparingInt(p -> p.getGroupAdmin().getHeight())).get().getGroupAdmin().getHeight();
            if (studyGroup.getGroupAdmin().getHeight() != null && studyGroup.getGroupAdmin().getHeight() > heightMAX) {
                long id = ++manager.id;
                studyGroup.setId(id);
                manager.getCol().add(studyGroup);
                return "Элемент коллекции сохранен, так как его height больше height других элементов коллекции";
            } else {
                return "Элемент коллекции не сохранен, так как его height меньше height других элементов коллекции или равен null";
            }
        } else {
            return "Коллекция пуста";
        }
    }
}