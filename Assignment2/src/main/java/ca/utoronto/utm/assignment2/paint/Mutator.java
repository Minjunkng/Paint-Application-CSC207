package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface Mutator {
    void mutate(ArrayList<Drawable> canvas, String undoRedo) throws NoSuchElementException;
}
