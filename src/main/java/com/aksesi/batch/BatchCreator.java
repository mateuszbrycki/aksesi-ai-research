package com.aksesi.batch;

import com.aksesi.LearningEntity;
import com.aksesi.generator.Configuration;
import com.aksesi.generator.GenerationStrategy;
import com.aksesi.generator.IShapeGenerator;
import com.aksesi.generator.Point;
import com.aksesi.generator.mutator.DefaultMutator;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by Mateusz Brycki on 15/08/2017.
 */
public class BatchCreator {

    private Map<Integer, IShapeGenerator> generators;
    private final Configuration configuration;

    Logger logger = Logger.getLogger(BatchCreator.class);

    public BatchCreator(Configuration configuration, Map<Integer, IShapeGenerator> generators) {
        this.generators = generators;
        this.configuration = configuration;
    }

    public List<LearningEntity> create(Integer numberOfGestures) {

        logger.info("Generating gestures for a batch.");
        List<LearningEntity> gestures = new ArrayList<>();

        generators.entrySet()
                .forEach(e -> {
                    IShapeGenerator generator = e.getValue();
                    GenerationStrategy strategy = new GenerationStrategy(
                            new DefaultMutator(configuration), configuration);

                    IntStream.range(0, numberOfGestures)
                            .forEach(gestureNumber -> {
                                List<Point> gesture = strategy.generate(generator);

                                gestures.add(new LearningEntity(e.getKey(), gesture));
                            });
                });

        Collections.shuffle(gestures);
        return gestures;
    }

}
