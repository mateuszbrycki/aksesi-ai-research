package com.aksesi;

/**
 * Created by Mateusz Brycki on 15/08/2017.
 */

import com.aksesi.generator.Point;

import java.util.List;

public class LearningEntity {

    private Integer gestureNumber;
    private List<Point> gesture;


    public LearningEntity(Integer gestureNumber, List<Point> gesture) {
        this.gestureNumber = gestureNumber;
        this.gesture = gesture;

    }

    public Integer getGestureNumber() {
        return gestureNumber;
    }

    public List<Point> getGesture() {
        return gesture;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        gesture.forEach(e -> builder.append(e.getX() + " " + e.getY() + "\n"));

        return builder.toString();
    }
}
