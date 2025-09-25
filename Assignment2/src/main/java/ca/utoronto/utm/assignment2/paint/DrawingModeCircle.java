package ca.utoronto.utm.assignment2.paint;
import javafx.scene.input.MouseEvent;

public class DrawingModeCircle implements DrawingMode {
    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
        Point centre = new Point(mouseEvent.getX(), mouseEvent.getY());
        Circle c = new Circle(centre, 0, model.getCurrentColor(), model.getFillType(), "Circle");
        s.setDrawable(c);
        if (ThicknessHandler.thicknessValue > 0) {
            s.getDrawable().setThickness(ThicknessHandler.thicknessValue);
        }
        model.add(c);
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        Circle c = (Circle) s.getDrawable();
        double base = Math.abs(c.getCentre().x - mouseEvent.getX());
        double height = Math.abs(c.getCentre().y - mouseEvent.getY());
        double radius = Math.sqrt(base * base + height * height);
        c.modify(radius);
        model.modify();
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        s.resetState();
    }
    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {}
}
