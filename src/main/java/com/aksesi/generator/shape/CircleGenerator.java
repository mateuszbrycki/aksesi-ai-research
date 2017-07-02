package com.aksesi.generator.shape;

import com.aksesi.generator.Configuration;
import com.aksesi.generator.IShapeGenerator;
import com.aksesi.generator.Point;

import java.util.Random;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class CircleGenerator implements IShapeGenerator {

    private Configuration configuration;
    private Point circleCenter;
    private Float r;

    public CircleGenerator(Configuration configuration) {
        this.configuration = configuration;

        Random random = new Random();

        Integer max = configuration.getMaximumCoordinateValue(),
                min = configuration.getMinimumCoordinateValue();

        Float x = random.nextFloat() * (max - min) + min,
                y = random.nextFloat() * (max - min) + min;
        this.circleCenter = new Point(x, y);

        this.r = random.nextFloat() * (max - min) + min;
    }


    @Override
    public Point getPoint(Integer i) {

        Float angleDelta = 360f / configuration.getNumberOfPoints();

        Double t = (double)angleDelta * i;

        Float x = getCircleCenter().getX() + (float)(getR() * Math.cos(t)),
                y = getCircleCenter().getY() + (float)(getR() * Math.sin(t));

        return new Point(x, y);
    }

    protected Float getR() {
        return this.r;
    }

    protected Point getCircleCenter() {
        return this.circleCenter;
    }
}
