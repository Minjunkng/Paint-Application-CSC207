package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ClearShapeHandler implements EventHandler<ActionEvent> {
    private View v;

    public ClearShapeHandler(View view) {
        this.v = view;
    }

    @Override
    public void handle(ActionEvent event) {
        String currentMode = this.v.getPaintPanel().getMode();

        // Check if the current mode is not one of the restricted modes
        if (!(currentMode.equals("Copy") ||
                currentMode.equals("Cut") ||
                currentMode.equals("Select") ||
                currentMode.equals("Paste"))) {

            if (currentMode.equals("Polylines")) {
                this.v.resetPolyline();
                this.v.getPaintModel().removeShape(currentMode);
            } else {
                this.v.getPaintModel().removeShape(currentMode);
            }
        } else {
            // If we're in a restricted mode (copy, cut, select, paste), remove the most recent shape (plus eraser).
            this.v.getPaintModel().removeShape(this.v.getPaintPanel().getMostRecentShape());
        }
    }
}
