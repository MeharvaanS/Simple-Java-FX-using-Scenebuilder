package com.example;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class HelpController implements Initializable {

    @FXML
    private Button exit;

    @FXML
    private Button home;

    @FXML
    private TextArea helpText;

    //Method to Exit//

    @FXML
    void exitMethod(ActionEvent event) {
        Alert alert = new Alert( AlertType.CONFIRMATION);
        alert.setTitle(" Close Application ");
        alert.setHeaderText(" CLOSE ");
        alert.setContentText( " Do you want to close the Application?");
    
        Optional<ButtonType> btn =  alert.showAndWait();
     
      
        if( btn.isPresent() && btn.get() == ButtonType.OK){
            Platform.exit();  
        }
    }

    //Method to go to home screen//

    @FXML
    void homeMethod(ActionEvent event) throws IOException {
        App.setRoot("Home");
    }

    //Hover for exit button//

    @FXML
    void exitHover(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            exit.setStyle("-fx-text-fill:red;");
        }else{
            exit.setStyle("-fx-text-fill: black;");
        }
    }

    //Hover for home button//

    @FXML
    void homeHover(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            home.setStyle("-fx-text-fill:blue;");
        }else{
            home.setStyle("-fx-text-fill: black;");
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // Adding animations//

        FadeTransition ft = new FadeTransition(Duration.millis(2000), helpText );
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
        
    }

}
