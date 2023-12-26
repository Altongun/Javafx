package com.example.demo3;

import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;



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
        BufferedImage blankCanvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < blankCanvas.getWidth(); x++) {
            for (int y = 0; y < blankCanvas.getHeight(); y++) {
                if (y < 200){
                    blankCanvas.setRGB(x, y, 255-(x/4));
                }else if (y<400){
                    blankCanvas.setRGB(x, y, (int) (Math.floor(255-(float)(x/4))*256));
                }else{
                    blankCanvas.setRGB(x,y, (255-(x/4))*65536);
                }
            }
        }
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
        for (int x = 0; x < this.currentIt.getWidth(); x++) {
            for (int y = 0; y < this.currentIt.getHeight(); y++) {
                int currentRGB = this.currentIt.getRGB(x,y);
                int currentB = (int)Math.floor(currentRGB/65536f);
                int restB = currentRGB - (currentB*65536);
                int currentG = (int)Math.floor(restB/256f);
                int currentR = restB - (currentG*256);
                int alpha = (int) Math.floor((currentR+currentG+currentB)/3f);
                this.currentIt.setRGB(x, y, alpha*65536 + alpha*256 + alpha);
            }
        }
        this.imgView.setImage(toFXImage(this.currentIt));
        this.modifiedRadio.fire();
    }

    private void oldStyle() {

    }

    private void treshold() {

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
                    this.currentIt.setRGB(x, y, (255 - this.currentIt.getRGB(x, y)));
                }
            }
        }else{
            for (int x = 0; x < this.stepback.getWidth(); x++) {
                for (int y = 0; y < this.stepback.getHeight(); y++) {
                    this.currentIt.setRGB(x, y, (255 - this.stepback.getRGB(x, y)));
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
