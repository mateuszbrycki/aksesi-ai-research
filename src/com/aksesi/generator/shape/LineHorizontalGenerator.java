package com.aksesi.generator.shape;

import com.aksesi.generator.Configuration;
import com.aksesi.generator.Point;

import java.util.Random;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class LineHorizontalGenerator extends AbstractLineGenerator {

    public LineHorizontalGenerator(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Point getPoint(Integer i) {

        Float x, y = startPoint.getY();
        Float delta = (endPoint.getX() - startPoint.getX()) / (configuration.getNumberOfPoints() - 1);

        x = startPoint.getX() + (i - 1) * delta;

        return new Point(x, y);
    }

    protected Point createRandomPoint() {

        if(startPoint == null) {
            return super.createRandomPoint();
        }

        Random rand = new Random();

        Integer max = configuration.getMaximumCoordinateValue(),
                min = configuration.getMinimumCoordinateValue();

        Float x = rand.nextFloat() * (max - min) + min;
        Float y = startPoint.getY();

        return new Point(x, y);
    }
}
