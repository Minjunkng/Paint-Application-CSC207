package ca.utoronto.utm.assignment2.paint;
import javafx.scene.canvas.Canvas;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

public class PaintPanel extends Canvas implements EventHandler<MouseEvent>, Observer {
    private String mode="Circle";
    private String mostRecentShape = "Circle";
    private PaintModel model;
    private DrawingMode currentMode = new DrawingModeCircle();

    public PaintPanel(PaintModel model) {
        super(300, 300);
        this.model=model;
        this.model.addObserver(this);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, this);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, this);
    }
    public void resetPolyline() {
        if (this.mode.equals("Polylines")) {
            DrawingModePolylines e = (DrawingModePolylines) currentMode;
            e.exit(DrawableState.getInstance(), model);
        }

    }
    /**
     *  Controller aspect of this
     */
    public void setMode(String mode){
        if (this.mode.equals("Polylines")) {
            DrawingModePolylines e = (DrawingModePolylines) currentMode;
            e.exit(DrawableState.getInstance(), model);
        }

        this.mode=mode;
        switch (mode) {
            case "Circle" -> {
                currentMode = new DrawingModeCircle();
                this.setMostRecentShape("Circle");
            }
            case "Rectangle" -> {
                currentMode = new DrawingModeRectangle();
                this.setMostRecentShape("Rectangle");
            }
            case "Squiggle" -> {
                currentMode = new DrawingModeSquiggle();
                this.setMostRecentShape("Squiggle");
            }
            case "Square" -> {
                currentMode = new DrawingModeSquare();
                this.setMostRecentShape("Square");
            }
            case "Triangle" -> {
                currentMode = new DrawingModeTriangle();
                this.setMostRecentShape("Triangle");
            }
            case "Oval" -> {
                currentMode = new DrawingModeOval();
                this.setMostRecentShape("Oval");
            }
            case "ObjectEraser" -> {
                currentMode = new DrawingModeObjectEraser();
                this.setMostRecentShape("ObjectEraser");
            }
            case "Polylines" -> {
                currentMode = new DrawingModePolylines();
                this.setMostRecentShape("Polylines");
            }
            case "Copy"  -> currentMode = new DrawingModeCopyCut("Copy");
            case "Cut" -> currentMode = new DrawingModeCopyCut("Cut");
            case "Paste" -> currentMode = new DrawingModePaste();
            case "Select" -> currentMode = new DrawingModeSelect();
        }
    }

    public String getMode(){
        return this.mode;
    }

    public void setMostRecentShape(String mode){
        this.mostRecentShape = mode;
    }

    public String getMostRecentShape(){
        return this.mostRecentShape;
    }



    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();
        // instead of class variables a state is used
        DrawableState s = DrawableState.getInstance();

        switch (mouseEventType.getName()) {
            case "MOUSE_PRESSED":
                currentMode.mousePressed(mouseEvent, s, model);
                break;
            case "MOUSE_DRAGGED":
                currentMode.mouseDragged(mouseEvent, s, model);
                break;
            case "MOUSE_RELEASED":
                currentMode.mouseReleased(mouseEvent, s, model);
                break;
            case "MOUSE_MOVED":
                currentMode.mouseMoved(mouseEvent, s, model);
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        // Clears canvas
        GraphicsContext g2d = this.getGraphicsContext2D();
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());


        // Draws existing shapes
        for(Drawable d: this.model.getDrawables()){
            // Properly centering the initial point
            d.draw(g2d);
        }

        // Draw the grid
        String gridLevel = this.model.getGridLevel();
        this.drawGrid(g2d, gridLevel);
    }

    private void drawGrid(GraphicsContext g2d, String level){
        double gridSpacing = 0;
        g2d.setStroke(Color.BLACK);
        g2d.setFill(Color.BLACK);
        g2d.setLineWidth(1);
        switch (level) {
            case "None":
                return; // No grid
            case "Large":
                gridSpacing = 40;
                break;
            case "Medium":
                gridSpacing = 20;
                break;
            case "Small":
                gridSpacing = 10;
                break;
        }

        // Drawing grid based on grid spacing
        for (double x = 0; x < this.getWidth(); x += gridSpacing) {
            g2d.strokeLine(x, 0, x, this.getHeight());  // Draws vertical lines of grid
        }
        for (double y = 0; y < this.getHeight(); y += gridSpacing) {
            g2d.strokeLine(0, y, this.getWidth(), y);  // Draws horizontal lines of grid
        }
    }
}

