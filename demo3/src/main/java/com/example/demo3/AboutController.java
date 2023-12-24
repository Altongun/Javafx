package com.example.demo3;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutController {
    @FXML
    private Button closeButton;
    @FXML
    protected void closePopup() {
        closeButton.getScene().getWindow().hide();
    }
}
