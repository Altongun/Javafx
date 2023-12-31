package com.example.demo3;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ImageWorker {
    RadioButton modifiedRadio;
    BufferedImage currentIt;
    BufferedImage stepback;
    ImageView imgView;
    RadioButton originalRadio;
    Random random = new Random();
    boolean currentImg;



    public ImageWorker(File origin, ImageView mainimgview, RadioButton originalRadio, RadioButton modifiedRadio) throws IOException {
            this.currentIt = ImageIO.read(origin);
            this.stepback = ImageIO.read(origin);


        this.imgView = mainimgview;
        this.imgView.setImage(toFXImage(this.currentIt));

        this.imgView.setPreserveRatio(true);
        this.imgView.setFitHeight(this.imgView.getScene().getHeight()/2.0f);
        this.imgView.setFitWidth(this.imgView.getScene().getWidth()/2.0f); //první setup výšky a šířky
        this.imgView.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.imgView.setFitHeight(newVal.floatValue()/2.0f));
        this.imgView.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.imgView.setFitWidth(newVal.floatValue()/2.0f); // odposluchače na změnu výšky a šířky, upraví výšku/šířku obrázku
        });
        this.originalRadio = originalRadio;
        this.modifiedRadio = modifiedRadio;
        this.originalRadio.fire();
        this.imgView.setImage(toFXImage(this.stepback));
    }
    public ImageWorker(ImageView mainimgview, RadioButton originalRadio, RadioButton modifiedRadio){
        this.currentIt = genImg();
        this.stepback = genImg();
        this.imgView = mainimgview;
        this.imgView.setImage(toFXImage(this.currentIt));

        this.imgView.setPreserveRatio(true);
        this.imgView.setFitHeight(this.imgView.getScene().getHeight()/2.0f);
        this.imgView.setFitWidth(this.imgView.getScene().getWidth()/2.0f); //první setup výšky a šířky
        this.imgView.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.imgView.setFitHeight(newVal.floatValue()/2.0f));
        this.imgView.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.imgView.setFitWidth(newVal.floatValue()/2.0f); // odposluchače na změnu výšky a šířky, upraví výšku/šířku obrázku
        });
        this.originalRadio = originalRadio;
        this.modifiedRadio = modifiedRadio;
        this.originalRadio.fire();
        this.imgView.setImage(toFXImage(this.stepback));
    }
    public ImageWorker(javafx.scene.image.Image pastedImg, ImageView mainimgview, RadioButton originalRadio, RadioButton modifiedRadio){
        BufferedImage result1 = new BufferedImage((int) pastedImg.getWidth(), (int) pastedImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage result2 = new BufferedImage((int) pastedImg.getWidth(), (int) pastedImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        PixelReader reader = pastedImg.getPixelReader();
        for (int x = 0; x < pastedImg.getWidth(); x++) {
            for (int y = 0; y < pastedImg.getHeight(); y++) {
                javafx.scene.paint.Color originalColor = reader.getColor(x,y);
                int currentB = (int) Math.floor(255 * originalColor.getBlue());
                int currentG = (int) Math.floor(255 * originalColor.getGreen());
                int currentR = (int) Math.floor(255 * originalColor.getRed());
                Color setter = new Color(currentR, currentG, currentB);
                result1.setRGB(x,y,setter.getRGB());
                result2.setRGB(x,y,setter.getRGB());
            }
        }
        this.currentIt = result1;
        this.stepback = result2;
        this.imgView = mainimgview;
        this.imgView.setImage(toFXImage(this.currentIt));

        this.imgView.setPreserveRatio(true);
        this.imgView.setFitHeight(this.imgView.getScene().getHeight()/2.0f);
        this.imgView.setFitWidth(this.imgView.getScene().getWidth()/2.0f); //první setup výšky a šířky
        this.imgView.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.imgView.setFitHeight(newVal.floatValue()/2.0f));
        this.imgView.getScene().widthProperty().addListener((obs, oldVal, newVal) ->
            this.imgView.setFitWidth(newVal.floatValue()/2.0f) // odposluchače na změnu výšky a šířky, upraví výšku/šířku obrázku
        );
        this.originalRadio = originalRadio;
        this.modifiedRadio = modifiedRadio;
        this.originalRadio.fire();
        this.imgView.setImage(toFXImage(this.stepback));
    }



    private BufferedImage genImg() {
        // Vytvoření prázdného plátna s určenou šířkou a výškou
        BufferedImage blankCanvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // Grafický kontext pro kreslení na plátno
        Graphics2D g2d = blankCanvas.createGraphics();

        // Náhodné vybrání typu tvaru (0 pro kruh, 1 pro obdélník, 2 pro mnohoúhelník)
        int shapeType = random.nextInt(7);

        // Nastavení náhodné barvy pozadí
        int backgroundRed = random.nextInt(256);
        int backgroundGreen = random.nextInt(256);
        int backgroundBlue = random.nextInt(256);
        Color backgroundColor = new Color(backgroundRed, backgroundGreen, backgroundBlue);

        // Nastavení barvy pozadí a vyplnění celého plátna
        g2d.setBackground(backgroundColor);
        g2d.clearRect(0, 0, blankCanvas.getWidth(), blankCanvas.getHeight());

        // Nastavení náhodné barvy a parametrů pro kruh, obdélník nebo mnohoúhelník
        int shapeRed = random.nextInt(256);
        int shapeGreen = random.nextInt(256);
        int shapeBlue = random.nextInt(256);
        Color shapeColor = new Color(shapeRed, shapeGreen, shapeBlue);

        int x = 50; // x-ová souřadnice začátku tvaru
        int y = 50; // y-ová souřadnice začátku tvaru
        int width = 700; // šířka tvaru
        int height = 500; // výška tvaru

        if (shapeType == 0) {
            // Kruh
            g2d.setColor(shapeColor);
            g2d.fillOval(x, y, width, height);
        } else if (shapeType == 1) {
            // Obdélník
            g2d.setColor(shapeColor);
            g2d.fillRect(x, y, width, height);
        } else {
            // Mnohoúhelník
            int sides = random.nextInt(6) + 3; // Minimálně 3 strany, maximálně 8
            int[] xPoints = new int[sides];
            int[] yPoints = new int[sides];

            for (int i = 0; i < sides; i++) {
                // Náhodně generujeme vrcholy mnohoúhelníku
                xPoints[i] = random.nextInt(width) + x;
                yPoints[i] = random.nextInt(height) + y;
            }

            // Kreslení mnohoúhelníku
            g2d.setColor(shapeColor);
            g2d.fillPolygon(new Polygon(xPoints, yPoints, sides));
        }

        // Generace proužků
        for (int i = 0; i < 5; i++) {
            // Náhodná barva pro pruh
            int stripeRed = random.nextInt(256);
            int stripeGreen = random.nextInt(256);
            int stripeBlue = random.nextInt(256);
            Color stripeColor = new Color(stripeRed, stripeGreen, stripeBlue);
            g2d.setColor(stripeColor);

            // Náhodná pozice a šířka pruhu
            int stripeX = random.nextInt(blankCanvas.getWidth());
            int stripeY = random.nextInt(blankCanvas.getHeight());
            int stripeWidth = random.nextInt(20) + 20;

            // Kreslení pruhu
            g2d.fillRect(stripeX, stripeY, stripeWidth, blankCanvas.getHeight());
        }

        // Uvolnění zdrojů Graphics2D
        g2d.dispose();

        // Vrácení vygenerovaného obrázku
        return blankCanvas;
    }


    public void whatToShow(boolean currentImg){
        this.currentImg = currentImg;
        if (this.currentImg){
            this.imgView.setImage(toFXImage(this.currentIt));
        }else{
            this.imgView.setImage(toFXImage(this.stepback));
        }
    }



    public void filterHandler(int filterType){
        switch (filterType) {
            case 0 -> negative();
            case 1 -> pixelizer();
            case 2 -> identity();
            case 3 -> treshold();
            case 4 -> oldStyle();
            case 5 -> blackAndW();
            case 6 -> vinette();
            case 7 -> colorizer();
        }

    }

    private void colorizer() {
        Stage colorizerStage = new Stage();
        ImageView editedImage = new ImageView();
        editedImage.setPreserveRatio(true);
        editedImage.setFitWidth(300);
        editedImage.setFitHeight(170);
        ImageView imgHost = new ImageView();
        if(this.currentImg) {
            imgHost.setImage(toFXImage(this.currentIt));
            editedImage.setImage(toFXImage(this.currentIt));
        }else{
            imgHost.setImage(toFXImage(this.stepback));
            editedImage.setImage(toFXImage(this.stepback));
        }
        Slider sliderR = new Slider(-255,255,0);
        Slider sliderG = new Slider(-255, 255, 0);
        Slider sliderB = new Slider(-255, 255, 0);
        sliderR.setBlockIncrement(1);
        sliderG.setBlockIncrement(1);
        sliderB.setBlockIncrement(1);
        Label currVal = new Label("R: 0, G: 0, B: 0");
        Button accept = new Button("Done");
        CheckBox loopOver = new CheckBox("Loop over max/min values?");
        sliderR.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                sliderR.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            int offsetIntR = newValue.intValue();
            int offsetIntG = (int) sliderG.getValue();
            int offsetIntB = (int) sliderB.getValue();
            editedImage.setImage(previewColorize(imgHost, new int[]{offsetIntR, offsetIntG, offsetIntB}, loopOver.isSelected()));
            currVal.setText("R: " + offsetIntR + ", G: " + offsetIntG + ", B: " + offsetIntB);
        });
        sliderG.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                sliderG.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            int offsetIntR = (int) sliderR.getValue();
            int offsetIntG = newValue.intValue();
            int offsetIntB = (int) sliderB.getValue();
            editedImage.setImage(previewColorize(imgHost, new int[]{offsetIntR, offsetIntG, offsetIntB}, loopOver.isSelected()));
            currVal.setText("R: " + offsetIntR + ", G: " + offsetIntG + ", B: " + offsetIntB);
        });
        sliderB.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                sliderB.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            int offsetIntR = (int) sliderR.getValue();
            int offsetIntG = (int) sliderG.getValue();
            int offsetIntB = newValue.intValue();
            editedImage.setImage(previewColorize(imgHost, new int[]{offsetIntR, offsetIntG, offsetIntB}, loopOver.isSelected()));
            currVal.setText("R: " + offsetIntR + ", G: " + offsetIntG + ", B: " + offsetIntB);
        });
        accept.setOnAction(event -> {
            currVal.getText();
            applyColorize(editedImage);
            accept.getScene().getWindow().hide();
        });
        loopOver.setOnAction(event -> editedImage.setImage(previewColorize(imgHost, new int[]{(int) sliderR.getValue(), (int) sliderG.getValue(), (int) sliderB.getValue()}, loopOver.isSelected())));
        VBox start = new VBox();
        start.getChildren().addAll(editedImage, sliderR, sliderG, sliderB, currVal, loopOver, accept);
        colorizerStage.setTitle("Colorize filter");
        Scene scene = new Scene(start, 300,275);
        colorizerStage.setScene(scene);
        colorizerStage.show();
    }

    private Image previewColorize(ImageView imageView, int[] adjustments, boolean loopOver){
        Image imgToEdit = imageView.getImage();
        BufferedImage result = new BufferedImage((int) imgToEdit.getWidth(), (int) imgToEdit.getHeight(), BufferedImage.TYPE_INT_RGB);
        PixelReader pr = imgToEdit.getPixelReader();
        for (int x = 0; x < imgToEdit.getWidth(); x++) {
            for (int y = 0; y < imgToEdit.getHeight(); y++) {
                javafx.scene.paint.Color originalColor = pr.getColor(x, y);
                int currentR = (int) Math.floor(255 * originalColor.getRed());
                int currentG = (int) Math.floor(255 * originalColor.getGreen());
                int currentB = (int) Math.floor(255 * originalColor.getBlue());
                int correctedR = currentR + adjustments[0];
                int correctedG = currentG + adjustments[1];
                int correctedB = currentB + adjustments[2];
                if(loopOver) {
                    if (correctedR < 0) {
                        correctedR = 255 + correctedR;
                    }else if(255 < correctedR){
                        correctedR = correctedR - 255;
                    }
                    if (correctedG < 0) {
                        correctedG = 255 + correctedG;
                    }else if(255 < correctedG){
                        correctedG = correctedG - 255;
                    }
                    if (correctedB < 0) {
                        correctedB = 255 + correctedB;
                    }else if(255 < correctedB){
                        correctedB = correctedB - 255;
                    }
                }else{
                    if (correctedR < 0) {
                        correctedR = 0;
                    }else if(255 < correctedR){
                        correctedR = 255;
                    }
                    if (correctedG < 0) {
                        correctedG = 0;
                    }else if(255 < correctedG){
                        correctedG = 255;
                    }
                    if (correctedB < 0) {
                        correctedB = 0;
                    }else if(255 < correctedB){
                        correctedB = 255;
                    }
                }
                Color setterColor = new Color(correctedR, correctedG, correctedB);
                result.setRGB(x, y, setterColor.getRGB());
            }
        }

        return toFXImage(result);
    }

    private void applyColorize(ImageView imageView){
        Image imgToEdit = imageView.getImage();
        BufferedImage result = new BufferedImage((int) imgToEdit.getWidth(), (int) imgToEdit.getHeight(), BufferedImage.TYPE_INT_RGB);
        PixelReader reader = imgToEdit.getPixelReader();
        for (int x = 0; x < imgToEdit.getWidth(); x++) {
            for (int y = 0; y < imgToEdit.getHeight(); y++) {
                javafx.scene.paint.Color originalColor = reader.getColor(x,y);
                int currentB = (int) Math.floor(255 * originalColor.getBlue());
                int currentG = (int) Math.floor(255 * originalColor.getGreen());
                int currentR = (int) Math.floor(255 * originalColor.getRed());
                Color setter = new Color(currentR, currentG, currentB);
                result.setRGB(x,y,setter.getRGB());
            }
        }
        if(this.currentImg){
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    this.stepback.setRGB(x, y, (this.currentIt.getRGB(x, y)));
                }
            }
            this.currentIt = result;
        }else{
            this.currentIt = result;
        }
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void vinette() {

    }

    private void blackAndW() {
        if (this.currentImg) {
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    this.stepback.setRGB(x, y, (this.currentIt.getRGB(x, y)));
                }
            }
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    Color originalColor = new Color(this.currentIt.getRGB(x,y));
                    int currentB = originalColor.getBlue();
                    int currentG = originalColor.getGreen();
                    int currentR = originalColor.getRed();
                    Color newColor;
                    if (currentR > currentG && currentR > currentB){
                        newColor = new Color(currentR, currentR, currentR);
                    }else if (currentG > currentR && currentG > currentB){
                        newColor = new Color(currentG, currentG, currentG);
                    }else{
                        newColor = new Color(currentB, currentB, currentB);
                    }
                    this.currentIt.setRGB(x, y, newColor.getRGB());
                }
            }
        } else {
            for (int x = 0; x < this.stepback.getWidth(); x++) {
                for (int y = 0; y < this.stepback.getHeight(); y++) {
                    Color originalColor = new Color(this.stepback.getRGB(x,y));
                    int currentB = originalColor.getBlue();
                    int currentG = originalColor.getGreen();
                    int currentR = originalColor.getRed();
                    Color newColor;
                    if (currentR > currentG && currentR > currentB){
                         newColor = new Color(currentR, currentR, currentR);
                    }else if (currentG > currentR && currentG > currentB){
                        newColor = new Color(currentG, currentG, currentG);
                    }else{
                        newColor = new Color(currentB, currentB, currentB);
                    }
                    this.currentIt.setRGB(x, y, newColor.getRGB());
                }
            }
        }
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void oldStyle() {
        if (this.currentImg) {
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    this.stepback.setRGB(x, y, (this.currentIt.getRGB(x, y)));
                }
            }
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    Color originalColor = new Color(this.currentIt.getRGB(x,y));
                    int currentB = originalColor.getBlue();
                    int currentG = originalColor.getGreen();
                    int currentR = originalColor.getRed();
                    Color newColor;
                    if (currentR > currentG && currentR > currentB){
                        newColor = new Color(currentR, currentR, (int)(currentR*0.80));
                    }else if (currentG > currentR && currentG > currentB){
                        newColor = new Color(currentG, currentG, (int)(currentG*0.80));
                    }else{
                        newColor = new Color(currentB, currentB, (int)(currentB*0.80));
                    }
                    this.currentIt.setRGB(x, y, newColor.getRGB());
                }
            }
        } else {
            for (int x = 0; x < this.stepback.getWidth(); x++) {
                for (int y = 0; y < this.stepback.getHeight(); y++) {
                    Color originalColor = new Color(this.stepback.getRGB(x,y));
                    int currentB = originalColor.getBlue();
                    int currentG = originalColor.getGreen();
                    int currentR = originalColor.getRed();
                    Color newColor;
                    if (currentR > currentG && currentR > currentB){
                        newColor = new Color(currentR, currentR, (int)(currentR*0.80));
                    }else if (currentG > currentR && currentG > currentB){
                        newColor = new Color(currentG, currentG, (int)(currentG*0.80));
                    }else{
                        newColor = new Color(currentB, currentB, (int)(currentB*0.80));
                    }
                    this.currentIt.setRGB(x, y, newColor.getRGB());
                }
            }
        }
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void treshold() {
        Stage tresholdStage = new Stage();
        ImageView editedImage = new ImageView();
        editedImage.setPreserveRatio(true);
        editedImage.setFitWidth(300);
        editedImage.setFitHeight(170);
        ImageView imgHost = new ImageView();
        if(this.currentImg) {
            imgHost.setImage(toFXImage(this.currentIt));
            editedImage.setImage(toFXImage(this.currentIt));
        }else{
            imgHost.setImage(toFXImage(this.stepback));
            editedImage.setImage(toFXImage(this.stepback));
        }
        Slider slider = new Slider(0,255,0);
        slider.setBlockIncrement(1);
        Label currVal = new Label("0");
        Button accept = new Button("Done");
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                slider.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            editedImage.setImage(previewTreshold(imgHost, newValue.intValue()));
            currVal.setText(newValue.toString());
        });
        accept.setOnAction(event -> {
            applyTreshold(editedImage);
            accept.getScene().getWindow().hide();
        });
        VBox start = new VBox();
        start.getChildren().addAll(editedImage, slider, currVal, accept);
        tresholdStage.setTitle("Treshold filter");
        Scene scene = new Scene(start, 300,230);
        tresholdStage.setScene(scene);
        tresholdStage.show();
    }
    private Image previewTreshold(ImageView imageView, int treshold){
        Image imgToEdit = imageView.getImage();
        BufferedImage result = new BufferedImage((int) imgToEdit.getWidth(), (int) imgToEdit.getHeight(), BufferedImage.TYPE_INT_RGB);
        PixelReader reader = imgToEdit.getPixelReader();
        for (int x = 0; x < imgToEdit.getWidth(); x++) {
            for (int y = 0; y < imgToEdit.getHeight(); y++) {
                javafx.scene.paint.Color originalColor = reader.getColor(x,y);
                int currentB = (int) Math.floor(255 * originalColor.getBlue());
                int currentG = (int) Math.floor(255 * originalColor.getGreen());
                int currentR = (int) Math.floor(255 * originalColor.getRed());
                Color white = new Color(255,255,255);
                Color black = new Color(0,0,0);
                int[] theThreeColors = {currentB, currentG, currentR};
                Arrays.sort(theThreeColors);
                if(theThreeColors[2] > treshold){
                    result.setRGB(x,y,white.getRGB());
                }else{
                    result.setRGB(x,y,black.getRGB());
                }
            }
        }
        return toFXImage(result);
    }
    private void applyTreshold(ImageView imgToApply){
        Image imgToEdit = imgToApply.getImage();
        BufferedImage result = new BufferedImage((int) imgToEdit.getWidth(), (int) imgToEdit.getHeight(), BufferedImage.TYPE_INT_RGB);
        PixelReader reader = imgToEdit.getPixelReader();
        for (int x = 0; x < imgToEdit.getWidth(); x++) {
            for (int y = 0; y < imgToEdit.getHeight(); y++) {
                javafx.scene.paint.Color originalColor = reader.getColor(x,y);
                int currentB = (int) Math.floor(255 * originalColor.getBlue());
                int currentG = (int) Math.floor(255 * originalColor.getGreen());
                int currentR = (int) Math.floor(255 * originalColor.getRed());
                Color setter = new Color(currentR, currentG, currentB);
                result.setRGB(x,y,setter.getRGB());
            }
        }
        if(this.currentImg){
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    this.stepback.setRGB(x, y, (this.currentIt.getRGB(x, y)));
                }
            }
            this.currentIt = result;
        }else{
            this.currentIt = result;
        }
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void negative() {
        if(this.currentImg) {
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    this.stepback.setRGB(x, y, (this.currentIt.getRGB(x, y)));
                }
            }
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    Color originColor = new Color(this.currentIt.getRGB(x,y));
                    Color replacementColor = new Color(255 - originColor.getRed(), 255 - originColor.getGreen(), 255 - originColor.getBlue());
                    this.currentIt.setRGB(x, y, replacementColor.getRGB());
                }
            }
        }else{
            for (int x = 0; x < this.stepback.getWidth(); x++) {
                for (int y = 0; y < this.stepback.getHeight(); y++) {
                    Color originColor = new Color(this.stepback.getRGB(x,y));
                    Color replacementColor = new Color(255 - originColor.getRed(), 255 - originColor.getGreen(), 255 - originColor.getBlue());
                    this.currentIt.setRGB(x, y, replacementColor.getRGB());
                }
            }
        }
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void pixelizer() { // toto asi proste prepisu z pdfka
        Stage pixeliserStage = new Stage();
        Slider slider = new Slider(0,100,1);
        slider.setBlockIncrement(1);
        Label currVal = new Label("0");
        Button accept = new Button("Done");
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(Double.compare(Math.floor((double)newValue), (double) newValue) != 0){
                slider.setValue(Math.floor((double)newValue));
                newValue = Math.floor((double)newValue);
            }
            currVal.setText(newValue.toString());
        });
        accept.setOnAction(event -> {
            int pixeliserValue = (int)slider.getValue();
            applyPixelizer(pixeliserValue);
            accept.getScene().getWindow().hide();
        });
        VBox start = new VBox();
        start.getChildren().addAll(slider, currVal, accept);
        pixeliserStage.setTitle("Pixeliser filter");
        Scene scene = new Scene(start, 300,230);
        pixeliserStage.setScene(scene);
        pixeliserStage.show();

    }
    private void applyPixelizer(int intensity){
        BufferedImage result = new BufferedImage(this.currentIt.getWidth(), this.currentIt.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (this.currentImg) {
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    this.stepback.setRGB(x, y, (this.currentIt.getRGB(x, y)));
                }
            }
            for (int x = 0; x < this.currentIt.getWidth();x = x + intensity * 2){
                for (int y = 0; y < this.currentIt.getHeight();y = y + intensity * 2){
                    for (int a = x - intensity; a < x + intensity; a++){
                        for (int b = y - intensity; b < y + intensity; b++){
                            int getA = Math.min(this.currentIt.getWidth()-1, Math.max(0, a));
                            int getB = Math.min(this.currentIt.getHeight()-1, Math.max(0, b));
                            Color c = new Color(this.currentIt.getRGB(x, y));
                            result.setRGB(getA, getB, c.getRGB());
                        }
                    }
                }
            }
        } else {
            for (int x = 0; x < this.stepback.getWidth();x = x + intensity * 2){
                for (int y = 0; y < this.stepback.getHeight();y = y + intensity * 2){
                    for (int a = x - intensity; a < x + intensity; a++){
                        for (int b = y - intensity; b < y + intensity; b++){
                            int getA = Math.min(this.stepback.getWidth()-1, Math.max(0, a));
                            int getB = Math.min(this.stepback.getHeight()-1, Math.max(0, b));
                            Color c = new Color(this.stepback.getRGB(x, y));
                            result.setRGB(getA, getB, c.getRGB());
                        }
                    }
                }
            }
        }
        this.currentIt = result;
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void identity() {
    }



    private Image toFXImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public void applyMatrix(int[][] matrix) {
        if(this.currentImg){
            BufferedImage result = new BufferedImage(this.currentIt.getWidth(), this.currentIt.getHeight(), this.currentIt.getType());
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int matrixX = 0; matrixX < matrix.length; matrixX++) {
                        for (int matrixY = 0; matrixY < matrix[0].length; matrixY++) {
                            if(matrix[matrixX][matrixY] != 0){
                                int workerPixelX = x+matrixX-matrix.length/2;
                                int workerPixelY = y+matrixY-matrix[0].length/2;
                                if (workerPixelX < 0) workerPixelX = 0;
                                if (workerPixelY < 0) workerPixelY = 0;
                                if (workerPixelX > this.currentIt.getWidth()) workerPixelX = this.currentIt.getWidth()-1;
                                if (workerPixelY > this.currentIt.getHeight()) workerPixelY = this.currentIt.getHeight()-1;
                                Color originalCol = new Color(this.currentIt.getRGB(workerPixelX, workerPixelY));
                                r += originalCol.getRed()*matrix[matrixX][matrixY];
                                g += originalCol.getGreen()*matrix[matrixX][matrixY];
                                b += originalCol.getBlue()*matrix[matrixX][matrixY];
                            }
                        }
                    }
                    if (r < 0) r = 0;
                    if (g < 0) g = 0;
                    if (b < 0) b = 0;
                    if (r > 255) r = 255;
                    if (g > 255) g = 255;
                    if (b > 255) b = 255;
                    Color resultCol = new Color(r,g,b);
                    result.setRGB(x,y,resultCol.getRGB());
                }
            }
            this.stepback = this.currentIt;
            this.currentIt = result;
            this.imgView.setImage(toFXImage(this.currentIt));
            this.modifiedRadio.fire();
        }else{
            BufferedImage result = new BufferedImage(this.stepback.getWidth(), this.stepback.getHeight(), this.stepback.getType());
            for (int x = 0; x < this.stepback.getWidth()-1; x++) {
                for (int y = 0; y < this.stepback.getHeight()-1; y++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int matrixX = 0; matrixX < matrix.length; matrixX++) {
                        for (int matrixY = 0; matrixY < matrix[0].length; matrixY++) {
                            if(matrix[matrixX][matrixY] != 0){
                                int workerPixelX = x+matrixX-matrix.length/2;
                                int workerPixelY = y+matrixY-matrix[0].length/2;
                                if (workerPixelX < 0){ workerPixelX = 0;}
                                if (workerPixelY < 0){ workerPixelY = 0;}
                                if (workerPixelX > this.stepback.getWidth()){ workerPixelX = this.stepback.getWidth()-1;}
                                if (workerPixelY > this.stepback.getHeight()){ workerPixelY = this.stepback.getHeight()-1;}
                                Color originalCol = new Color(this.stepback.getRGB(workerPixelX, workerPixelY));
                                r += originalCol.getRed()*matrix[matrixX][matrixY];
                                g += originalCol.getGreen()*matrix[matrixX][matrixY];
                                b += originalCol.getBlue()*matrix[matrixX][matrixY];
                            }
                        }
                    }
                    if (r < 0) r = 0;
                    if (g < 0) g = 0;
                    if (b < 0) b = 0;
                    if (r > 255) r = 255;
                    if (g > 255) g = 255;
                    if (b > 255) b = 255;
                    Color resultCol = new Color(r,g,b);
                    result.setRGB(x,y,resultCol.getRGB());
                }
            }
            this.currentIt = result;
            this.imgView.setImage(toFXImage(this.currentIt));
            this.modifiedRadio.fire();
        }
    }
}
