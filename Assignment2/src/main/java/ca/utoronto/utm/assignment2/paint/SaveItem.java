package ca.utoronto.utm.assignment2.paint;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveItem implements Serializable {
    private final String name;
    private final ArrayList<Drawable> drawables = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    public SaveItem(String name, ArrayList<Object> drawables) {
        this.name = name;
        for (Object drawable : drawables) {
            if (drawable instanceof Drawable) {
                this.drawables.add((Drawable) drawable);
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Drawable> getDrawables() {
        return drawables;
    }
}
