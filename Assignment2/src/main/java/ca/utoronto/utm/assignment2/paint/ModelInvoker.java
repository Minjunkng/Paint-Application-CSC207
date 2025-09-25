package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

public class ModelInvoker {

    private ArrayList<Mutator> madeActions = new ArrayList<>();
    private ArrayList<Mutator> undoneActions = new ArrayList<>();
    private ArrayList<Drawable> drawables;

    public ModelInvoker(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    public void undo() {
        if (!madeActions.isEmpty()) {
            undoneActions.add(this.madeActions.getLast());
            this.madeActions.getLast().mutate(drawables, "Undo");
            this.madeActions.removeLast();
        }
    }

    public void redo() {
        if (!undoneActions.isEmpty()) {
            madeActions.add(this.undoneActions.getLast());
            this.undoneActions.getLast().mutate(drawables, "Redo");
            this.undoneActions.removeLast();
        }
    }

    public void registerAction(Mutator mutator) {
        madeActions.add(mutator);
    }

    public void wipeHistory() {
        madeActions.clear();
        undoneActions.clear();
    }
}
