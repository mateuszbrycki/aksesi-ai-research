package com.aksesi;

import com.aksesi.generator.Point;

import java.util.List;

/**
 * Created by Mateusz Brycki on 13/05/2017.
 */
public interface IGesturePointsAligner {
    List<Point> align(List<Point> points);
}
