package com.aksesi.generator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.util.List;

/**
 * Created by Mateusz Brycki on 27/05/2017.
 */
public class ChartFrame extends ApplicationFrame {
    public ChartFrame(String applicationTitle, String chartTitle, List<Point> points) {
        super(applicationTitle);

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "X", "Y",
                createDataset(points),
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = lineChart.getXYPlot();

        XYDotRenderer renderer = new XYDotRenderer();
        renderer.setDotHeight(3);
        renderer.setDotWidth(3);

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 560));

        setContentPane(chartPanel);

    }

    private XYDataset createDataset(List<Point> points) {

        XYSeries series = new XYSeries("x");

        for (Point point : points) {
            series.add(point.getX().doubleValue(), point.getY().doubleValue());
        }

        XYDataset dataset = new XYSeriesCollection(series);
        return dataset;
    }

}