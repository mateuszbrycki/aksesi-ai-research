package com.aksesi;

import com.aksesi.generator.Point;

import java.util.List;

/**
 * Created by Mateusz Brycki on 26/05/2017.
 */
public interface IGestureNormalizer {

    double[] normalize(List<Point> pointsList);
}
