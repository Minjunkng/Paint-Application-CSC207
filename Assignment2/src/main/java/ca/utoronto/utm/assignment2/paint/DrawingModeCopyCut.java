package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DrawingModeCopyCut implements DrawingMode {
    static Drawable storedShape;
    private String cutOrCopy;
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) { behave(mouseEvent, s, model);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}

    public static Drawable getStoredShape() {
        return storedShape.getCopy();
    }

    private void behave(MouseEvent mouseEvent, DrawableState s, PaintModel model){
        ArrayList<Drawable> drawings = model.getDrawables();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        for (int i = drawings.size() - 1; i >= 0; i--) {
            Drawable d = drawings.get(i);
            if (d.contains(x, y)) {
                if (Objects.equals(cutOrCopy, "Cut")) {
                    model.addAction("Cut", new ArrayList<>(Arrays.asList(model.remove(d))), new ArrayList<>(Arrays.asList(d)));
                }
                storedShape = d;
                System.out.println(d);
                model.modify();
                break;
            }
        }
    }

    public DrawingModeCopyCut(String cutOrCopy) {
        this.cutOrCopy = cutOrCopy;
    }
}
