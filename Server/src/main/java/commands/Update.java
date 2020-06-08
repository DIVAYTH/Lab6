package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;

public class Update extends AbstractCommand {
    public Update(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод обновляет элемент в коллекции по его id
     *
     * @param str
     * @param studyGroup
     * @return
     */
    @Override
    public String execute(String str, StudyGroup studyGroup) throws NumberFormatException {
        if (!(studyGroup == null)) {
            if (!(manager.getCol().size() == 0)) {
                int idNew = Integer.parseInt(str);
                if (manager.getCol().removeIf(col -> col.getId() == idNew)) {
                    studyGroup.setId(idNew);
                    manager.getCol().add(studyGroup);
                    return "Элемент обновлен";
                } else {
                    return "Элемента с таким id нет";
                }
            }
            return "Коллекция пуста";
        } else {
            return "Ошибка при добавлении элемента. Поля указаны не верно";
        }
    }
}