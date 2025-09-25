package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Squiggle extends Drawable implements Serializable {
    private ArrayList<Point> points;

    public Squiggle(Point point, Color c, String fillType, String shapeType) {
        super(point, c, fillType, shapeType, 2.0);
        this.points = new ArrayList<>();
        this.points.add(point);
    }
    public Drawable getCopy() {
        Point start = new Point(this.getPoints().getFirst().x, this.getPoints().getFirst().y);
        Squiggle copy = new Squiggle(start, this.color.toColor(), this.fillType, "Squiggle");
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

            double distance = distance(mouseX, mouseY, p1, p2);
            if (distance < this.thickness/2) {return true;}
        }
        return false;
    }

    private double distance(double x, double y, Point p1, Point p2) {
        double len2 = (p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y);
        if (len2 == 0) {return Math.hypot(x - p1.x, y - p1.y);}

        // projection of the mouse point onto the line seg
        double t = ((x - p1.x) * (p2.x - p1.x) + (y - p1.y) * (p2.y - p1.y)) / len2;

        // limit the point to be on this line segment
        t = Math.max(0, Math.min(1, t));

        // closest point on the line segment
        double closestX = p1.x + t * (p2.x - p1.x);
        double closestY = p1.y + t * (p2.y - p1.y);

        return Math.hypot(x - closestX, y - closestY);
    }


}
