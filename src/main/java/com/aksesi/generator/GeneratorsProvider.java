package com.aksesi.generator;

import com.aksesi.generator.shape.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class GeneratorsProvider {

    private Configuration configuration;

    private Map<Integer, IShapeGenerator> generators = new HashMap<>();


    public GeneratorsProvider(Configuration configuration) {
        this.configuration = configuration;

        generators.put(1, new LineVerticalGenerator(configuration));
        generators.put(2, new LineHorizontalGenerator(configuration));
        generators.put(3, new LineDiagonalLeftGenerator(configuration));
        generators.put(4, new LineDiagonalRightGenerator(configuration));
        generators.put(5, new CircleGenerator(configuration));
    }

    public Map<Integer, IShapeGenerator> getGenerators() {
        return this.generators;
    }


    /*public static void main(String[] args) {

        GenerationStrategy generationStrategy = new GenerationStrategy(new DefaultMutator(configuration), configuration);

        for (IShapeGenerator generator : generators) {

            List<Point> points = generationStrategy.generate(generator);
            System.out.println("Generated points " + points.size());
            try {
                fileSaver.save(points, generator.getClass().getName());
                System.out.println("Saved points to " + generator.getClass().getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ChartFrame chart = new ChartFrame("Gesture chart", generator.getClass().getCanonicalName(), points);
            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);
        }
    }*/

}


