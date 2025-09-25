package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ShapeFactory {
    public Shape createShape(String shapeType) {
        switch (shapeType) {
            case "Circle":
                return new Circle(7, Color.BLACK);
            case "Rectangle":
                return new Rectangle(22, 11, Color.BLACK);
            case "Square":
                return new Rectangle(11, 11, Color.BLACK);
            case "Squiggle":
                Polyline squiggle = new Polyline();
                createSquiggle(squiggle);
                return squiggle;
            case "Polylines":
                Polyline polyline = new Polyline();
                createPolyline(polyline);
                return polyline;
            case "Triangle":
                Polygon triangle = new Polygon();
                triangle.getPoints().addAll(10.0, 0.0, 17.0, 12.0, 3.0, 12.0);
                triangle.setFill(Color.BLACK);
                return triangle;
            case "Oval":
                return new Ellipse(12.0, 6);
            case "ObjectEraser":
                return new Rectangle(11, 11, Color.WHITE);
        }
        throw new IllegalArgumentException();
    }
    private void createSquiggle(Polyline squiggle) {
        int numPoints = 50;
        int amp = 6;
        double freq = 0.14;

        for (int i = 0; i < numPoints; i++) {
            double xCord = i * (50 / numPoints);
            double yCord = amp * Math.sin(freq * xCord);
            squiggle.getPoints().addAll(xCord, yCord);
        }
        squiggle.setStroke(Color.BLACK);
        squiggle.setStrokeWidth(2.5);
    }

    private void createPolyline(Polyline polyline) {
        polyline.getPoints().addAll(0.0, 0.0,
                10.0, 10.0,
                20.0, 0.0,
                30.0, 10.0,
                40.0, 0.0,
                50.0, 10.0
        );
        polyline.setStroke(Color.BLACK);
        polyline.setStrokeWidth(2.5);
    }
}
