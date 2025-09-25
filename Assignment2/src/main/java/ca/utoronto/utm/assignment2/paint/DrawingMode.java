package ca.utoronto.utm.assignment2.paint;
import javafx.scene.input.MouseEvent;

public interface DrawingMode {
    void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model);
    void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model);
    void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model);
    void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model);
}
