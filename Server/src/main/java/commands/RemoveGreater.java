package commands;

import proga.CollectionManager;

public class RemoveGreater extends AbstractCommand {
    public RemoveGreater(CollectionManager manager) {
        super(manager);
    }

    /**
     * Удаляет из коллекции все элементы, превышающие заданный height
     *
     * @param str
     * @return
     */
    @Override
    public String execute(String str) throws NumberFormatException {
        if (!(manager.getCol().size() == 0)) {
            int oldSize = manager.getCol().size();
            int height = Integer.parseInt(str);
            if (manager.getCol().removeIf(col -> col.getGroupAdmin().getHeight() != null && col.getGroupAdmin().getHeight() > height)) {
                int newSize = oldSize - manager.getCol().size();
                if (newSize == 1) {
                    return "Был удален " + newSize + " элемент коллекции";
                } else {
                    return "Было удалено " + newSize + " элемента коллекции";
                }
            } else {
                return "Коллекция не изменина, так как height всех элементов меньше указанного";
            }
        } else {
            return "Коллекция пуста";
        }
    }
}