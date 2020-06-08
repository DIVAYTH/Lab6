package proga;

import collectionClasses.StudyGroup;

import java.io.Serializable;

/**
 * Класс для передачи команд в виде объекта
 */
public class Command implements Serializable {
    private static final long serialVersionUID = 17L;
    String name;
    String args;
    StudyGroup studyGroup;

    public Command(String name, String args) {
        this.name = name;
        this.args = args;
    }

    public Command(String name) {
        this.name = name;
    }

    public Command(String name, StudyGroup studyGroup) {
        this.name = name;
        this.studyGroup = studyGroup;
    }

    public String getName() {
        return name;
    }

    public String getArgs() {
        return args;
    }

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public Command(String name, String args, StudyGroup studyGroup) {
        this.name = name;
        this.args = args;
        this.studyGroup = studyGroup;
    }
}
