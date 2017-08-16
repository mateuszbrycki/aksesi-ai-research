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

    private File getDirectory(LearningEntity entity) {
        File file = new File("/Users/mateusz-mac/Projects/aksesi-ai-research/" + entity.getGestureNumber());

        if(!file.exists()) {
            file.mkdirs();
        }

        return file;
    }
}
