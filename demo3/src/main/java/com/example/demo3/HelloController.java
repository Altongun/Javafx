package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        TilePane tilepane = new TilePane();
        EventHandler<ActionEvent> closeIt = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        };
        button.setOnAction(closeIt);
        tilepane.getChildren().add(label);
        tilepane.getChildren().add(button);
        tilepane.setMaxWidth(300);
        tilepane.setBackground(new Background(new BackgroundFill(new Color(1, 1, 1, 1), new CornerRadii(10), new Insets(1))));
        popup.getContent().add(tilepane);
        popup.show(InsertImgF.getScene().getWindow());
    }
    protected void exitPopup(){
        popup.hide();
    }
}