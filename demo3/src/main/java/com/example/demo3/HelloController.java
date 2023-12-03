package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.io.File;
import java.util.ArrayList;

public class HelloController {
    Popup popup = new Popup();

    @FXML
    private Button InsertImgF;
    @FXML
    private ImageView mainimage;

    @FXML
    protected void exitProgram() {
        System.exit(0);
    }
    @FXML
    protected void aboutPopup() {
        Label label = new Label("Paint app v1.0\nby: Grumbajzik, Jurajs_, Alton, ThatMeow\nFor PEPE, with love <3");
        Button button = new Button("Close");
        Image eastereggimg = new Image("http://media.tenor.com/y8R_Sz09brMAAAAi/faruzan-genshin-impact.gif");
        ImageView easteregg = new ImageView(eastereggimg);
        TilePane tilepane = new TilePane();
        EventHandler<ActionEvent> closeIt = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        };
        button.setOnAction(closeIt);
        tilepane.getChildren().add(label);
        tilepane.getChildren().add(button);
        tilepane.getChildren().add(easteregg);
        easteregg.setVisible(false);
        tilepane.setMaxWidth(300);
        tilepane.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 1), new CornerRadii(10), new Insets(1))));
        popup.getContent().add(tilepane);
        final KeyCombination keycombo =  new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN);
        popup.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                    if(keycombo.match(event)){
                        easteregg.setVisible(true);
                    }
                }
        });
        popup.show(InsertImgF.getScene().getWindow());
    }
    @FXML
    protected void loadPicture() {
        String filePath = "";
        File[] drives = File.listRoots();
        File file = null;
        if (drives.length == 1) {
            filePath = drives[0].toString();
            file = new File(filePath);
        }
        for (File drive : drives) {
            System.out.println(drive);
        }
        File[] files = (drives.length == 1 ? file.listFiles() : drives);
        ArrayList<Button> dirButtonList = new ArrayList<Button>();
        TilePane tilepane = new TilePane();
        tilepane.setMaxWidth(300);
        tilepane.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 1), new CornerRadii(10), new Insets(1))));
        for (File f : files) {
            String extension = findActualExtension(f.getName());
            String[] allowedExtensions = {".png", ".jpg", ".bmp", ".webp"};
            boolean correctExtension = false;
            for (String compare : allowedExtensions) {
                if (extension.equals(compare)) {
                    correctExtension = true;
                    break;
                }
            }
            if (f.isDirectory() || correctExtension) {
                Button thisIter = !f.getName().equals("") ? new Button(f.getName()) : new Button(f.toString());
                String finalFilePath = f.getAbsolutePath();
                EventHandler<ActionEvent> moveDir = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        System.out.println(f);
                        loaderHelper(finalFilePath + "/" + f.getName(), tilepane);
                    }
                };
                EventHandler<ActionEvent> endSelection = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("UwU");
                    }
                };
                if (f.isDirectory()) {
                    thisIter.setOnAction(moveDir);
                } else {
                    thisIter.setOnAction(endSelection);
                }
                tilepane.getChildren().add(thisIter);
                dirButtonList.add(thisIter);
            }
        }
        popup.getContent().add(tilepane);
        popup.show(InsertImgF.getScene().getWindow());
    }
    protected String findActualExtension(String name){
        int cutoff = name.indexOf(".");
        if (cutoff > 0){
            return findActualExtension(name.substring(cutoff));
        }else{
            return name;
        }
    }
    @FXML
    protected void exitPopup(){
        popup.hide();
    }
    protected void loaderHelper(String filePath, TilePane tilepane){
        File file = new File(filePath);
        File[] files = file.listFiles();
        ArrayList<Button> dirButtonList = new ArrayList<Button>();
        tilepane.getChildren().clear();
        for (File f : files){
            String extension = findActualExtension(f.getName());
            String[] allowedExtensions = {".png", ".jpg", ".bmp", ".webp"};
            boolean correctExtension = false;
            for (String compare : allowedExtensions){
                if (extension.equals(compare)){
                    correctExtension = true;
                    break;
                }
            }
            if (f.isDirectory() || correctExtension){
                Button thisIter = new Button(f.getName());
                EventHandler<ActionEvent> moveDir = new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent actionEvent) {
                        loaderHelper(filePath + "/" + f.getName(), tilepane);
                    }
                };
                EventHandler<ActionEvent> endSelection = new EventHandler<ActionEvent>(){
                    public void handle(ActionEvent actionEvent) {
                        mainimage.setImage(new Image(filePath + "/" + f.getName()));
                        popup.hide();
                    }
                };
                if(f.isDirectory() ){
                    thisIter.setOnAction(moveDir);
                }else{
                    thisIter.setOnAction(endSelection);
                }
                tilepane.getChildren().add(thisIter);
                dirButtonList.add(thisIter);
            }
        }
    }
}