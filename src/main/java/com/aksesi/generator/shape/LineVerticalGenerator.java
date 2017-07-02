package com.aksesi.generator.shape;

import com.aksesi.generator.Configuration;
import com.aksesi.generator.Point;

import java.util.Random;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class LineVerticalGenerator extends AbstractLineGenerator {

    public LineVerticalGenerator(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Point getPoint(Integer i) {
        Float x = startPoint.getX(), y;
        Float delta = (endPoint.getY() - startPoint.getY()) / (configuration.getNumberOfPoints() - 1);

        y = startPoint.getY() + (i - 1) * delta;

        return new Point(x, y);
    }

    protected Point createRandomPoint() {

        if(startPoint == null) {
            return super.createRandomPoint();
        }

        Random rand = new Random();

        Integer max = configuration.getMaximumCoordinateValue(),
                min = configuration.getMinimumCoordinateValue();

        Float x = startPoint.getX();
        Float y = rand.nextFloat() * (max - min) + min;

        return new Point(x, y);
    }
}
