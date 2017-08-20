package com.aksesi.storage;

import com.aksesi.LearningEntity;

import java.io.*;
import java.time.LocalDateTime;

/**
 * Created by Mateusz Brycki on 15/08/2017.
 */
public class GestureFileStorage {


    public void save(LearningEntity entity) {

        File directory = getDirectory(entity);
        File file = new File(directory.getPath() + "/" + LocalDateTime.now() + ".txt");
        try {
            file.createNewFile();
            PrintStream out = new PrintStream(new FileOutputStream(file));
            out.print(entity.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void save(final double[][] gestures) {

        File directory = getDirectory();
        for (int gestureNumber = 0; gestureNumber < gestures.length; gestureNumber++) {
            File file = new File(directory.getPath() + "/" + LocalDateTime.now() + ".txt");
            try {
                PrintStream out = new PrintStream(new FileOutputStream(file));

                for (int i = 0; i < gestures[gestureNumber].length - 2; i = i + 2) {
                    String result = gestures[gestureNumber][i] + " " + gestures[gestureNumber][i + 1] + "\r";
                    out.print(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getDirectory() {
        File file = new File("mixed/");

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    private File getDirectory(LearningEntity entity) {
        File file = new File(entity.getGestureNumber().toString());

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }
}
