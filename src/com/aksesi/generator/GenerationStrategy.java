package com.aksesi.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class GenerationStrategy {

    protected IMutator mutator;
    protected Configuration configuration;

    public GenerationStrategy(IMutator mutator, Configuration configuration) {
        this.mutator = mutator;
        this.configuration = configuration;
    }

    public List<Point> generate(IShapeGenerator generator) {

        List<Point> points = new ArrayList<>();

        for (int i = 0; i < configuration.getNumberOfPoints(); i++) {
            Point idealPoint = generator.getPoint(i);
            points.add(mutator.mutate(idealPoint));
        }

        return points;
    }
}
