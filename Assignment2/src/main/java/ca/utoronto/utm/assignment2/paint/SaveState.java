package ca.utoronto.utm.assignment2.paint;

import java.io.File;
import java.util.ArrayList;

public class SaveState {
    public static File SAVEDCANVAS = new File("SavedCanvas.ser");
    private ArrayList<SaveItem> canvases;

    public SaveState() {
        this.canvases = new ArrayList<>();
        this.loadSave();
    }

    public void addSave(SaveItem item) {
        this.canvases.add(item);
        SaveFile.saveItem(SAVEDCANVAS, this.canvases);
    }

    public ArrayList<Drawable> getSave(String s) {
        for (SaveItem item : canvases) {
            if (item.getName().equals(s)) {
                return item.getDrawables();
            }
        }
        return null;
    }

    public ArrayList<String> getListSaves() {
        ArrayList<String> list = new ArrayList<>();
        for (SaveItem canvases : canvases) {
            list.add(canvases.getName());
        }
        return list;
    }

    public void loadSave() {
        ArrayList<Object> loadedObjects = LoadFile.loadAllSaves(SAVEDCANVAS);
        for (Object obj : loadedObjects) {
            if (obj instanceof SaveItem item) {
                canvases.add(item);
            }
        }
    }
}
