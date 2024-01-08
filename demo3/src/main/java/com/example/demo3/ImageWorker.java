package com.example.demo3;

import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;


public class ImageWorker {
    RadioButton modifiedRadio;
    BufferedImage currentIt;
    BufferedImage stepback;
    ImageView imgView;
    RadioButton originalRadio;
    boolean currentImg;



    public ImageWorker(File origin, ImageView mainimgview, RadioButton originalRadio, RadioButton modifiedRadio){
        try {
            this.currentIt = ImageIO.read(origin);
            this.stepback = ImageIO.read(origin);
        }catch (Exception e){
            System.out.print("uhhh..." + e);
        }
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
    }



    private BufferedImage genImg() {
        // Vytvoření prázdného plátna s určenou šířkou a výškou
        BufferedImage blankCanvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // Grafický kontext pro kreslení na plátno
        Graphics2D g2d = blankCanvas.createGraphics();
        Random random = new Random();

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
            default -> {
            }
        }

    }

    private void colorizer() {
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

    private void pixelizer() {
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
}
