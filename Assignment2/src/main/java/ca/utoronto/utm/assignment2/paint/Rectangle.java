package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Rectangle extends Drawable implements Serializable {
    private double height;
    private double width;

    public Rectangle(Point v, double h, double w, Color c, String fillType, String shapeType) {
        super(v, c, fillType, shapeType, 2.0);
        this.height = h;
        this.width = w;
    }
    public Drawable getCopy() {
        return new Rectangle(this.getPoint(), this.height, this.width, this.color.toColor(), this.fillType, "Rectangle");
    }
    public Point getVertex() {
        return this.getPoint();
    }
    public void setVertex(Point v) {this.setPoint(v);}

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Point getNewVertex(double x, double y) {
        Point newVertex = new Point(this.getVertex().x, this.getVertex().y);
        if (x < this.getVertex().x) {
            newVertex.x = newVertex.x - this.width;
        }
        if (y < this.getVertex().y) {
            newVertex.y = newVertex.y - this.height;;
        }
        return newVertex;
    }

    public void draw(GraphicsContext g2d) {
        Point v = this.getVertex();
        if(this.fillType.equals("Solid")){
            g2d.setFill(this.getColor());
            g2d.fillRect(v.x, v.y, this.width, this.height);
        }
        else if(this.fillType.equals("Outline")){
            g2d.setStroke(this.color.toColor());
            g2d.setLineWidth(this.thickness);
            g2d.strokeRect(v.x, v.y, this.width, this.height);
        }
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        double x = this.getPoint().x;
        double y = this.getPoint().y;
        if (this.fillType.equals("Solid")) {
            return mouseX >= x && mouseX <= x + this.width && mouseY >= y && mouseY <= y + this.height;
        }
        double innerLeft = x + this.thickness / 2;
        double innerRight = x + width - this.thickness / 2;
        double innerTop = y + this.thickness / 2;
        double innerBottom = y + height - this.thickness / 2;

        double outerLeft = x - this.thickness / 2;
        double outerRight = x + width + this.thickness / 2;
        double outerTop = y - this.thickness / 2;
        double outerBottom = y + height + this.thickness / 2;
        boolean isInsideOuter = (mouseX >= outerLeft && mouseX <= outerRight) &&
                (mouseY >= outerTop && mouseY <= outerBottom);

        boolean isInsideInner = (mouseX >= innerLeft && mouseX <= innerRight) &&
                (mouseY >= innerTop && mouseY <= innerBottom);

        // The mouse is inside the outer rectangle but outside the inner rectangle (inside stroke area)
        return isInsideOuter && !isInsideInner;
    }

    public void modify(Rectangle r) {
        this.setVertex(new Point(r.getVertex().x, r.getVertex().y));
        this.setWidth(r.getWidth());
        this.setHeight(r.getHeight());
    }

}
