package com.aksesi.batch;

import com.aksesi.IGestureNormalizer;
import com.aksesi.LearningEntity;
import com.aksesi.aligner.CentricPointsAligner;
import com.aksesi.generator.IShapeGenerator;
import com.aksesi.network.TrainingService;
import com.aksesi.normalizer.GestureNormalizer;
import com.aksesi.resizer.RepeatingResizer;
import com.aksesi.supplier.FlattenPointsSupplier;

import java.util.List;
import java.util.Map;

/**
 * Created by Mateusz Brycki on 15/08/2017.
 */
public class BatchProcessor {

    private final IGestureNormalizer gestureNormalizer = new GestureNormalizer(
            new CentricPointsAligner(),
            new FlattenPointsSupplier(),
            new RepeatingResizer()
    );

    private Map<Integer, IShapeGenerator> generators;

    public BatchProcessor(Map<Integer, IShapeGenerator> generators, TrainingService trainingService) {
        this.generators = generators;
    }

    private double[][] prepareLabels(List<LearningEntity> gestures) {
        double labels[][] = new double[gestures.size()][];
        for (int i = 0; i < gestures.size(); i++) {
            LearningEntity learningEntity = gestures.get(i);

            double label[] = new double[generators.size()];

            for (int x = 0; x < label.length; x++) {
                if (learningEntity.getId() != x) {
                    label[x] = 0;
                } else {
                    label[x] = 1;
                }
            }

            labels[i] = label;
        }

        return labels;
    }

    private double[][] normalizeGestures(List<LearningEntity> gestures) {
        double normalizedGestures[][] = new double[gestures.size()][];

        for (int i = 0; i < gestures.size(); i++) {

            LearningEntity learningEntity = gestures.get(i);
            double[] gesture = this.gestureNormalizer.normalize(learningEntity.getGesture());

            normalizedGestures[i] = gesture;
        }

        return normalizedGestures;
    }

    public static class ProcessorResult {

    }
}
