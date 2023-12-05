package com.example.demo3;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.util.Optional;

public class HelloController {
    @FXML
    private MenuItem filterNeg;
    @FXML
    private MenuItem filterPix;
    @FXML
    private MenuItem filterId;
    @FXML
    private MenuItem filterTresh;
    @FXML
    private MenuItem filterOld;
    @FXML
    private MenuItem filterBW;
    @FXML
    private MenuItem filterVin;
    @FXML
    private MenuItem filterCol;
    Popup popup = new Popup();

    @FXML
    private Button InsertImgF;
    @FXML
    private ImageView mainimage;
    @FXML
    private MenuItem saveImg;
    @FXML
    private Button restoreButton;
    @FXML
    private RadioButton originalRadio;
    @FXML
    private RadioButton moddedRadio;

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
    protected void loadPicture(){
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().addAll(
                /*new FileChooser.ExtensionFilter("All Images", "*.*"),*/
                new FileChooser.ExtensionFilter("Supported Image Formats", "*.jpg", "*.png", "*.bmp", "*.dib")
        );
        filechooser.setTitle("Select an image to load");
        Optional<File> fileOptional = Optional.ofNullable(filechooser.showOpenDialog(InsertImgF.getScene().getWindow()));
        fileOptional.ifPresent( // Grumbajzik on top
                file -> {
                    Image image = new Image(file.toURI().toString());
                    mainimage.setImage(image);
                    saveImg.setDisable(false);
                    restoreButton.setDisable(false);
                    originalRadio.setDisable(false);
                    moddedRadio.setDisable(false);
                    filterNeg.setDisable(false);
                    filterBW.setDisable(false);
                    filterCol.setDisable(false);
                    filterId.setDisable(false);
                    filterOld.setDisable(false);
                    filterPix.setDisable(false);
                    filterTresh.setDisable(false);
                    filterVin.setDisable(false);
                });
    }
    @FXML
    protected void savePicture(){ // thx to Grumbajzik for helping with this section <3
        FileChooser fileChooser = new FileChooser();
        File fiiiile = fileChooser.showSaveDialog(InsertImgF.getScene().getWindow());
        Image javafxImage = mainimage.getImage();
        if (javafxImage != null) {
            int width = (int) javafxImage.getWidth();
            int height = (int) javafxImage.getHeight();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            PixelReader pixelReader = javafxImage.getPixelReader();

            // Načtení pixelů a jejich nastavení v BufferedImage
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = pixelReader.getArgb(x, y);
                    bufferedImage.setRGB(x, y, argb);
                }
            }
            try{ImageIO.write(bufferedImage, "PNG", fiiiile);}catch(Exception e){System.out.println(e);}
        }
    }
}
