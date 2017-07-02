package com.aksesi.generator;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Mateusz Brycki on 21/05/2017.
 */
public class FileSaver {

    public void save(List<Point> points, String filename) throws FileNotFoundException {

        try (PrintWriter printWriter = new PrintWriter(filename + ".csv")) {
//            printWriter.println("x;y");
            int i = 0;
            for (Point point : points) {

                String line = point.getX() + ";" + point.getY();
                line = line.replaceAll("\\.", ",");

                printWriter.println(line);
                i++;
            }
        }
    }
}
