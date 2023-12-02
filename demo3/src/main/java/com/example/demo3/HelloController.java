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

public class HelloController {
    Popup popup = new Popup();

    @FXML
    private Button InsertImgF;

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
    protected void exitPopup(){
        popup.hide();
    }
}
