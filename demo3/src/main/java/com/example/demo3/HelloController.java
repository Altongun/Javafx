package com.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Optional;

public class HelloController {
    ImageWorker imageww;
    int[][] matrix;

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
    private Button applyMF;
    @FXML
    private RadioButton originalRadio;
    @FXML
    private RadioButton moddedRadio;


    @FXML
    protected void generatePicture(){
        this.imageww = new ImageWorker(mainimage, originalRadio, moddedRadio);
        enableEditSuite();
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
                    enableEditSuite();
                });
    }
    @FXML
    protected void copyFromClipboard(){
        Clipboard clippy = Clipboard.getSystemClipboard();
        if(clippy.hasImage()){
            this.imageww = new ImageWorker(clippy.getImage(), mainimage, originalRadio, moddedRadio);
            enableEditSuite();
        }
    }
    protected void enableEditSuite(){
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
        if(this.matrix != null){
            applyMF.setDisable(false);
        }
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
    public void initCopyListener() {
        mainimage.getScene().getAccelerators().put(new KeyCodeCombination(
                KeyCode.V, KeyCombination.CONTROL_DOWN), this::copyFromClipboard);
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



    @FXML
    protected void matrixEditor(){
        Stage matrixStage = new Stage();
        VBox start = new VBox();
        HBox[] rows = new HBox[3];
        for (int x = 0; x < 3; x++) {
            rows[x] = new HBox();
            for (int y = 0; y < 3; y++) {
                rows[x].getChildren().add(new TextField());
            }
        }
        Slider sliderx = new Slider(0,10,3);
        sliderx.setBlockIncrement(1);
        Slider slidery = new Slider(0,10,3);
        slidery.setBlockIncrement(1);
        Label dimensions = new Label("3;3");
        Button acceptButton = new Button("Accept");
        start.getChildren().addAll(rows);
        start.getChildren().addAll(sliderx,slidery, dimensions, acceptButton);
        sliderx.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                sliderx.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            start.getChildren().removeAll(start.getChildren());
            HBox[] newRows = new HBox[newValue.intValue()];
            Number dim2 = slidery.getValue();
            for (int x = 0; x < newValue.intValue(); x++) {
                newRows[x] = new HBox();
                for (int y = 0; y < dim2.intValue(); y++) {
                    newRows[x].getChildren().add(new TextField());
                }
            }
            dimensions.setText(newValue.intValue() + ";" + dim2.intValue());
            start.getChildren().addAll(newRows);
            start.getChildren().addAll(sliderx, slidery, dimensions, acceptButton);
        });
        slidery.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                slidery.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            start.getChildren().removeAll(start.getChildren());
            Number dim1 = sliderx.getValue();
            HBox[] newRows = new HBox[dim1.intValue()];
            for (int x = 0; x < dim1.intValue(); x++) {
                newRows[x] = new HBox();
                for (int y = 0; y < newValue.intValue(); y++) {
                    newRows[x].getChildren().add(new TextField());
                }
            }
            dimensions.setText(dim1.intValue() + ";" + newValue.intValue());
            start.getChildren().addAll(newRows);
            start.getChildren().addAll(sliderx, slidery, dimensions, acceptButton);
        });
        acceptButton.setOnAction(event -> {
            try {
                Number dim1 = sliderx.getValue();
                Number dim2 = slidery.getValue();
                this.matrix = new int[dim1.intValue()][dim2.intValue()];
                start.getChildren().removeAll(sliderx, slidery, dimensions, acceptButton);
                for (int a = 0; a < this.matrix.length; a++) {
                    for (int b = 0; b < this.matrix[a].length; b++) {
                        HBox iter = (HBox) start.getChildren().get(a);
                        this.matrix[a][b] = Integer.parseInt(((TextField) iter.getChildren().get(b)).getText());
                    }
                }
                start.getScene().getWindow().hide();
                drawMatrix();
                if(this.imageww != null){
                    applyMF.setDisable(false);
                }
            }catch(Exception ignored){
                Alert ono = new Alert(Alert.AlertType.ERROR, "Invalid values in matrix. Please try again.");
                ono.show();
                start.getChildren().addAll(sliderx, slidery, dimensions, acceptButton);
                applyMF.setDisable(true);
            }
        });
        matrixStage.setTitle("Matrix editor");
        Scene scene = new Scene(start, 300,230);
        matrixStage.setScene(scene);
        matrixStage.show();
    }
    protected void drawMatrix(){

    }
    @FXML
    protected void applyMatrix(){
        this.imageww.applyMatrix(this.matrix);
    }
}


