package com.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    protected void generatePicture(){
        this.imageww = new ImageWorker(mainimage, originalRadio, moddedRadio);
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
    }
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
                    this.imageww = new ImageWorker(file, mainimage, originalRadio, moddedRadio);
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
        File filePath = fileChooser.showSaveDialog(InsertImgF.getScene().getWindow());
        try {
            ImageIO.write(imageww.currentIt, "PNG", filePath);
        } catch (Exception ignored) {}
    }



    @FXML
    protected void negativeFilter(){
        imageww.filterHandler(0);
    }
    @FXML
    protected void pixelizer(){
        imageww.filterHandler(1);
    }
    @FXML
    protected void identity(){
        imageww.filterHandler(2);
    }
    @FXML
    protected void treshold(){
        imageww.filterHandler(3);
    }
    @FXML
    protected void oldStyle(){
        imageww.filterHandler(4);
    }
    @FXML
    protected void blackAndWhite(){
        imageww.filterHandler(5);
    }
    @FXML
    protected void vinette(){
        imageww.filterHandler(6);
    }
    @FXML
    protected void colorizer(){
        imageww.filterHandler(7);
    }



    @FXML
    protected void aboutPopup() throws IOException {
        FXMLLoader aboutScreenFXML = new FXMLLoader(HelloApplication.class.getResource("about-view.fxml"));
        Stage popupStage = new Stage();
        popupStage.setTitle("About");

            popupStage.setScene(new Scene(aboutScreenFXML.load(), 300, 150));

        popupStage.show();
    }



    @FXML
    protected void exitProgram() {
        System.exit(0);
    }



    @FXML
    protected void showStepback(){
        imageww.whatToShow(false);
    }
    @FXML
    protected void showCurrent(){
        imageww.whatToShow(true);
    }
}


