package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class MutatorCut implements Mutator {
    private ArrayList<Drawable> drawables;
    private ArrayList<Integer> indexes;
    private Action action;

    public MutatorCut(ArrayList<Drawable> drawables, ArrayList<Integer> indexes, Action action) {
        this.drawables = drawables;
        this.indexes = indexes;
        this.action = action;
    }

    @Override
    public void mutate(ArrayList<Drawable> canvas, String undoRedo) {
        action.cut(canvas, drawables, indexes, undoRedo);
    }
}
