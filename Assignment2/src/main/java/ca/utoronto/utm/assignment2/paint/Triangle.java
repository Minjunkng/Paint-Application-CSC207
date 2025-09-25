package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Triangle extends Drawable implements Serializable {
    private double base;
    private double height;

    public Triangle(Point v,double base, double height, Color c, String fillType, String shapeType) {
        super(v, c, fillType, shapeType, 2.0);  // v is the vertex connecting the two sides that are equal length
        this.base = base;
        this.height = height;
    }

    public Drawable getCopy() {
        return new Triangle(this.point, this.base, this.height, this.color.toColor(), this.fillType, "Triangle");
    }

    public Point getVertex(){
        return this.getPoint();
    }

    public void setVertex(Point v){
        this.setPoint(v);
    }

    public double getBase() {
        return this.base;
    }

    public void setBase(double base) {
        this.base = base;  // Radius
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext g2d) {
        Point v = this.getVertex();

        // Calculating three vertices of triangle
        double x1 = v.x;
        double y1 = v.y;
        double x2 = v.x + this.getBase();
        double y2 = v.y + this.getHeight();
        double x3 = v.x - this.getBase();
        double y3 = v.y + this.getHeight();
        if(this.fillType.equals("Solid")){
            g2d.setFill(this.getColor());
            g2d.fillPolygon(new double[]{x1,x2,x3},new double[]{y1,y2,y3}, 3);
        }
        else if(this.fillType.equals("Outline")){
            g2d.setStroke(this.color.toColor());
            g2d.setLineWidth(this.thickness);
            g2d.strokePolygon(new double[]{x1,x2,x3},new double[]{y1,y2,y3}, 3);
        }
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        Point v = this.getVertex();
        double b = this.getBase();
        double h = this.getHeight();
        if (this.fillType.equals("Solid")) {
            return insideTriangle(v, b, h, mouseX, mouseY);
        }
        Point[] n = {new Point(v.x, v.y), new Point(v.x + b, v.y + h),
                     new Point(v.x - b, v.y + h), new Point(v.x, v.y)};
        for (int j = 0; j < n.length - 1; j++) {
            Point p1 = n[j];
            Point p2 = n[j + 1];
            double distance = distanceToLineSegment(mouseX, mouseY, p1.x, p1.y, p2.x, p2.y);

            // If the distance is less than or equal to half the thickness, return true
            if (distance <= this.thickness / 2.0) {
                return true;
            }
        }
        return false;
    }


    private Boolean insideTriangle(Point v, double b, double h, double mouseX, double mouseY) {
        double x1 = v.x;
        double y1 = v.y;
        double x2 = v.x + b;
        double y2 = v.y + h;
        double x3 = v.x - b;


        double area = triangleArea(x1, y1, x2, y2, x3, y2);

        double area1 = triangleArea(mouseX, mouseY, x2, y2, x3, y2);
        double area2 = triangleArea(x1, y1, mouseX, mouseY, x3, y2);
        double area3 = triangleArea(x1, y1, x2, y2, mouseX, mouseY);

        return Math.abs(area - (area1 + area2 + area3)) < 1e-10;
    }

    private double triangleArea(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    private double distanceToLineSegment(double px, double py, double x1, double y1, double x2, double y2) {
        double len2 = (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
        if (len2 == 0) {
            return Math.hypot(px - x1, py - y1); // if points same, just calculate distance to p1
        }

        // projection
        double t = ((px - x1) * (x2 - x1) + (py - y1) * (y2 - y1)) / len2;

        // clamp
        t = Math.max(0, Math.min(1, t));

        // closest point on the segment to the mouse
        double closestX = x1 + t * (x2 - x1);
        double closestY = y1 + t * (y2 - y1);

        // return distance between the mouse and the closest point
        return Math.hypot(px - closestX, py - closestY);
    }

    public void modify(Triangle t) {
        this.setVertex(t.getPoint());
        this.setBase(t.getBase());
        this.setHeight(t.getHeight());
    }
}
