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
    }
    public ImageWorker(ImageView mainimgview){
        this.currentIt = genImg();
        this.stepback = this.currentIt;
        this.imgView = mainimgview;
        this.imgView.setImage(toFXImage());
    }

    private BufferedImage genImg() {
        return null;
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
