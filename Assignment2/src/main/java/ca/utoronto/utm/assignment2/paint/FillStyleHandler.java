package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class FillStyleHandler implements EventHandler<ActionEvent> {
    private PaintModel model;
    private Button solidButton;
    private Button outlineButton;
    private Button currentSelectedButton;

    public FillStyleHandler(PaintModel model, Button solidButton, Button outlineButton) {
        this.model = model;
        this.solidButton = solidButton;
        this.outlineButton = outlineButton;
        this.currentSelectedButton = solidButton; // Solid selected by default
    }

    @Override
    public void handle(ActionEvent event){
        Button source = (Button) event.getSource();
        String fillType = (String) source.getUserData();
        if ("Outline".equals(fillType)) {
            this.model.setFillType("Outline");
            this.updateSelectedButton(this.outlineButton);
        }
        else if ("Solid".equals(fillType)) {
            this.model.setFillType("Solid");
            this.updateSelectedButton(this.solidButton);
        }
    }

    public void updateSelectedButton(Button newSelected){
        this.currentSelectedButton.setStyle(null);
        newSelected.setStyle("-fx-background-color: yellow");
        this.currentSelectedButton = newSelected;
    }
}
