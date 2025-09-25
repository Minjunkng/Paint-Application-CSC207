package ca.utoronto.utm.assignment2.paint;

import java.io.*;
import java.util.ArrayList;

public class SaveFile {

    public static void saveItem(File filename, ArrayList<SaveItem> canvas) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(canvas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
