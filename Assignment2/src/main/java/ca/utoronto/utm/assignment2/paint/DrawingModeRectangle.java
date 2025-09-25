package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModeRectangle implements DrawingMode {
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
        Point vertex = new Point(mouseEvent.getX(), mouseEvent.getY());
        Rectangle r = new Rectangle(vertex, 0, 0, model.getCurrentColor(), model.getFillType(), "Rectangle");
        s.setDrawable(new Rectangle(vertex, 0, 0, r.getColor(), model.getFillType(), "Rectangle"));
        if (ThicknessHandler.thicknessValue > 0) {
            r.setThickness(ThicknessHandler.thicknessValue);
        }
        model.add(r);
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        double dirX = mouseEvent.getX();
        double dirY = mouseEvent.getY();
        Rectangle r = (Rectangle)s.getDrawable();
        Point p = new Point(r.getVertex().x, r.getVertex().y);
        double height = Math.abs(r.getVertex().y - dirY);
        double width = Math.abs(r.getVertex().x - dirX);
        r.setHeight(height);
        r.setWidth(width);
        r.setVertex(r.getNewVertex(dirX, dirY));
        Rectangle preview = (Rectangle)model.getDrawables().getLast();
        preview.modify(r);
        model.modify();
        r.setVertex(p);
        r.setHeight(0);
        r.setWidth(0);
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}
}
