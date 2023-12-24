package com.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

public class HelloController {
    ImageWorker imageww;
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
        FXMLLoader aboutScreenFXML = new FXMLLoader(HelloApplication.class.getResource("about-view.fxml"));
        Stage popupStage = new Stage();
        popupStage.setTitle("About");
        try{
            popupStage.setScene(new Scene(aboutScreenFXML.load(), 300, 150));
        }catch (Exception ignored){

        }
        popupStage.show();
    }
    /*@FXML
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
    }*/
    @FXML
    protected void loadPicture(){
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Supported Image Formats", "*.jpg", "*.png", "*.bmp", "*.dib")
        );
        filechooser.setTitle("Select an image to load");
        Optional<File> fileOptional = Optional.ofNullable(filechooser.showOpenDialog(InsertImgF.getScene().getWindow()));
        fileOptional.ifPresent( // Grumbajzik on top
                file -> {
                    this.imageww = new ImageWorker(file, mainimage);
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
        mainimage.setPreserveRatio(true);
        mainimage.setFitHeight(InsertImgF.getScene().getHeight()/2.0f);
        mainimage.setFitWidth(InsertImgF.getScene().getWidth()/2.0f); //první setup výšky a šířky
        InsertImgF.getScene().heightProperty().addListener((obs, oldVal, newVal) -> mainimage.setFitHeight(newVal.floatValue()/2.0f));
        InsertImgF.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            mainimage.setFitWidth(newVal.floatValue()/2.0f); // odposluchače na změnu výšky a šířky, upraví výšku/šířku obrázku
        });
    }
    @FXML
    protected void savePicture(){ // thx to Grumbajzik for helping with this section <3
        FileChooser fileChooser = new FileChooser();
        File filePath = fileChooser.showSaveDialog(InsertImgF.getScene().getWindow());

        Optional.ofNullable(mainimage.getImage()).ifPresent(javafxImage -> {
            int width = (int) javafxImage.getWidth();
            int height = (int) javafxImage.getHeight();

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            PixelReader pixelReader = javafxImage.getPixelReader();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int argb = pixelReader.getArgb(x, y);
                    bufferedImage.setRGB(x, y, argb);
                }
            }

            try {
                ImageIO.write(bufferedImage, "PNG", filePath);
            } catch (Exception ignored) {
            }
        });
    }

}


