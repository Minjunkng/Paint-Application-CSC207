package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.util.*;


public class PaintModel extends Observable {

        private ArrayList<Drawable> drawables = new ArrayList<>();
        ModelInvoker invoker = new ModelInvoker(this.drawables);
        private String gridLevel = "None";
        private String fillType = "Solid";
        private Color currentColor = Color.BLACK;
        private Action action = new Action();
        
        public void add(Drawable d) {
                this.drawables.add(d);
                addAction("Place", new ArrayList<>(Arrays.asList(this.drawables.size() - 1)),
                        new ArrayList<>(Arrays.asList(d)));

        }

        public ArrayList<Drawable> getDrawables() {
                return drawables;
        }

        public void setCurrentColor(Color c) {
                this.currentColor = c;
        }

        public Color getCurrentColor() {
                return currentColor;
        }

        public int remove(Drawable d) {
                for (int i = 0; i < drawables.size(); i++) {
                        if (drawables.get(i).equals(d)) {
                                drawables.remove(i);
                                return i;
                        }
                }
                return -1;
        }

        public void modify() {
                this.setChanged();
                this.notifyObservers();
        }

        public void mutate(String s) {
                if (Objects.equals(s, "Undo")) {
                        this.invoker.undo();
                } else if (Objects.equals(s, "Redo")) {
                        this.invoker.redo();
                } else if (Objects.equals(s, "Wipe History")) {
                        this.invoker.wipeHistory();
                }
                this.setChanged();
                this.notifyObservers();
        }

        public void removeShape(String shape) {
                Iterator<Drawable> iterator = this.drawables.iterator();
                int i = 0;
                ArrayList<Integer> indexes = new ArrayList<>();
                ArrayList<Drawable> toRemove = new ArrayList<>();
                while(iterator.hasNext()) {
                        Drawable drawable = iterator.next();

                        if (drawable.getShapeType().equals(shape)) {
                                indexes.add(i);
                                toRemove.add(drawable);
                                iterator.remove();
                        }
                        i++;
                }
                addAction("Clear", indexes, toRemove);
                this.modify();
        }

        public void addAction(String s, ArrayList<Integer> indexes, ArrayList<Drawable> drawables) {
                if (Objects.equals(s, "Cut")) {
                        invoker.registerAction(new MutatorCut(drawables, indexes, action));
                } else if (Objects.equals(s, "Place")){
                        invoker.registerAction(new MutatorPlace(drawables, indexes, action));
                } else if (Objects.equals(s, "Clear")){
                        invoker.registerAction(new MutatorClear(drawables, indexes, action));
                } else if (Objects.equals(s, "Select")){
                        invoker.registerAction(new MutatorSelect(drawables, action));
                }

                this.modify();
        }

        public void setGridLevel(String newGridLevel) {
                this.gridLevel = newGridLevel;
                this.modify();
        }

        public String getGridLevel(){
                return this.gridLevel;
        }

        public void setFillType(String fillType){
                this.fillType = fillType;
                this.modify();
        }

        public String getFillType(){return this.fillType;}

        public void setDrawables(ArrayList<Drawable> drawables) {
                this.drawables = drawables;
                invoker = new ModelInvoker(drawables);
                this.modify();
        }
}
