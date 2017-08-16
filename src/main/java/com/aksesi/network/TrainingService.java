package com.aksesi.network;

import org.apache.log4j.Logger;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

import java.util.stream.IntStream;

/**
 * Created by Mateusz Brycki on 01/07/2017.
 */
public class TrainingService {

    private static final Integer EPOCH_COUNT = 50;
    private INeuralNetwork neuralNetwork = new NeuralNetwork();

    private Logger log = Logger.getLogger(TrainingService.class);

    public TrainingService() {
    }

    public void train(double[][] gestures, double[][] responses) {

        log.info("Training the neural network with " + gestures.length + " gestures");
        log.info("The number of epoch " + EPOCH_COUNT);

        MLDataSet trainingSet = new BasicMLDataSet(gestures, responses);
        Backpropagation backpropagation = new Backpropagation(neuralNetwork.getNetwork(), trainingSet, 0.5, 0.3);

        IntStream.range(0, EPOCH_COUNT)
                .forEach((indx) -> backpropagation.iteration());

        backpropagation.finishTraining();

        log.info("Training finished");
    }

    public double test(double[][] gestures, double[][] responses) {
        MLDataSet testingSet = new BasicMLDataSet(gestures, responses);
        return neuralNetwork.getNetwork().calculateError(testingSet);
    }
}