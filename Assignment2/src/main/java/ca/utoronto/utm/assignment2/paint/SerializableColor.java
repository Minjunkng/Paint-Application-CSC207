package ca.utoronto.utm.assignment2.paint;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class SerializableColor implements Serializable {
    //Since color is not serializable, class to encapsulate is required to save into file.
    private double red;
    private double green;
    private double blue;
    private double opacity;

    // Constructor accepts a Color object
    public SerializableColor(Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
    }

    // Converts SerializableColor back to Color
    public Color toColor() {
        return new Color(red, green, blue, opacity);
    }
}
