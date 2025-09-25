package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class Action {

    public void cut(ArrayList<Drawable> canvas, ArrayList<Drawable> drawables, ArrayList<Integer> indexes, String undoRedo) {
        if (undoRedo.equals("Undo")) {
            canvas.add(indexes.getFirst(), drawables.getFirst());
        } else {
            canvas.remove(drawables.getFirst());
        }
    }

    public void place(ArrayList<Drawable> canvas, ArrayList<Drawable> drawables, ArrayList<Integer> indexes, String undoRedo) {
        if (undoRedo.equals("Undo")) {
            canvas.remove(drawables.getFirst());
        } else {
            canvas.add(indexes.getFirst(), drawables.getFirst());
        }
    }

    public void clear(ArrayList<Drawable> canvas, ArrayList<Drawable> drawables, ArrayList<Integer> indexes, String undoRedo) {
        if (undoRedo.equals("Undo")) {
            for (int i = 0; i < drawables.size(); i++) {
                canvas.add(indexes.get(i), drawables.get(i));
            }
        } else {
            for (Drawable drawable : drawables) {
                canvas.remove(drawable);
            }
        }
    }

    public void select(ArrayList<Drawable> drawables, String undoRedo) {
        if (undoRedo.equals("Undo")) {
            Drawable pointHolder = drawables.getFirst();
            Drawable drawable = drawables.getLast();
            Point relocate = new Point(pointHolder.getPoint().x, pointHolder.getPoint().y);
            Point stored = new Point(drawable.getPoint().x, drawable.getPoint().y);
            pointHolder.setPoint(stored);
            drawable.setPoint(relocate);
        } else {
            Drawable pointHolder = drawables.getFirst();
            Drawable drawable = drawables.getLast();
            Point relocate = new Point(pointHolder.getPoint().x, pointHolder.getPoint().y);
            Point stored = new Point(drawable.getPoint().x, drawable.getPoint().y);
            pointHolder.setPoint(stored);
            drawable.setPoint(relocate);
        }
    }
}
