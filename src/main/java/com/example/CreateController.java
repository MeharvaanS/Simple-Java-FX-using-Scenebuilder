package com.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class CreateController {

    @FXML
    private Button create;

    @FXML
    private Label error;

    @FXML
    private Button exit;

    @FXML
    private TextField t1;

    @FXML
    private PasswordField t2;

    @FXML
    private Button sign;

    @FXML
    void createMethod(ActionEvent event) throws IOException {
        try{
            if(t1.getText().equals("") && t2.getText().equals("")){
                error.setStyle("-fx-text-fill:red;");
                error.setText("ERROR: ENTER USERNAME AND PASSWORD");
            }else if(t1.getText().equals("")){
                error.setStyle("-fx-text-fill:red;");
                error.setText("ERROR: ENTER USERNAME ");
            }else if(t2.getText().equals("")){
                error.setStyle("-fx-text-fill:red;");
                error.setText("ERROR: ENTER PASSWORD ");
            }else{
                error.setStyle("-fx-text-fill:green;");
                File file2 = new File("passwordData.txt");
                try (PrintWriter writer = new PrintWriter(new FileWriter(file2, true))) {
                    writer.write("\n" + t1.getText().trim() + "," + t2.getText().trim());
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    error.setText("THE ACCOUNT WAS CREATED");
                }
            }
        }catch(Exception e){
            error.setText("THE ACCOUNT WAS NOT CREATED !!");
        }
    }

    @FXML
    void exitHover(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            exit.setStyle("-fx-text-fill:red;");
        }else{
            exit.setStyle("-fx-text-fill: black;");
        }
    }

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

    @FXML
    void signAction(ActionEvent event) throws IOException {
        App.setRoot("Login");
    }

    @FXML
    void signHover(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            sign.setStyle("-fx-text-fill:blue;");
        }else{
            sign.setStyle("-fx-text-fill: black;");
        }
    }
}
