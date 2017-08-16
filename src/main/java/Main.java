import com.aksesi.LearningEntity;
import com.aksesi.batch.BatchCreator;
import com.aksesi.batch.BatchProcessor;
import com.aksesi.generator.Configuration;
import com.aksesi.generator.GeneratorsProvider;
import com.aksesi.generator.IShapeGenerator;
import com.aksesi.network.TrainingService;
import com.aksesi.storage.GestureFileStorage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        Initialization initialization = new Main.Initialization();
        initialization.start();
    }

    public static class Initialization {
        private final Configuration configuration = new Configuration(200, 3, 1, 100, -100);

        private BatchCreator creator;
        private BatchProcessor processor;
        private TrainingService trainingService = new TrainingService();
        private GestureFileStorage storage = new GestureFileStorage();

        private Map<Integer, IShapeGenerator> generators;

        private final Integer NUMBER_OF_BATCHES = 600;
        private final Integer NUMBER_OF_GESTURES_PER_BATCH = 1000;

        Logger logger = Logger.getLogger(Main.Initialization.class);

        public Initialization() {
            GeneratorsProvider generatorsProvider = new GeneratorsProvider(configuration);
            generators = generatorsProvider.getGenerators();

            this.creator = new BatchCreator(configuration, generators);
            this.processor = new BatchProcessor(generators, trainingService);
        }

        IntConsumer processBatch = (batchNumber) -> {
            logger.info("Processing batch number " + batchNumber);
            final Integer numberOfGestures = NUMBER_OF_GESTURES_PER_BATCH / generators.size();

            List<LearningEntity> gestures = creator.create(numberOfGestures);
            List<LearningEntity> trainingSet = gestures.subList(0, (int)(gestures.size() * 0.8));

            List<LearningEntity> testingSet = new ArrayList<>(gestures);
            testingSet.removeAll(trainingSet);

            train(trainingSet);
            savePoints(trainingSet);
            test(testingSet, batchNumber);
        };

        private void savePoints(List<LearningEntity> set) {

            set.forEach(storage::save);

        }

        private void train(List<LearningEntity> trainingSet) {

            logger.info("Training the network with " + trainingSet.size() + " elements");

            BatchProcessor.ProcessorResult result = processor.process(trainingSet);
            trainingService.train(result.gestures, result.labels);
        }

        private void test(List<LearningEntity> testingSet, Integer batchNumber) {

            logger.info("Testing the network with " + testingSet.size() + " for bath number " + batchNumber);

            BatchProcessor.ProcessorResult result = processor.process(testingSet);
            Double error = trainingService.test(result.gestures, result.labels);

            logger.error("Error for batch " + batchNumber + " is equal " + error);
        }

        public void start() {
            IntStream.range(0, NUMBER_OF_BATCHES)
                    .forEach(processBatch);

        }
    }
}