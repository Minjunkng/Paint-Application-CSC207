package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

public class DrawingModeOval implements DrawingMode {
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model){
        s.resetState();
        Point vertex = new Point(mouseEvent.getX(), mouseEvent.getY());
        Oval o = new Oval(vertex,0,0, model.getCurrentColor(), model.getFillType(), "Oval");
        s.setDrawable(new Oval(o.getVertex(),0,0,o.getColor(), model.getFillType(), "Oval"));
        if (ThicknessHandler.thicknessValue > 0) {
            o.setThickness(ThicknessHandler.thicknessValue);
        }
        model.add(o);
    }
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model){
        double dirX = mouseEvent.getX();
        double dirY = mouseEvent.getY();
        Oval o = (Oval)s.getDrawable();
        Point p = new Point(o.getVertex().x, o.getVertex().y);
        double height = Math.abs(o.getVertex().y - dirY);
        double width = Math.abs(o.getVertex().x - dirX);
        o.setHeight(height);
        o.setWidth(width);
        o.setVertex(o.getNewVertex(dirX, dirY));
        Oval preview = (Oval)model.getDrawables().getLast();
        preview.modify(o);
        model.modify();
        o.setVertex(p);
        o.setHeight(0);
        o.setWidth(0);
    }
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model){
        s.resetState();
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}
}
