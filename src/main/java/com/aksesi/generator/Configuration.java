package com.aksesi.generator;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class Configuration {

    /**
     * Number of points
     */
    private Integer numberOfPoints;

    /**
     * Maximum neighbour deviation specifies the maximum difference
     * between points that are neighbours
     */
    private Integer maximumNeighbourDeviation;

    /**
     * Maximum series deviation specifies the maximum distance
     * from the ideal point's position
     * e.g.
     * ideal position (0,0)
     * maximum deviation = 10
     * point (9, 9) is valid
     * point (11, 11) is not -> distance is longer than maximum series deviation
     */
    private Integer maximumSeriesDeviation;

    private Integer maximumCoordinateValue;
    private Integer minimumCoordinateValue;

    public Configuration(Integer numberOfPoints,
                         Integer maximumNeighbourDeviation,
                         Integer maximumSeriesDeviation,
                         Integer maximumCoordinateValue,
                         Integer minimumCoordinateValue) {
        this.numberOfPoints = numberOfPoints;
        this.maximumNeighbourDeviation = maximumNeighbourDeviation;
        this.maximumSeriesDeviation = maximumSeriesDeviation;
        this.maximumCoordinateValue = maximumCoordinateValue;
        this.minimumCoordinateValue = minimumCoordinateValue;
    }

    public Integer getNumberOfPoints() {
        return numberOfPoints;
    }

    public Integer getMaximumNeighbourDeviation() {
        return maximumNeighbourDeviation;
    }

    public Integer getMaximumSeriesDeviation() {
        return maximumSeriesDeviation;
    }


    public Integer getMaximumCoordinateValue() {
        return maximumCoordinateValue;
    }

    public Integer getMinimumCoordinateValue() {
        return minimumCoordinateValue;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "numberOfPoints=" + numberOfPoints +
                ", maximumNeighbourDeviation=" + maximumNeighbourDeviation +
                ", maximumSeriesDeviation=" + maximumSeriesDeviation +
                ", maximumCoordinateValue=" + maximumCoordinateValue +
                ", minimumCoordinateValue=" + minimumCoordinateValue +
                '}';
    }
}
