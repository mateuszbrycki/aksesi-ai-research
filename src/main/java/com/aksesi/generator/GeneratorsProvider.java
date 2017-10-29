package com.aksesi.generator;

import com.aksesi.generator.shape.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class GeneratorsProvider {

    private Configuration configuration;

    private List<IShapeGenerator> generators = new ArrayList<>();


    public GeneratorsProvider(Configuration configuration) {
        this.configuration = configuration;

        generators.add(new LineVerticalGenerator(configuration));
        generators.add(new LineHorizontalGenerator(configuration));
        generators.add(new LineDiagonalLeftGenerator(configuration));
        generators.add(new LineDiagonalRightGenerator(configuration));
        generators.add(new CircleGenerator(configuration));
    }

    public List<IShapeGenerator> getGenerators() {
        return this.generators;
    }
}


