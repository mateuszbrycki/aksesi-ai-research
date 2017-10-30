import com.aksesi.LearningEntity;
import com.aksesi.batch.BatchCreator;
import com.aksesi.generator.Configuration;
import com.aksesi.generator.GeneratorsProvider;
import com.aksesi.generator.IShapeGenerator;
import com.aksesi.storage.GestureFileStorage;
import org.apache.log4j.Logger;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Initialization initialization = new Main.Initialization();
        initialization.start();
    }

    public static class Initialization {
        private final Configuration configuration = new Configuration(400, 5, 3, 1000, -1000);

        private BatchCreator creator;
        private GestureFileStorage storage = new GestureFileStorage();

        private List<IShapeGenerator> generators;

        private final Integer NUMBER_OF_GESTURES = 50_000;

        Logger logger = Logger.getLogger(Main.Initialization.class);

        public Initialization() {
            GeneratorsProvider generatorsProvider = new GeneratorsProvider(configuration);
            generators = generatorsProvider.getGenerators();

            this.creator = new BatchCreator(configuration, generators);
        }

        private void saveGesture(LearningEntity entity) {
            storage.save(entity);
        }

        public void start() {
            List<LearningEntity> gestures = creator.create(NUMBER_OF_GESTURES);

            gestures.forEach(this::saveGesture);
        }
    }
}