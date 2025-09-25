package ca.utoronto.utm.assignment2.paint;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polylines extends Drawable implements Serializable {
    private ArrayList<Point> points;

    public Polylines(Point point, Color color, String fillType, String shapeType) {
        super(point, color, fillType, shapeType, 2.0);
        this.points = new ArrayList<>();
        this.points.add(point);

    }
    public Drawable getCopy() {
        Point start = new Point(this.getPoints().getFirst().x, this.getPoints().getFirst().y);
        Polylines copy = new Polylines(start, this.color.toColor(), this.fillType, "Polylines");
        for (int i = 1; i < this.getPoints().size(); i++) {
            copy.addPoint(new Point(this.getPoints().get(i).x, this.getPoints().get(i).y));
        }
        return copy;
    }
    public void setPoint(Point point) {
        double difX = point.x - this.getPoints().getFirst().x;
        double difY = point.y - this.getPoints().getFirst().y;
        for (int i = 0; i < this.getPoints().size(); i++) {
            this.points.get(i).x += difX;
            this.points.get(i).y += difY;
        }
    }
    public void addPoint(Point p){
        this.points.add(p);
    }

    public ArrayList<Point> getPoints(){
        return this.points;
    }

    public  void removePoint(){
        this.points.removeLast();
    }

    public void draw(GraphicsContext g2d) {
        g2d.setLineWidth(this.thickness);
        g2d.setStroke(this.color.toColor());
        g2d.setStroke(this.getColor());
        for(int j=0;j<points.size()-1;j++){
            Point p1=points.get(j);
            Point p2=points.get(j+1);
            g2d.strokeLine(p1.x,p1.y,p2.x,p2.y);
        }
    }

    @Override
    public boolean contains(double mouseX, double mouseY) {
        for (int j = 0; j < points.size() - 1; j++) {
            Point p1 = points.get(j);
            Point p2 = points.get(j + 1);
            double distance = distanceToLineSegment(mouseX, mouseY, p1.x, p1.y, p2.x, p2.y);

            // If the distance is less than or equal to half the thickness, return true
            if (distance <= this.thickness / 2.0) {
                return true;
            }
        }
        return false;
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

}
