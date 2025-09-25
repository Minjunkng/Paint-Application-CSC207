package ca.utoronto.utm.assignment2.paint;

public class DrawableState {
    private static DrawableState state;
    private Drawable thing;

    private DrawableState() {
        this.thing = null;
    }

    public static DrawableState getInstance() {
        if (state == null) {
            state = new DrawableState();
        }
        return state;
    }

    public void resetState() {
        this.thing = null;
    }

    public void setDrawable(Drawable d) {
        this.thing = d;
    }

    public Drawable getDrawable() {
        return this.thing;
    }

}
