package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

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

public class LoginController {

    @FXML
    private Label error;

    @FXML
    private TextField t1;

    @FXML
    private PasswordField t2;

    @FXML
    private Button log;

    @FXML
    private Button exit;

    @FXML
    private Button create;
    
    //Method to open the home page//

    @FXML
    void login(ActionEvent event)throws Exception {
        File file = new File("passwordData.txt");
        Scanner scn = new Scanner(file);
        String head = scn.nextLine(); 

        if(t1.getText().equals("") && t2.getText().equals("")){
            error.setText("ERROR: ENTER " + head);
        }else if(t1.getText().equals("")){
            error.setText("ERROR: ENTER USERNAME ");
        }else if(t2.getText().equals("")){
            error.setText("ERROR: ENTER PASSWORD ");
        }else{
            while(scn.hasNextLine()){
                try{
                    if((scn.nextLine()).equals(t1.getText().trim() + "," + t2.getText().trim())){    
                        App.setRoot("Home");
                        error.setText("");
                        break;    
                    }else{
                        continue;
                    } 
                }catch(Exception e ){
                } 
            }
        }
        scn.close();
    }

    //Creating hover for exit button//

    @FXML
    void exitHover(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            exit.setStyle("-fx-text-fill:red;");
        }else{
            exit.setStyle("-fx-text-fill: black;");
        }
    }

    //Exit method to exit the Application//

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

    //Method to create a account//

    @FXML
    void createMethod(ActionEvent event) throws IOException {
        App.setRoot("Create");
    }
}





