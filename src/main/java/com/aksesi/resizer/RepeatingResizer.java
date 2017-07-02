package com.aksesi.resizer;

import com.aksesi.IGestureResizer;
import com.aksesi.generator.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Mateusz Brycki on 14/05/2017.
 */
public class RepeatingResizer implements IGestureResizer {

    private static Integer GESTURE_SIZE = 200;

    @Override
    public List<Point> resize(List<Point> points) {

        if (points.size() < 2) {
            return points;
        }

        return performResizing(points);
    }

    private List<Point> performResizing(List<Point> points) {
        if (points.size() < GESTURE_SIZE) {
            return extend(points);
        }

        return shorten(points);
    }

    private List<Point> extend(List<Point> points) {

        int gesturePointsAmount = points.size();

        Integer pointsToAdd = GESTURE_SIZE - gesturePointsAmount;
        Integer availableRanges = gesturePointsAmount - 1;

        Float pointsPerRange = pointsToAdd.floatValue() / availableRanges.floatValue();

        List<Point> result = new ArrayList<>();

        for (int i = 0; i <= availableRanges; i++) {

            //get range bounds
            Point rangeStart = points.get(i),
                    rangeEnd;

            if (points.size() > i + 1) {
                rangeEnd = points.get(i + 1);
            } else {
                // it is last iteration then add the last element
                result.add(rangeStart);
                break;
            }

            /*
                IF:
                    1. the half of points is provided
                OR
                    2. it is the first iteration
                THEN the lower number of points (Math.ceil()) to generate is used
             */
            Boolean isHalfOfPointsProvided = (GESTURE_SIZE) / 2 == gesturePointsAmount;
            Boolean isItFirsIteration = i == 0;

            Integer pointsToGenerate = (int) ((isItFirsIteration || isHalfOfPointsProvided) ? Math.floor(pointsPerRange) : Math.ceil(pointsPerRange)) + 1;

            Boolean recountPointsToGenerate = (i == availableRanges - 1);
            if(recountPointsToGenerate) {
                // calculate number of missing points
                pointsToGenerate = GESTURE_SIZE - result.size() - 1;
            }

            // generate points for range
            List<Point> generatedPoints = generatePointsForRange(rangeStart, rangeEnd, pointsToGenerate);

            result.add(rangeStart);
            result.addAll(generatedPoints);
        }


        return result;
    }

    private List<Point> generatePointsForRange(Point rangeStart, Point rangeEnd, Integer pointsPerRange) {

        Float deltaX = (rangeEnd.getX() - rangeStart.getX()) / pointsPerRange,
                deltaY = (rangeEnd.getY() - rangeStart.getY()) / pointsPerRange;

        Function<Integer, Point> countPointCoordinates = (index) -> {
            Float x = rangeStart.getX() + deltaX * index;
            Float y = rangeStart.getY() + deltaY * index;
            return new Point(x, y);
        };

        return IntStream.range(1, pointsPerRange)
                .boxed()
                .map(countPointCoordinates)
                .collect(Collectors.toList());
    }

    private List<Point> shorten(List<Point> points) {

        points = removeDuplicates(points);

        if (points.size() > GESTURE_SIZE) {
            points = removeNeighboringPoints(points);
        }

        return points;
    }

    private List<Point> removeNeighboringPoints(List<Point> points) {

        Iterator<Point> iterator = points.iterator();

        Point previous = null;
        while (iterator.hasNext() && (points.size()) > GESTURE_SIZE) {

            if (previous == null) {
                previous = iterator.next();
                continue;
            }

            Point current = iterator.next();

            Float horizontalDifference = Math.abs(current.getX() - previous.getX());
            Float verticalDifference = Math.abs(current.getY() - previous.getY());

            if (horizontalDifference < 2 && verticalDifference < 2) {
                iterator.remove();
                continue;
            }

            previous = current;
        }

        return points;
    }

    private List<Point> removeDuplicates(List<Point> points) {

        Iterator<Point> iterator = points.iterator();

        Point previous = null;
        while (iterator.hasNext() && (points.size()) > GESTURE_SIZE) {

            if (previous == null) {
                previous = iterator.next();
                continue;
            }
            Point current = iterator.next();

            if (current.equals(previous)) {
                iterator.remove();
                continue;
            }

            previous = current;
        }


        return points;
    }

}
