package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class MutatorPlace implements Mutator {
    private ArrayList<Drawable> ActionInfo;
    private ArrayList<Integer> indexes;
    private Action action;

    public MutatorPlace(ArrayList<Drawable> drawables, ArrayList<Integer> indexes, Action action) {
        this.ActionInfo = drawables;
        this.indexes = indexes;
        this.action = action;
    }

    @Override
    public void mutate(ArrayList<Drawable> canvas, String undoRedo) {
        action.place(canvas, ActionInfo, indexes, undoRedo);
    }
}
