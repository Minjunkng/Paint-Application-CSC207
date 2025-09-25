package ca.utoronto.utm.assignment2.paint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;

import java.util.HashMap;


public class ShapeChooserPanel extends GridPane implements EventHandler<ActionEvent> {
        private View view;
        private HashMap<String, Button> shape = new HashMap<>();
        private ShapeFactory shapeFactory;
        private Button bClearShape = new Button("Clear Circles");
        private Button bToggleGrid = new Button("Grid: None");
        private Button bSolidFill;
        private Button bOutlineFill;

        public ShapeChooserPanel(View view) {
                this.view = view;
                this.shapeFactory = new ShapeFactory();
                // Button creation
                ButtonShapeCreate("Circle", 0);
                ButtonShapeCreate("Rectangle", 1);
                ButtonShapeCreate("Square", 2);
                ButtonShapeCreate("Squiggle", 3);
                ButtonShapeCreate("Polylines", 4);
                ButtonShapeCreate("Triangle", 5);
                ButtonShapeCreate("Oval", 6);

                // Clear shapes button setup
                ClearShapeHandler clearShapeHandler = new ClearShapeHandler(this.view);
                bClearShape.setOnAction(clearShapeHandler);
                this.add(bClearShape, 0, 7);
                bClearShape.setStyle("-fx-padding: 0; -fx-alignment: CENTER;");
                bClearShape.setPrefWidth(100);
                bClearShape.setPrefHeight(27);

                // Eraser button
                ButtonShapeCreate("ObjectEraser", 8);

                // Toggle grid button setup
                ToggleGridHandler toggleGridHandler = new ToggleGridHandler(this.view);
                bToggleGrid.setOnAction(toggleGridHandler);
                this.add(bToggleGrid, 0, 9);
                bToggleGrid.setStyle("-fx-padding: 0; -fx-alignment: CENTER;");
                bToggleGrid.setPrefWidth(100);
                bToggleGrid.setPrefHeight(27);

                // Fill style buttons setup
                HBox fillStyleBox = new HBox(1);

                this.bSolidFill = new Button();
                this.bSolidFill.setUserData("Solid");
                this.bOutlineFill = new Button();
                this.bOutlineFill.setUserData("Outline");

                Label fillStyleLabel = new Label("Fill: ");
                Circle solidCircleIcon = new Circle(10, Color.BLACK);
                Circle outlineCircleIcon = new Circle(10, Color.TRANSPARENT);
                outlineCircleIcon.setStroke(Color.BLACK);
                outlineCircleIcon.setStrokeWidth(2);

                this.bSolidFill.setGraphic(solidCircleIcon);
                this.bOutlineFill.setGraphic(outlineCircleIcon);

                FillStyleHandler fillStyleHandler =
                        new FillStyleHandler(this.view.getPaintModel(), this.bSolidFill, this.bOutlineFill);
                this.bSolidFill.setOnAction(fillStyleHandler);
                this.bOutlineFill.setOnAction(fillStyleHandler);

                this.bSolidFill.setStyle("-fx-background-color: yellow");  // Highlights the current selected fill

                fillStyleBox.getChildren().addAll(fillStyleLabel,bSolidFill,bOutlineFill);

                this.add(fillStyleBox,0,10);
                Button applyThickness = new Button("Apply");
                this.add(applyThickness, 0, 13);
                TextField thicknessInput = new TextField();
                thicknessInput.setText("2.0");
                thicknessInput.setPrefWidth(10);
                this.add(thicknessInput, 0, 12);
                ThicknessHandler thickness = new ThicknessHandler(thicknessInput);
                applyThickness.setOnAction(thickness);

                this.add(new Label("Thickness: "), 0, 11);

        }

        // Helper method to create buttons for input shape and label
        private void ButtonShapeCreate(String label, int position) {
                Shape shape = shapeFactory.createShape(label);
                Button button = new Button();
                button.setGraphic(shape);
                button.setMinWidth(100);
                button.setUserData(label);
                button.setOnAction(this);
                //adds to map of shapes
                this.shape.put(label, button);
                //starter mode
                this.shape.get("Circle").setStyle("-fx-background-color: yellow");
                //adds button
                this.add(button, 0, position);
        }

        public void clearHighlight() {
                for (Button i : shape.values()) {
                        i.setStyle(null);
                }
        }

        @Override
        public void handle(ActionEvent event) {
                String command = ((Button) event.getSource()).getUserData().toString();
                for (Button i : shape.values()) {
                        i.setStyle(null);
                }
                shape.get(command).setStyle("-fx-background-color: yellow");
                view.setMode(command);

                // Changes Clear Shape button text to current shape
                if(isDrawable(command)) {
                        if (command.equals("Polylines")) {
                                this.bClearShape.setText("Clear " + command);
                        } else {
                                this.bClearShape.setText("Clear " + command + "s");
                        }
                }
                else if (command.equals("ObjectEraser")) {
                        this.bClearShape.setText("Clear None");
                }
        }

        // Helper to identify if something is Drawable object
        private static boolean isDrawable(String name) {
                try {
                        Class<?> className= Class.forName("ca.utoronto.utm.assignment2.paint." + name);

                        // Check if it implements Drawable class
                        return Drawable.class.isAssignableFrom(className);

                } catch (ClassNotFoundException e) {
                        return false;
                }
        }
}