package ca.utoronto.utm.assignment2.paint;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Circle extends Drawable implements Serializable {
        private double radius;

        public Circle(Point centre, int radius, Color c, String fillType, String shapeType){
                super(centre, c, fillType, shapeType, 2.0);
                this.radius = radius;
        }
        public Drawable getCopy() {
                Circle copy = new Circle(this.getCentre(), 3, this.color.toColor(), this.fillType, "Circle");
                copy.setRadius(this.radius);
                return copy;
        }
        public Point getCentre() {
                return this.getPoint();
        }

//        public void setCentre(Point centre) {
//                this.setPoint(centre);
//        }

        public double getRadius() {
                return radius;
        }

        public void setRadius(double radius) {
                this.radius = radius;
        }

        public void draw(GraphicsContext g2d) {
                double x = this.getCentre().x - this.getRadius();
                double y = this.getCentre().y - this.getRadius();

                double diameter = this.getRadius() * 2;

                if(this.fillType.equals("Solid")){
                        g2d.setFill(this.color.toColor());
                        g2d.fillOval(x, y, diameter, diameter);
                }
                else if(this.fillType.equals("Outline")){
                        g2d.setStroke(this.color.toColor());
                        g2d.setLineWidth(this.thickness);
                        g2d.strokeOval(x,y,diameter,diameter);
                }
        }

        public boolean contains(double mouseX, double mouseY){
                double dx = mouseX - this.getPoint().x;
                double dy = mouseY - this.getPoint().y;
                if (this.fillType.equals("Solid")){
                        return (dx * dx + dy * dy) <= (radius * radius);
                }
                double newR = radius - (this.thickness/2);
                double upperR = radius + (this.thickness/2);
                return (newR * newR) <= (dx * dx + dy * dy) && (dx * dx + dy * dy) <= upperR * upperR;
        }

        public void modify(double r) {
                this.setRadius(r);
        }
}
