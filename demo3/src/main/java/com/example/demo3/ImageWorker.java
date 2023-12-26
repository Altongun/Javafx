package com.example.demo3;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class ImageWorker {
    BufferedImage currentIt;
    BufferedImage stepback;
    ImageView imgView;

    public ImageWorker(File origin, ImageView mainimgview){
        try {
            this.currentIt = ImageIO.read(origin);
        }catch (Exception e){
            System.out.print("uhhh..." + e);
        }
        this.stepback = this.currentIt;
        this.imgView = mainimgview;
        this.imgView.setImage(toFXImage());
        this.imgView.setPreserveRatio(true);
        this.imgView.setFitHeight(this.imgView.getScene().getHeight()/2.0f);
        this.imgView.setFitWidth(this.imgView.getScene().getWidth()/2.0f); //první setup výšky a šířky
        this.imgView.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.imgView.setFitHeight(newVal.floatValue()/2.0f));
        this.imgView.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.imgView.setFitWidth(newVal.floatValue()/2.0f); // odposluchače na změnu výšky a šířky, upraví výšku/šířku obrázku
        });
    }
    public ImageWorker(ImageView mainimgview){
        this.currentIt = genImg();
        this.stepback = this.currentIt;
        this.imgView = mainimgview;
        this.imgView.setImage(toFXImage());
        this.imgView.setPreserveRatio(true);
        this.imgView.setFitHeight(this.imgView.getScene().getHeight()/2.0f);
        this.imgView.setFitWidth(this.imgView.getScene().getWidth()/2.0f); //první setup výšky a šířky
        this.imgView.getScene().heightProperty().addListener((obs, oldVal, newVal) -> this.imgView.setFitHeight(newVal.floatValue()/2.0f));
        this.imgView.getScene().widthProperty().addListener((obs, oldVal, newVal) -> {
            this.imgView.setFitWidth(newVal.floatValue()/2.0f); // odposluchače na změnu výšky a šířky, upraví výšku/šířku obrázku
        });
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

    private Image toFXImage() {
        WritableImage wr = null;
        if (this.currentIt != null) {
            wr = new WritableImage(this.currentIt.getWidth(), this.currentIt.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < this.currentIt.getWidth(); x++) {
                for (int y = 0; y < this.currentIt.getHeight(); y++) {
                    pw.setArgb(x, y, this.currentIt.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }
}
