package commands;

import proga.CollectionManager;

public class RemoveId extends AbstractCommand {
    public RemoveId(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод удаляет элемент из коллекции по его id
     *
     * @param str
     * @return
     */
    @Override
    public String execute(String str) throws NumberFormatException {
        if (!(manager.getCol().size() == 0)) {
            int id = Integer.parseInt(str);
            if (manager.getCol().removeIf(col -> col.getId() == id)) {
                return "Элемент удален";
            } else return "Нет элемента с таким id";
        } else {
            return "Коллекция пуста";
        }
    }
}