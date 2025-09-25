package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModeSquare implements DrawingMode {
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
        Point vertex = new Point(mouseEvent.getX(), mouseEvent.getY());
        Square square = new Square(vertex, 0, model.getCurrentColor(), model.getFillType(), "Square");
        s.setDrawable(new Square(vertex, 0, square.getColor(), model.getFillType(), "Square"));
        if (ThicknessHandler.thicknessValue > 0) {
            square.setThickness(ThicknessHandler.thicknessValue);
        }
        model.add(square);
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        double dirX = mouseEvent.getX();
        double dirY = mouseEvent.getY();
        Square square = (Square)s.getDrawable();
        Point p = new Point(square.getVertex().x, square.getVertex().y);
        double width = Math.abs(square.getVertex().x - dirX);
        square.setWidth(width);
        square.setVertex(square.getNewVertex(dirX, dirY));
        Square preview = (Square)model.getDrawables().getLast();
        preview.modify(square);
        model.modify();
        square.setVertex(p);
        square.setWidth(0);
        //Maybe change drawable to an interface?
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}

}
