package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ToggleGridHandler implements EventHandler<ActionEvent> {
    private View view;
    private int currLevel = 0;

    public ToggleGridHandler(View view){
        this.view = view;
    }

    @Override
    public void handle(ActionEvent event){
        this.view.resetPolyline();
        this.currLevel = (this.currLevel + 1) % 4; // In order, cycles through ints 0, 1, 2, and 3.
        String newGridLevel = getGridLevelName(this.currLevel);
        this.view.getPaintModel().setGridLevel(newGridLevel);

        // Changing button text
        Button source = (Button) event.getSource();
        source.setText("Grid: " + getGridLevelName(currLevel));
    }

    // Helper to convert currLevel to its string size name
    public String getGridLevelName(int level){
        return switch(level){
            case 1 -> "Large";
            case 2 -> "Medium";
            case 3 -> "Small";
            default -> "None";
        };
    }
}

