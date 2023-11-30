package com.example.demo3;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

public class HelloController {
    @FXML
    private Menu Exit;

    @FXML
    protected void exitProgram() {
        System.out.println("Hello");
        System.exit(0);
    }
}