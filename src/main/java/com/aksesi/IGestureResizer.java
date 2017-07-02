package com.aksesi;

import com.aksesi.generator.Point;

import java.util.List;

/**
 * Created by Mateusz Brycki on 14/05/2017.
 */
public interface IGestureResizer {

    List<Point> resize(List<Point> points);
}
