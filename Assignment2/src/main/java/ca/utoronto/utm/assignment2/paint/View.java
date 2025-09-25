package ca.utoronto.utm.assignment2.paint;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import java.util.ArrayList;

public class View implements EventHandler<ActionEvent> {

        private PaintModel paintModel;
        private PaintPanel paintPanel;
        private ShapeChooserPanel shapeChooserPanel;
        private Slider opacity;
        private ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        private static SaveState saveState = new SaveState();


        public View(PaintModel model, Stage stage) {
                this.paintModel = model;
                this.paintPanel = new PaintPanel(this.paintModel);
                this.shapeChooserPanel = new ShapeChooserPanel(this);

                BorderPane root = new BorderPane();
                this.paintPanel.widthProperty().bind(root.widthProperty());
                this.paintPanel.heightProperty().bind(root.heightProperty());
                this.setupCanvasListeners();
                root.setTop(createMenuBar());
                root.setCenter(this.paintPanel);
                root.setLeft(this.shapeChooserPanel);
                Scene scene = new Scene(root, 500, 370);
                stage.setScene(scene);
                stage.setTitle("Paint");
                stage.show();
        }
        public void resetPolyline() {
                this.paintPanel.resetPolyline();
        }
        public PaintModel getPaintModel() {
                return this.paintModel;
        }

        public PaintPanel getPaintPanel() { return this.paintPanel; }

        // ugly way to do this?
        public void setMode(String mode){
                this.paintPanel.setMode(mode);
        }

        private MenuBar createMenuBar() {

                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;

                // A menu for File

                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                Menu nestedMenu = new Menu("Save");
                menuItem = new MenuItem("Non-editable (PNG)");
                menuItem.setOnAction(this);
                nestedMenu.getItems().add(menuItem);
                menuItem = new MenuItem("Editable (.ser)");
                menuItem.setOnAction(this);
                nestedMenu.getItems().add(menuItem);
                menu.getItems().add(nestedMenu);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Another menu for Edit

                menu = new Menu("Edit");

                menuItem = new MenuItem("Cut");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Copy");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Paste");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());
                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());
                menuItem = new MenuItem("Select");
                menuItem.setOnAction(this);
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                menu = new Menu("Color");
                CustomMenuItem cp = new CustomMenuItem(this.colorPicker);
                cp.setText("Color");
                cp.setOnAction(this);
                cp.setHideOnClick(false);
                menu.getItems().add(cp);
                menuBar.getMenus().add(menu);

                menu = new Menu("Opacity");
                this.opacity = new Slider(0, 1, 1);
                CustomMenuItem slider = new CustomMenuItem(this.opacity);
                slider.setText("Opacity");
                slider.setHideOnClick(false);
                this.opacity.setShowTickMarks(true);
                this.opacity.setShowTickLabels(true);
                this.opacity.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

                // set Major tick unit
                this.opacity.setMajorTickUnit(0.25f);
                // blockIncrement
                this.opacity.setBlockIncrement(0.05);
                setOpacitySliderHandlers();
                slider.setOnAction(this);
                menu.getItems().add(slider);
                menuBar.getMenus().add(menu);

                return menuBar;
        }

        private void saveFileWindow() {
                Stage saveStage = new Stage();
                saveStage.initModality(Modality.APPLICATION_MODAL);
                saveStage.setTitle("Save Details");
                Label fileNameLabel = new Label("File Name:");
                TextField fileNameField = new TextField();
                Button confirmButton = new Button("Confirm");
                confirmButton.setOnAction(e -> {String fileName = fileNameField.getText();
                        if (!fileName.isEmpty()) {
                                saveState.addSave(new SaveItem(fileName, (ArrayList<Object>) paintModel.getDrawables().clone()));
                                saveStage.close();
                        }});
                HBox layout = new HBox(10, fileNameLabel, fileNameField, confirmButton);
                Scene saveScene = new Scene(layout);
                saveStage.setScene(saveScene);
                saveStage.showAndWait();
        }

        private void openFileWindow() {
                Stage saveStage = new Stage();
                saveStage.initModality(Modality.APPLICATION_MODAL);
                saveStage.setTitle("Open File");
                Label descLabel = new Label("Select Save File:");
                ComboBox dropdown = new ComboBox();
                SaveState loadState = new SaveState();
                dropdown.getItems().addAll(loadState.getListSaves());
                dropdown.setOnAction(e -> { String selectedItem = dropdown.getSelectionModel().getSelectedItem().toString();
                        if (!selectedItem.isEmpty()) {
                                this.paintModel.setDrawables(loadState.getSave(selectedItem));
                                this.paintModel.mutate("Wipe History");
                                saveStage.close();
                        }
                });
                HBox layout = new HBox(10, descLabel,dropdown);
                Scene saveScene = new Scene(layout);
                saveStage.setScene(saveScene);
                saveStage.showAndWait();
        }


        @Override
        public void handle(ActionEvent event) {
                String command = ((MenuItem) event.getSource()).getText();
                if (command.equals("Color")){
                        this.colorPicker.setValue(this.colorPicker.getValue());
                        this.paintModel.setCurrentColor(this.colorPicker.getValue());
                } else if (command.equals("Exit")) {
                        Platform.exit();
                } else if (command.equals("Undo") || command.equals("Redo")) {
                        this.paintModel.mutate(command);
                } else if (command.equals("Copy")|| command.equals("Cut")|| command.equals("Paste")||command.equals("Select")) {
                        this.setMode(command);
                        shapeChooserPanel.clearHighlight();
                } else if (command.equals("New")) {
                        this.paintModel.mutate("Clear");
                        this.paintModel.getDrawables().clear();
                        this.paintModel.modify();
                } else if (command.equals("Editable (.ser)")) {
                        this.saveFileWindow();
                } else if (command.equals("Open")) {
                        this.openFileWindow();
                } else if (command.equals("Non-editable (PNG)")) {
                        WritableImage picture = this.paintPanel.snapshot(null, null);
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG Files", "*.jpg", "*.jpeg"));
                        File file = fileChooser.showSaveDialog(null);
                        if (file == null) {
                                return;
                        }
                        try {
                                ImageIO.write(SwingFXUtils.fromFXImage(picture, null), "PNG", file);
                        } catch (IOException e) {
                                System.out.println(e.getMessage());
                        }

                } else if (command.equals("Opacity")) {
                        updateOpacity();
                }
        }

        private void setOpacitySliderHandlers() {
                this.opacity.setOnMouseDragged(event -> updateOpacity());

                this.opacity.setOnMouseReleased(event -> updateOpacity());
        }

        public void updateOpacity() {
                Color c = this.colorPicker.getValue();
                double o = this.opacity.getValue();
                this.paintModel.setCurrentColor(c.deriveColor(0, 1, 1, o));
        }

        public void setupCanvasListeners() {
                // a single listener for both width and height
                ChangeListener<Number> resizeListener = new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                handleCanvasResize();
                        }
                };

                // same listener to both width and height properties
                this.paintPanel.widthProperty().addListener(resizeListener);
                this.paintPanel.heightProperty().addListener(resizeListener);
        }

        public void handleCanvasResize() {
                this.paintPanel.update(this.paintModel, true);
        }

}
