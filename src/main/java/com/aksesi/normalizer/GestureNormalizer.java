package com.aksesi.normalizer;


import com.aksesi.IFormattedInputSupplier;
import com.aksesi.IGestureNormalizer;
import com.aksesi.IGesturePointsAligner;
import com.aksesi.IGestureResizer;
import com.aksesi.generator.Point;
import org.encog.util.arrayutil.NormalizeArray;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Mateusz Brycki on 26/05/2017.
 */
public class GestureNormalizer implements IGestureNormalizer {

    private IGesturePointsAligner pointsAligner;
    private IFormattedInputSupplier formattedInputSupplier;
    private IGestureResizer gestureResizer;

    public GestureNormalizer(IGesturePointsAligner pointsAligner,
                             IFormattedInputSupplier formattedInputSupplier,
                             IGestureResizer gestureResizer) {
        this.pointsAligner = pointsAligner;
        this.formattedInputSupplier = formattedInputSupplier;
        this.gestureResizer = gestureResizer;

    }

    public double[] normalize(List<Point> pointsList) {
        pointsList = pointsAligner.align(pointsList);
        pointsList = gestureResizer.resize(pointsList);

        return convertToArray(pointsList);

    }

    private double[] convertToArray(List<Point> points) {
        List<Double> listOfElements = formattedInputSupplier.apply(points);

        double[] arrayOfElements = new double[listOfElements.size()];

        IntStream.range(0, listOfElements.size())
                .forEach(idx -> arrayOfElements[idx] = listOfElements.get(idx));

        return normalizeArray(arrayOfElements);
    }

    private double[] normalizeArray(double[] arrayOfElements) {
        NormalizeArray normalizeArray = new NormalizeArray();
        normalizeArray.setNormalizedHigh(1);
        normalizeArray.setNormalizedLow(-1);
        return normalizeArray.process(arrayOfElements);
    }
}
