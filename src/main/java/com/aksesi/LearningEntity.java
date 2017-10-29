package com.aksesi;

/**
 * Created by Mateusz Brycki on 15/08/2017.
 */

import com.aksesi.generator.Point;

import java.util.List;

public class LearningEntity {

    private Integer id;
    private String gestureName;
    private List<Point> gesture;


    public LearningEntity(Integer gestureNumber, String gestureName, List<Point> gesture) {
        this.id = gestureNumber;
        this.gestureName = gestureName;
        this.gesture = gesture;

    }

    public Integer getId() {
        return id;
    }

    public List<Point> getGesture() {
        return gesture;
    }

    public String getName() {
        return gestureName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.gestureName)
                .append("\n");

        gesture.forEach(e -> builder.append(e.getX() + " " + e.getY() + "\n"));

        return builder.toString();
    }
}
