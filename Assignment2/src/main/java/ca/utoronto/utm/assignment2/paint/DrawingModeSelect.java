package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class DrawingModeSelect implements DrawingMode {
    static Drawable draggedShape;
    static Point selectedPoint;
    static int selectedShapeIndex;
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) { behave(mouseEvent, s, model);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        if (draggedShape != null) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            draggedShape.setPoint(new Point(x,y));
            model.modify();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        Color c = new Color(0, 0, 0, 0);
        model.addAction("Select", new ArrayList<>(Arrays.asList(selectedShapeIndex, model.getDrawables().size())),
                new ArrayList<>(Arrays.asList(new Squiggle(selectedPoint, c, "", ""), draggedShape)));

    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}

    private void behave(MouseEvent mouseEvent, DrawableState s, PaintModel model){
        ArrayList<Drawable> drawings = model.getDrawables();
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        boolean deselect = true;
        for (int i = drawings.size() - 1; i >= 0; i--) {
            Drawable d = drawings.get(i);
            if (d.contains(x, y)) {
                draggedShape = d;
                selectedPoint = d.getPoint();
                selectedShapeIndex = i;
                deselect = false;
                break;
            }
        }
        if (deselect) {
            draggedShape = null;
        }
    }
}
