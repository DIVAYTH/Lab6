package commands;

import proga.CollectionManager;

public class Clear extends AbstractCommand {

    public Clear(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод очищает коллекцию
     *
     * @return
     */
    @Override
    public String execute() {
        manager.getCol().clear();
        return "Коллекция очищена";
    }
}