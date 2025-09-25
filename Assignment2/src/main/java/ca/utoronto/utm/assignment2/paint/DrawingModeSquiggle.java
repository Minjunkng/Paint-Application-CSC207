package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModeSquiggle implements DrawingMode{
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
        Squiggle sq = new Squiggle(new Point(mouseEvent.getX(), mouseEvent.getY()), model.getCurrentColor(), model.getFillType(), "Squiggle");
        s.setDrawable(sq);
        if (ThicknessHandler.thicknessValue > 0) {
            s.getDrawable().setThickness(ThicknessHandler.thicknessValue);
        }
        model.add(sq);
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        if (s.getDrawable() instanceof Squiggle sq) {
            sq.addPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));
        }
        model.modify();
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}
}
