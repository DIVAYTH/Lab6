package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;

public class Add extends AbstractCommand {
    public Add(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод добавляет элемент в коллекцию
     *
     * @param studyGroup
     * @return
     */
    @Override
    public String execute(StudyGroup studyGroup) {
        long id = ++manager.id;
        studyGroup.setId(id);
        manager.getCol().add(studyGroup);
        return "Элемент коллекции добавлен";
    }
}