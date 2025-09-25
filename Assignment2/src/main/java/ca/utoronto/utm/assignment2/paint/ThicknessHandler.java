package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class ThicknessHandler implements EventHandler<ActionEvent> {
    TextField thickness;
    public static double thicknessValue = -1;
    public ThicknessHandler(TextField input) {
        this.thickness = input;
    }
    @Override
    public void handle(ActionEvent event) {
        try {
            thicknessValue = Double.parseDouble(thickness.getText());
        } catch (NumberFormatException e) {
            thicknessValue = -1;
        }
    }
}
