package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class MutatorSelect implements Mutator {
    private ArrayList<Drawable> drawables;
    private Action action;

    public MutatorSelect(ArrayList<Drawable> drawables, Action action) {
        this.drawables = drawables;
        this.action = action;
    }

    @Override
    public void mutate(ArrayList<Drawable> canvas, String undoRedo) {
        action.select(drawables, undoRedo);
    }
}
