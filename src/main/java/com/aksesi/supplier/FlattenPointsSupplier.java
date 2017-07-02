package com.aksesi.supplier;

import com.aksesi.IFormattedInputSupplier;
import com.aksesi.generator.Point;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Brycki on 14/05/2017.
 */
public class FlattenPointsSupplier implements IFormattedInputSupplier {

    public List<Double> apply(List<Point> points) {
        List<Double> result = new ArrayList<>();

        points.forEach(p ->  {

            BigDecimal value = new BigDecimal(p.getX());
            result.add(value.doubleValue());

            value = new BigDecimal(p.getY());
            result.add(value.doubleValue());
        });

        return result;
    }
}
