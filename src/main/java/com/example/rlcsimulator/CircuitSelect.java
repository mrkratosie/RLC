package com.example.rlcsimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class CircuitSelect {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private RadioButton radioButton1, radioButton2, radioButton3;

    public void openScene(ActionEvent event) throws IOException{
        if(radioButton1.isSelected()){
            root = FXMLLoader.load(getClass().getResource("RLC.fxml"));
        } else if(radioButton2.isSelected()){
            root = FXMLLoader.load(getClass().getResource("RL.fxml"));
        }else if(radioButton3.isSelected()){
            root = FXMLLoader.load(getClass().getResource("RC.fxml"));
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
