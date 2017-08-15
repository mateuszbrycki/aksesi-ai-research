import com.aksesi.IGestureNormalizer;
import com.aksesi.aligner.CentricPointsAligner;
import com.aksesi.generator.*;
import com.aksesi.generator.mutator.DefaultMutator;
import com.aksesi.network.NeuralNetwork;
import com.aksesi.network.TrainingService;
import com.aksesi.normalizer.GestureNormalizer;
import com.aksesi.resizer.RepeatingResizer;
import com.aksesi.supplier.FlattenPointsSupplier;
import org.apache.log4j.Logger;
import org.encog.mathutil.Equilateral;

import java.util.ArrayList;
import java.util.Collections;
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
        private final NeuralNetwork network = new NeuralNetwork();

        private final IGestureNormalizer gestureNormalizer = new GestureNormalizer(
                new CentricPointsAligner(),
                new FlattenPointsSupplier(),
                new RepeatingResizer()
        );

        private TrainingService trainingService;

        private Map<Integer, IShapeGenerator> generators;

        private final Integer NUMBER_OF_BATCHES = 600;
        private final Integer NUMBER_OF_GESTURES_PER_BATCH = 1000;

        Logger logger = Logger.getLogger(Main.Initialization.class);

        public Initialization() {
            GeneratorsProvider generatorsProvider = new GeneratorsProvider(configuration);
            generators = generatorsProvider.getGenerators();

            this.trainingService = new TrainingService(network);
        }

        IntConsumer processBatch = (batchNumber) -> {
            logger.info("Processing batch number " + batchNumber);
            final Integer numberOfGestures = NUMBER_OF_GESTURES_PER_BATCH / generators.size();

            List<LearningEntity> gestures = generateGesturesBatch(numberOfGestures);
            learnNetworkUsingBath(gestures);

            testNetwork(batchNumber);
        };

        private void learnNetworkUsingBath(List<LearningEntity> gestures) {

            double normalizedGestures[][] = new double[gestures.size()][];
            double labels[][] = new double[gestures.size()][];

            Equilateral eq = new Equilateral(generators.size(), -1, 1);
            for(int i = 0; i < gestures.size(); i++) {

                LearningEntity learningEntity = gestures.get(i);
                double[] gesture = this.gestureNormalizer.normalize(learningEntity.getGesture());

                normalizedGestures[i] = gesture;
                double label[] = new double[generators.size()];

                for(int x = 0; x < label.length; x++) {
                    if(learningEntity.getGestureNumber() != x) {
                        label[x] = 0;
                    } else {
                        label[x] = 1;
                    }
                }

                labels[i] = label;
            }

            logger.info("Learning the network with " + normalizedGestures.length + " gestures");

            trainingService.train(normalizedGestures, labels);
        }

        private List<LearningEntity> generateGesturesBatch(Integer numberOfGestures) {

            logger.info("Generating gestures for a batch.");
            List<LearningEntity> gestures = new ArrayList<>();

            generators.entrySet()
                    .forEach(e -> {
                        IShapeGenerator generator = e.getValue();
                        GenerationStrategy strategy = new GenerationStrategy(
                                new DefaultMutator(configuration), configuration);

                        IntStream.range(1, numberOfGestures)
                                .forEach(gestureNumber -> {
                                    List<Point> gesture = strategy.generate(generator);

                                    gestures.add(new LearningEntity(e.getKey(), gesture));
                                });
                    });

            Collections.shuffle(gestures);
            return gestures;
        }

        private void testNetwork(Integer batchNumber) {
            logger.info("Testing the network after learning with a batch number " + batchNumber);
        }

        public void start() {

            IntStream.range(0, NUMBER_OF_BATCHES)
                    .forEach(processBatch);

        }
    }
}

class LearningEntity {

    private Integer gestureNumber;
    private List<Point> gesture;


    public LearningEntity(Integer gestureNumber, List<Point> gesture) {
        this.gestureNumber = gestureNumber;
        this.gesture = gesture;

    }

    public Integer getGestureNumber() {
        return gestureNumber;
    }

    public List<Point> getGesture() {
        return gesture;
    }
}
