package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public abstract class Drawable implements Serializable {
    protected SerializableColor color;
    protected Point point;
    protected String fillType;
    protected String shapeType;
    protected double thickness;


    public Drawable(Point p, Color color, String fillType, String shapeType, double thickness) {
        this.color = new SerializableColor(color);
        this.point = p;
        this.fillType = fillType;
        this.shapeType = shapeType;
        this.thickness = thickness;
    }

    public abstract void draw(GraphicsContext g2d);
    public abstract boolean contains(double mouseX, double mouseY);
    public abstract Drawable getCopy();

    public Point getPoint() {
        return this.point;
    }
    public void setPoint(Point point) {
        this.point = point;
    }
    public Color getColor() {
        return this.color.toColor();
    }

    public void modify(){};

    public String getFillType() {
        return fillType;
    }
    public void setFillType(String fillType) {
        this.fillType = fillType;
    }

    public String getShapeType() {return this.shapeType;}

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
}
