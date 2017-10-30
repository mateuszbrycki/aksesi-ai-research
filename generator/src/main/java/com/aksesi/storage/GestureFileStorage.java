package com.aksesi.storage;

import com.aksesi.LearningEntity;

import java.io.*;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Created by Mateusz Brycki on 15/08/2017.
 */
public class GestureFileStorage {

    private Integer startingNumber;
    private final String DIRECTORY_NAME = "data";
    private final String EXTENSION = ".txt";

    public GestureFileStorage() {
        startingNumber = getStartingNumber();
    }

    private Integer getStartingNumber() {
        File directory = getDirectory();
        return Stream.of(directory.listFiles())
                .mapToInt(e -> Integer.valueOf(e.getName().replace(".txt", "")))
                .max()
                .orElse(0);
    }

    public void save(LearningEntity entity) {

        File directory = getDirectory();
        File file = new File(preparePathForEntity(entity, directory));
        try {
            file.createNewFile();
            PrintStream out = new PrintStream(new FileOutputStream(file));
            out.print(entity.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String preparePathForEntity(LearningEntity entity, File directory) {
        return directory.getPath() + "/" + (startingNumber + entity.getId()) + EXTENSION;
    }

    private File getDirectory() {
        File file = new File(DIRECTORY_NAME + "");

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }
}
