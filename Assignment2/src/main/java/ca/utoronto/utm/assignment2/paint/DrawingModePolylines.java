package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModePolylines implements DrawingMode {
    private static boolean first_dot = true;
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        if (first_dot) {
            s.resetState();
            Polylines polylines = new Polylines(new Point(mouseEvent.getX(), mouseEvent.getY()), model.getCurrentColor(), model.getFillType(), "Polylines");
            s.setDrawable(polylines);
            if (ThicknessHandler.thicknessValue > 0) {
                s.getDrawable().setThickness(ThicknessHandler.thicknessValue);
            }
            model.add(polylines);
            first_dot = false;
        } else {
            if (mouseEvent.isPrimaryButtonDown()) {
                Polylines polylines = (Polylines) s.getDrawable();
                polylines.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            } else if (mouseEvent.isSecondaryButtonDown()) {
                model.modify();
                first_dot = true;
                s.resetState();
            }
        }
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        if (!first_dot) {
            Polylines polylines = (Polylines) s.getDrawable();
            polylines.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            Polylines preview = (Polylines) model.getDrawables().getLast();
            preview.modify();
            model.modify();
            polylines.removePoint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {

    }
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        if (!first_dot) {
            Polylines polylines = (Polylines) s.getDrawable();
            polylines.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
            Polylines preview = (Polylines) model.getDrawables().getLast();
            preview.modify();
            model.modify();
            polylines.removePoint();
        }
    }
    public void exit(DrawableState s, PaintModel model) {
        model.modify();
        s.resetState();
        first_dot = true;
    }

}
