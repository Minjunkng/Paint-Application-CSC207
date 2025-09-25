package ca.utoronto.utm.assignment2.paint;

import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class DrawingModeObjectEraser implements DrawingMode {
    private AnimationTimer eraserTimer;
    private double lastMouseX = -1, lastMouseY = -1;

    @Override
    public void mousePressed(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        startEraser(mouseEvent, s, model);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        updateEraser(mouseEvent, s, model);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        stopEraser();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
    }

    private void startEraser(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        // Start timer, checks for mouse events
        eraserTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateEraser(mouseEvent, s, model);
            }
        };
        eraserTimer.start();
    }

    private void updateEraser(MouseEvent mouseEvent, DrawableState s, PaintModel model) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        // only update for significant moves (reduce redundancy)
        if (lastMouseX == -1 || lastMouseY == -1 || Math.abs(x - lastMouseX) > 5 || Math.abs(y - lastMouseY) > 5) {
            lastMouseX = x;
            lastMouseY = y;

            ArrayList<Drawable> drawings = model.getDrawables();
            for (Drawable d : drawings) {
                if (d.contains(x, y)) {
                    model.remove(d);
                    model.modify();
                    break;      // prevents thread exception
                }
            }
        }
    }

    private void stopEraser() {
        // Stop the animation timer when the mouse is released
        if (eraserTimer != null) {
            eraserTimer.stop();
        }
        lastMouseX = -1;
        lastMouseY = -1;
    }
}
