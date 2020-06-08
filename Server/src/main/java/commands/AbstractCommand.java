package commands;

import collectionClasses.StudyGroup;
import proga.CollectionManager;

import java.nio.ByteBuffer;

/**
 * Класс наследник для всех команд
 */
public class AbstractCommand {
    CollectionManager manager;

    public AbstractCommand(CollectionManager manager) {
        this.manager = manager;
    }

    public String execute() {
        return null;
    }

    public String execute(String str) {
        return null;
    }

    public String execute(StudyGroup studyGroup) {
        return null;
    }

    public String execute(String str, StudyGroup studyGroup) {
        return null;
    }

    public StudyGroup execute(String str1, String str2, String str3, String str4, String str5, String str6, String str7,
                          String str8, String str9, String str10, String str11, String str12, String str13) {
        return null;
    }
}