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

    private List<IShapeGenerator> generators;
    private final Configuration configuration;

    private final Integer PRINT_INFO = 1_000;

    Logger logger = Logger.getLogger(BatchCreator.class);

    public BatchCreator(Configuration configuration, List<IShapeGenerator> generators) {
        this.generators = generators;
        this.configuration = configuration;
    }

    public List<LearningEntity> create(Integer numberOfGestures) {

        List<LearningEntity> gestures = new ArrayList<>();

        GenerationStrategy strategy = new GenerationStrategy(
                new DefaultMutator(configuration), configuration);

        IntStream.range(0, numberOfGestures)
                .forEach(gestureNumber -> {

                    if ((gestureNumber % PRINT_INFO)==0) {
                        logger.info("Generating " + gestureNumber);
                    }

                    IShapeGenerator generator = generators.get(gestureNumber % generators.size());

                    List<Point> gesture = strategy.generate(generator);
                    gestures.add(new LearningEntity(gestureNumber, generator.getCreatedShape(), gesture));
                });

        Collections.shuffle(gestures);
        return gestures;
    }

}
