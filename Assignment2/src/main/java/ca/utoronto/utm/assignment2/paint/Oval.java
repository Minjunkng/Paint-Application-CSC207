package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Oval extends Drawable implements Serializable {
    private double height;
    private double width;

    public Oval(Point v, double h, double w, Color c, String fillType, String shapeType) {
        super(v, c, fillType, shapeType, 2.0);
        this.height = h;
        this.width = w;
    }
    public Drawable getCopy() {
        return new Oval(this.getPoint(), this.height, this.width, this.color.toColor(), this.fillType, "Oval");
    }
    public Point getVertex() {
        return this.getPoint();
    }

    public void setVertex(Point v) {
        this.setPoint(v);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double h) {
        this.height = h;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double w) {
        this.width = w;
    }

    public Point getNewVertex(double x, double y) {
        Point newVertex = this.getVertex();
        if (x < this.getVertex().x) {
            newVertex.x = x;
        }
        if (y < this.getVertex().y) {
            newVertex.y = y;
        }
        return newVertex;
    }

    public void draw(GraphicsContext g2d) {
        Point v = this.getVertex();
        if (this.fillType.equals("Solid")) {
            g2d.setFill(this.getColor());
            g2d.fillOval(v.x, v.y, this.width, this.height);
        } else if (this.fillType.equals("Outline")) {
            g2d.setStroke(this.color.toColor());
            g2d.setLineWidth(this.thickness);
            g2d.strokeOval(v.x, v.y, this.width, this.height);

        }
    }
    @Override
    public boolean contains(double mouseX, double mouseY) {
        Point topLeft = this.getVertex();
        double centerX = topLeft.x + (this.width / 2);
        double centerY = topLeft.y + (this.height / 2);
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        if (this.fillType.equals("Solid")) {

            double radiusX = this.width / 2;
            double radiusY = this.height / 2;

            // Equation for the ellipse centered at (centerX, centerY)
            return (dx * dx) / (radiusX * radiusX) + (dy * dy) / (radiusY * radiusY) <= 1;
        }
        double innerRadiusX = (this.width / 2) - (this.thickness/2);
        double innerRadiusY = (this.height / 2) - (this.thickness/2);
        double innerEllipse = (dx * dx) / (innerRadiusX * innerRadiusX) + (dy * dy) / (innerRadiusY * innerRadiusY);

        double outerRadiusX = (this.width / 2) + (this.thickness/2);
        double outerRadiusY = (this.height / 2) + (this.thickness/2);
        double outerEllipse = (dx * dx) / (outerRadiusX * outerRadiusX) + (dy * dy) / (outerRadiusY * outerRadiusY);

        return innerEllipse >= 1 && outerEllipse <= 1;



    }

    public void modify(Oval O) {
        this.setVertex(new Point(O.getVertex().x, O.getVertex().y));
        this.setWidth(O.getWidth());
        this.setHeight(O.getHeight());
    }
}
