package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModeTriangle implements DrawingMode {
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
        Point vertex = new Point(mouseEvent.getX(), mouseEvent.getY());
        Triangle triangle = new Triangle(vertex, 0, 0, model.getCurrentColor(), model.getFillType(), "Triangle");
        s.setDrawable(new Triangle(triangle.getVertex(), 0, 0, triangle.getColor(), model.getFillType(), "Triangle"));
        if (ThicknessHandler.thicknessValue > 0) {
            triangle.setThickness(ThicknessHandler.thicknessValue);
        }
        model.add(triangle);
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        Triangle t = (Triangle) s.getDrawable();
        double endX = mouseEvent.getX();
        double endY = mouseEvent.getY();

        double base = Math.abs(t.getVertex().x - endX);
        double height = Math.abs(t.getVertex().y - endY);

        // Invert the height if the mouse is above the original vertex
        if (endY < t.getVertex().y) {
            height = -height;
        }

        t.setBase(base);
        t.setHeight(height);

        Triangle preview = (Triangle) model.getDrawables().getLast();
        preview.modify(t);
        model.modify();
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}
}
