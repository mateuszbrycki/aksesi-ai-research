package com.aksesi.generator.shape;

import com.aksesi.generator.Configuration;
import com.aksesi.generator.Point;

import java.util.Random;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class LineDiagonalLeftGenerator extends AbstractLineGenerator {

    public LineDiagonalLeftGenerator(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Point getPoint(Integer i) {

        Float deltaX = (endPoint.getX() - startPoint.getX()) / (configuration.getNumberOfPoints() - 1);
        Float deltaY = (startPoint.getY() - endPoint.getY()) / (configuration.getNumberOfPoints() - 1);

        Float x = startPoint.getX() + (i - 1) * deltaX,
                y = startPoint.getY() - (i - 1) * deltaY;

        return new Point(x, y);
    }

    @Override
    protected Point createRandomPoint() {

        if(startPoint == null) {
           return super.createRandomPoint();
        }

        Random rand = new Random();

        Integer max = configuration.getMaximumCoordinateValue(),
                min = configuration.getMinimumCoordinateValue();

        Float x = startPoint.getX() ,
                y = startPoint.getY();

        while(x <= startPoint.getX() || y >= startPoint.getY()) {
            x = rand.nextFloat() * (max - min) + min;
            y = rand.nextFloat() * (max - min) + min;
        }

        return new Point(x, y);
    }
}
