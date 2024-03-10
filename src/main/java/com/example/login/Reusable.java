package com.example.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Reusable extends Pane {
    @FXML private Label labelRe;
    @FXML private Button buttonRe;
    public Reusable() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reusableAdvisement.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void setLabelText(String labelText) {
        this.labelRe.setText(labelText);
    }
    public String getLabelText() {
        return this.labelRe.getText();
    }

    public void setButtonText(String buttonText) {
        this.buttonRe.setText(buttonText);
    }

    public String getButtonText() {
        return this.buttonRe.getText();
    }

    public void setOnAction(EventHandler<ActionEvent> handler) {
        this.buttonRe.setOnAction(handler);
    }

    public EventHandler<ActionEvent> getOnAction() {
        return this.buttonRe.getOnAction();
    }

}
