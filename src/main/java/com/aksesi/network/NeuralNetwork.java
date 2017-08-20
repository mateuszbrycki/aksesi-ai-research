package com.aksesi.network;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;

/**
 * Created by Mateusz Brycki on 01/07/2017.
 */
public class NeuralNetwork implements INeuralNetwork {

    private final BasicNetwork network;

    public NeuralNetwork() {
        network = new BasicNetwork();

        network.addLayer(new BasicLayer(new ActivationSigmoid(),true, 400)); //input layer
//        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 200));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 100));
        network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 5)); //output layer
        network.getStructure().finalizeStructure();
        network.reset();
    }

    public BasicNetwork getNetwork() {
        return network;
    }
}
