package com.aksesi.generator.shape;

import com.aksesi.generator.Configuration;
import com.aksesi.generator.IShapeGenerator;
import com.aksesi.generator.Point;

import java.util.Random;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public abstract class AbstractLineGenerator implements IShapeGenerator {

    protected Configuration configuration;

    protected Point startPoint;
    protected Point endPoint;

    private final String createdShape;

    public AbstractLineGenerator(Configuration configuration, String createdShape) {
        this.configuration = configuration;

        this.startPoint = createRandomPoint();
        this.endPoint = createRandomPoint();
        this.createdShape = createdShape;
    }

    protected Point createRandomPoint() {

        Random rand = new Random();

        Integer max = configuration.getMaximumCoordinateValue(),
                min = configuration.getMinimumCoordinateValue();

        Float x = rand.nextFloat() * (max - min) + min;
        Float y = rand.nextFloat() * (max - min) + min;

        return new Point(x, y);
    }

    public String getCreatedShape() {
        return createdShape;
    }
}
