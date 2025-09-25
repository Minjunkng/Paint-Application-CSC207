package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModePaste implements DrawingMode {
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        behave(mouseEvent, s, model);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}

    private void behave(MouseEvent mouseEvent, DrawableState s, PaintModel model){
        try {
            Drawable copyCut = DrawingModeCopyCut.getStoredShape();
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            copyCut.setPoint(new Point(x, y));
            model.add(copyCut);
            model.modify();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
