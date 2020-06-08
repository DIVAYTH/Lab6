package commands;

import proga.CollectionManager;

public class RemoveStudentsCount extends AbstractCommand {
    public RemoveStudentsCount(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод удаляет эемент из коллекции по его student count
     *
     * @param str
     * @return
     */
    @Override
    public String execute(String str) throws NumberFormatException {
        if (!(manager.getCol().size() == 0)) {
            int students_count = Integer.parseInt(str);
            if (manager.getCol().removeIf(col -> col.getStudentsCount() != null && col.getStudentsCount() == students_count)) {
                return "Элемент удален";
            } else return "Нет элемента с таким student_count";
        }
        return "Коллекция пуста";
    }
}