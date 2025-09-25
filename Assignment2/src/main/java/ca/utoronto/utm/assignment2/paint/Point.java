package ca.utoronto.utm.assignment2.paint;

import java.io.Serializable;

public class Point implements Serializable {
        double x, y; // Available to our package
        Point(double x, double y){
                this.x=x; this.y=y;
        }
}
