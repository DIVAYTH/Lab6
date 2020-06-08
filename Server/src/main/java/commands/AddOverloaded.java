package commands;

import collectionClasses.*;
import proga.CollectionManager;

public class AddOverloaded extends AbstractCommand {
    public AddOverloaded(CollectionManager manager) {
        super(manager);
    }

    /**
     * Метод проверяет значения int для перегруженного add
     *
     * @param str
     * @return
     */
    Integer checkInt(String str) {
        Integer values;
        if (str.equals("")) {
            return null;
        } else {
            try {
                values = Integer.parseInt(str);
                return values;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Метод проверяет значения double для перегруженного add
     *
     * @param str
     * @return
     */
    Double checkDouble(String str) {
        Double values;
        if (str.equals("")) {
            return null;
        } else {
            try {
                values = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return values;
    }

    /**
     * Метод проверяет значения String для перегруженного add
     *
     * @param str
     * @return
     */
    String checkName(String str) {
        String name;
        if (str.equals("")) {
            return null;
        } else {
            name = str;
            return name;
        }
    }

    /**
     * Метод проверяет значения int для перегруженного add с возможным null
     *
     * @param str
     * @return
     */
    Integer checkIntWithNull(String str) {
        Integer values;
        if (str.equals("")) {
            values = null;
            return values;
        } else {
            try {
                values = Integer.parseInt(str);
                return values;
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Перегруженный метод add для execute_script
     *
     * @param str1
     * @param str2
     * @param str3
     * @param str4
     * @param str5
     * @param str6
     * @param str7
     * @param str8
     * @param str9
     * @param str10
     * @param str11
     * @param str12
     * @param str13
     * @return
     */
    @Override
    public StudyGroup execute(String str1, String str2, String str3, String str4, String str5, String str6,
                              String str7, String str8, String str9, String str10,
                              String str11, String str12, String str13) {
        StudyGroup studyGroup;
        FormOfEducation formOfEducation;
        Semester semesterEnum;
        Color hairColor;
        Country nationality;
        String name;
        int x;
        double y;
        int studentsCount;
        String per_name;
        Integer height;
        double loc_x;
        int loc_y;
        int loc_z;
        try {
            name = checkName(str1);
            x = checkInt(str2);
            y = checkDouble(str3);
            studentsCount = checkIntWithNull(str4);
            per_name = checkName(str7);
            height = checkIntWithNull(str8);
            loc_x = checkDouble(str11);
            loc_y = checkInt(str12);
            loc_z = checkInt(str13);
        } catch (NullPointerException e) {
            return null;
        }

        try {
            if (str5.equals("")) {
                formOfEducation = null;
            } else {
                formOfEducation = FormOfEducation.valueOf(str5);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            if (str6.equals("")) {
                semesterEnum = null;
            } else {
                semesterEnum = Semester.valueOf(str6);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            if (str9.equals("")) {
                hairColor = null;
            } else {
                hairColor = Color.valueOf(str9);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        try {
            if (str10.equals("")) {
                return null;
            } else {
                nationality = Country.valueOf(str10);
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        long id = 0;
        studyGroup = new StudyGroup(id, name, new Coordinates(x, y), studentsCount, formOfEducation, semesterEnum,
                new Person(per_name, height, hairColor, nationality, new Location(loc_x, loc_y, loc_z)));
        return studyGroup;
    }
}