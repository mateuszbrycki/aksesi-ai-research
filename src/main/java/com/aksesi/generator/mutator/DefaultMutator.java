package com.aksesi.generator.mutator;

import com.aksesi.generator.Configuration;
import com.aksesi.generator.IMutator;
import com.aksesi.generator.Point;

import java.util.Random;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class DefaultMutator implements IMutator {

    private Configuration configuration;
    private Random random = new Random();

    public DefaultMutator(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Point mutate(Point idealPoint) {

        Integer max = configuration.getMaximumNeighbourDeviation(),
                min = max * -1;

        //random delta value
        Float x = random.nextFloat() * (max - min) + min,
              y = random.nextFloat() * (max - min) + min;

        //decide in which side point should be moved
        x = (random.nextInt(2) % 2 == 0) ? idealPoint.getX() + x : idealPoint.getX() - x;
        y = (random.nextInt(2) % 2 == 0) ? idealPoint.getY() + y : idealPoint.getY() - y;

        return new Point(x, y);
    }
}
