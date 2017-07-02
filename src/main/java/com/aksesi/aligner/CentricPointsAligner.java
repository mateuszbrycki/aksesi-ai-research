package com.aksesi.aligner;

import com.aksesi.IGesturePointsAligner;
import com.aksesi.generator.Point;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Mateusz Brycki on 13/05/2017.
 */
public class CentricPointsAligner implements IGesturePointsAligner {

    @Override
    public List<Point> align(List<Point> points) {

        if(points.size() < 1) {
            return Collections.emptyList();
        }

        Supplier<Stream<Point>> streamSupplier = () -> points.stream();

        Supplier<Stream<Float>> xStreamSupplier = () -> streamSupplier.get().map(Point::getX);
        Float maxX = xStreamSupplier.get().max(Float::compare).get();
        Float minX = xStreamSupplier.get().min(Float::compare).get();

        Supplier<Stream<Float>> yStreamSupplier = () -> streamSupplier.get().map(Point::getY);
        Float maxY = yStreamSupplier.get().max(Float::compare).get();
        Float minY = yStreamSupplier.get().min(Float::compare).get();

        //vector (x, y) values
        Float middleX = minX + (maxX - minX) / 2;
        Float middleY = minY + (maxY - minY) / 2;

        //build the result list -> move all of the points by [-middleX, -middleY] vector
        List<Point> result = streamSupplier.get()
                .map(p -> new Point(p.getX() - middleX, p.getY() - middleY))
                .collect(Collectors.toList());

        return result;
    }
}
