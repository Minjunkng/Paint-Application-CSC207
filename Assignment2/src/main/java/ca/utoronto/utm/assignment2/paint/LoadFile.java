package ca.utoronto.utm.assignment2.paint;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class LoadFile {
    public static ArrayList<Object> loadAllSaves(File filename) {
        ArrayList<Object> objects = new ArrayList<>();
        if (filename.length() != 0) {
            try (FileInputStream fileIn = new FileInputStream(filename);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                while (true) {
                    try {
                        Object obj = in.readObject();
                        objects.addAll((Collection<?>) obj);
                    } catch (EOFException e) {
                        break;
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }
}
