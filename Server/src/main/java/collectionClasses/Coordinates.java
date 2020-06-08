package collectionClasses;

import java.io.Serializable;

public class Coordinates implements Serializable {
    public Integer x; //Поле не может быть null
    private double y;

    public Coordinates(Integer x, double y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return this.x;
    }
}