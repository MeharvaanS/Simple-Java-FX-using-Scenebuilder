package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class HomeController implements Initializable {

    //Declaring fields used to make the application//

    @FXML
    private Button clear;

    @FXML
    private TextField searchResult;

    @FXML
    private Button add;

    @FXML
    private Button change;

    @FXML
    private Button exit;

    @FXML
    private Button del;

    @FXML
    private Button disp;

    @FXML
    private Label error;

    @FXML
    private Button mod;

    @FXML
    private Label logo;

    @FXML
    private Button save;

    @FXML
    private TextField search;

    @FXML
    private Button searchButton;

    @FXML
    private TextField t1;

    @FXML
    private Button help;

    @FXML
    private ComboBox<String> t5;

    @FXML
    private TextField t2;

    @FXML
    private DatePicker t3;

    @FXML
    private TextField t4;

    @FXML
    private Button refresh;

    @FXML
    private TextField t6;

    @FXML
    private TextField t7;

    @FXML
    private TextField t8;

    @FXML
    private ListView<String> list;

    @FXML
    private Button load;

    static int i = 0;
    boolean loading = false;

    //creating Scanner method to scan the text file//

    Scanner scn = null; 
    ArrayList<String> moviesArray = new ArrayList<String>();
    File file1 = new File("MovieList.txt");

    //Array of loaded movies//
    ArrayList<String> dictionary = new ArrayList<>();

    //Object of class Movie//
    Movie m = new Movie();
    
    //Method to clear the text inside fields//

    public void clear(){
        t1.clear();
        t2.clear();
        t3.getEditor().clear();
        t4.clear();
        t5.setValue("");
        t6.clear();
        t7.clear();
        t8.clear();
    }
     
    /*This method runs after add button has been pressed
    it adds the movie record to the list view */

    @FXML
    void addMethod(ActionEvent event){
        i++;

        try{
            error.setStyle("-fx-text-fill:white");
            if(t1.getText().length() == 0 || t2.getText().length() == 0 || 
                t4.getText().length() == 0 || t3.getEditor().getText().length() == 0 
                || t6.getText().length() == 0 || t7.getText().length() == 0 || t8.getText().length() == 0 ){   

                error.setText("!ERROR Put the missing data");
                error.setStyle("-fx-text-fill:red");
            }
            else{
                //Validation of data inside input fields using regex//

                String ratingPattern = "[1-5]";
                Pattern p = Pattern.compile(ratingPattern);
                Matcher m = p.matcher(t6.getText());

                String isbnPattern = "[0-9]*";
                Pattern p1 = Pattern.compile(isbnPattern);
                Matcher m1 = p1.matcher(t2.getText());

                String datePattern = ("^\\d{4}-\\d{2}-\\d{2}$");
                Pattern p2 = Pattern.compile(datePattern);
                Matcher m2 = p2.matcher(t3.getEditor().getText());

                String timePattern = "[0-9]*";
                Pattern p3 = Pattern.compile(timePattern);
                Matcher m3 = p3.matcher(t7.getText());

                String pricePattern = "[0-9]*";
                Pattern p4 = Pattern.compile(pricePattern);
                Matcher m4 = p4.matcher(t8.getText());



                if(!m.matches()){
                    error.setText("ERROR! ENTER RATING 1-5");
                    error.setStyle("-fx-text-fill:red");
                }else if(!m1.matches()){
                    error.setText("ERROR! ISBN SHOULD BE NUMERIC");
                    error.setStyle("-fx-text-fill:red");
                }else if(!m2.matches()){
                    error.setText("ERROR! INCORRECT DATE FORMAT");
                    error.setStyle("-fx-text-fill:red");
                }else if(!m3.matches()){
                    error.setText("ERROR! TIME SHOULD BE NUMERIC");
                    error.setStyle("-fx-text-fill:red");
                } else if(!m4.matches()){
                    error.setText("ERROR! PRICE SHOULD BE NUMERIC");
                    error.setStyle("-fx-text-fill:red");
                }else{
                    error.setStyle("-fx-text-fill:white");

                    String movie = t1.getText()+ "    " + t2.getText() +
                    "    " +  t3.getEditor().getText() + "    " + t4.getText() +
                    "    " + t5.getValue().toString() + "    " + t6.getText() + 
                    "    " + t7.getText() + "    " + t8.getText() + "\n";

                    moviesArray.add(movie);
                    list.getItems().add(movie);

                    clear(); 
                    error.setText("STATUS: MOVIES HAVE BEEN ADDED");
                }
            }     
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //to clear the textFields//

    @FXML
    void refreshMethod(ActionEvent event) {
        error.setStyle("-fx-text-fill:white");
        error.setText("STATUS: REFRESHED");
        clear();
    }

    //Mehod used to clear the search result//

    @FXML
    void clearMethod(ActionEvent event) {
        error.setStyle("-fx-text-fill:white");
        searchResult.setText(null);
    }

    //Mehod used to delete the selected movie record//
    
    @FXML
    void deleteMethod(ActionEvent event)throws Exception{
        try{
            error.setStyle("-fx-text-fill:white");
            int selectedMovie = list.getSelectionModel().getSelectedIndex();
            list.getItems().remove(selectedMovie);

            error.setText("STATUS: SELECTED MOVIES IS DELETED");
        }catch(Exception e){
            error.setStyle("-fx-text-fill:red");
            error.setText("ERROR! NO MOVIES TO DELETE");
        }
    }

    //Mehod used to search the movie record//
    
    @FXML
    void search(ActionEvent event) {
        try{
            error.setStyle("-fx-text-fill:white");
            for(int c =0; c < i; c++){
                String[] searchArr = m.getArray(moviesArray.get(c));

                if(searchArr[0].equals(search.getText())){
                    searchResult.setText(moviesArray.get(c));
                    error.setText("STATUS: DISPLAYING THE SEARCH");
                }
            }for(int c =0; c < dictionary.size(); c++){
                String[] searchLoadArr = m.getArray(dictionary.get(c));

                if(searchLoadArr[0].equals(search.getText())){
                    searchResult.setText(dictionary.get(c));
                    error.setText("STATUS: DISPLAYING THE SEARCH");
                }
            } 
            search.clear(); 
        }catch(Exception e){
            error.setStyle("-fx-text-fill:red");
            error.setText("Sorry! NO SUCH RECORD ADDED");
        }
    }

    //Mehod used to save the displayed movie records to text file//

    @FXML
    void saveMethod(ActionEvent event) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(new FileWriter(file1, true))){  
            error.setStyle("-fx-text-fill:white");
            if(i == 0){
                error.setStyle("-fx-text-fill:red");
                error.setText("STATUS: NO MOVIES WERE ADDED TO SAVE"); 
            }else{
                if(loading = true){
                    writer.write("\n");
                }
                for(int z=0; z<i; z++){  
                    writer.write(moviesArray.get(z));
                } 
            } 
            error.setText("STATUS: SAVING DISPLAYED MOVIES");
        }catch (Exception e) { 
            error.setStyle("-fx-text-fill:red");
            error.setText("STATUS: NO MOVIES WERE SAVED !!"); 
        }
    }

    //Method to convert List item to string//

    private String itemToString(String s){
       String l = s.substring(1, s.length()-1);
        return l;
    }

    //Mehod used to modify selected movie record in list view//

    @FXML
    void modMethod(ActionEvent event) {
        try{
            error.setStyle("-fx-text-fill:white");
            error.setText("");
            String a = itemToString((list.getSelectionModel().getSelectedItems()).toString());

            t1.setText(m.getArray(a)[0]);
            t2.setText(m.getArray(a)[1]);
            t3.getEditor().setText(m.getArray(a)[2]);
            t4.setText(m.getArray(a)[3]);
            t6.setText(m.getArray(a)[5]);
            t7.setText(m.getArray(a)[6]);
            t8.setText(m.getArray(a)[7]);

            int selectedMovie = list.getSelectionModel().getSelectedIndex();
            list.getItems().remove(selectedMovie);
        }catch(Exception e){
            error.setStyle("-fx-text-fill:red");
            error.setText("NO MOVIES WERE MODIFIED");
        }
    }
    
    //Mehod used to load the saved movie records//

    @FXML
    void loadMethod(ActionEvent event) throws FileNotFoundException {
        try{
            error.setStyle("-fx-text-fill:white");
            loading = true;
            int l = 0;
            scn = new Scanner(file1);
            BufferedReader input = new BufferedReader(new FileReader("MovieList.txt"));
            for (String line = input.readLine(); line != null; line = input.readLine()){
                dictionary.add(line);
            }
            while(scn.hasNextLine()){
                list.getItems().add(dictionary.get(l));
                l++;
            }
            error.setText("STATUS: MOVIES UPLOADED");
            input.close(); 
        }catch(Exception e){
            error.setText("STATUS: MOVIES UPLOADED");
        }
    }

    //Mehod used to exit the application//

    @FXML
    void exit(ActionEvent event) {
        Alert alert = new Alert( AlertType.CONFIRMATION);
        alert.setTitle(" Close Application ");
        alert.setHeaderText(" CLOSE ");
        alert.setContentText( " Do you want to close the Application?");
    
        Optional<ButtonType> btn =  alert.showAndWait();
     
        if( btn.isPresent() && btn.get() == ButtonType.OK){
            Platform.exit();  
        }
    }

    //Setting root to the help window//

    @FXML
    void helpMethod(ActionEvent event) {
        try{
            App.setRoot("Help");
        }catch(Exception e ){
            System.out.println(e.getMessage());
        }
    }

    //Following methods are used to add tooltips to fields and buttons//

    @FXML
    void hoverT1(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter movie name here");
        t1.setTooltip(tooltip);
    }
    @FXML
    void hoverT2(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter ISBN number here\n" + 
        "(Only numeric values are accepted)");
        t2.setTooltip(tooltip);
    }
    @FXML
    void hoverT3(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter release date here ");
        t3.setTooltip(tooltip);
    }
    @FXML
    void hoverT4(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter movie release location here");
        t4.setTooltip(tooltip);
    }
    @FXML
    void hoverT5(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Choose movie genre");
        t5.setTooltip(tooltip);
    }
    @FXML
    void hoverT6(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter rating\n" + "Only (1-5) values are accepted");
        t6.setTooltip(tooltip);
    }
    @FXML
    void hoverT7(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter movie duration in minutes here");
        t7.setTooltip(tooltip);
    }
    @FXML
    void hoverT8(MouseEvent event) {
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter ticket price in $ here");
        t8.setTooltip(tooltip);
    }

    //Method for applying effect on button text//
    
    void hoverEffect(MouseEvent event, Button b){
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            b.setStyle("-fx-text-fill:white;-fx-background-color: black;");
        }else{
            b.setStyle("-fx-text-fill:#ababab;-fx-background-color: black;");
        }
    }

    @FXML
    void hoverAdd(MouseEvent event) {
        hoverEffect(event, add);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click to add new movie record");
        add.setTooltip(tooltip);
    }

    @FXML
    void hoverDelete(MouseEvent event) {
        hoverEffect(event, del);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click to delete the selected movie record\n" +
        "from display screen");
        del.setTooltip(tooltip);
    }

    @FXML
    void hoverExit(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            exit.setStyle("-fx-text-fill:red;-fx-background-color: black;");
        }else{
            exit.setStyle("-fx-text-fill: #ababab;-fx-background-color: black;");
        }
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click here to exit the app");
        exit.setTooltip(tooltip);
    }

    @FXML
    void hoverRefresh(MouseEvent event) {
        hoverEffect(event, refresh);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click here to refresh the data fields");
        refresh.setTooltip(tooltip);
    }

    @FXML
    void hoverLoad(MouseEvent event) {
        hoverEffect(event, load);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click here to load the saved movie records");
        load.setTooltip(tooltip);
    }

    @FXML
    void hoverModify(MouseEvent event) {
        hoverEffect(event, mod);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click here to modify the selected movie record");
        mod.setTooltip(tooltip);
    }

    @FXML
    void hoverSave(MouseEvent event) {
        hoverEffect(event, save);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Click here to save the displayed movie records");
        save.setTooltip(tooltip);
    }

    @FXML
    void hoverSearch(MouseEvent event) {
        hoverEffect(event, searchButton);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Enter the movie name to be searched then click here");
        searchButton.setTooltip(tooltip);
    }

    @FXML
    void clearHover(MouseEvent event) {
        if(event.getEventType().equals(MouseEvent.MOUSE_ENTERED)){
            clear.setStyle("-fx-text-fill:white;-fx-background-color: black;-fx-border-color: white;");
        }else{
            clear.setStyle("-fx-text-fill:#ababab;-fx-background-color: black;-fx-border-color: #ababab;");
        }
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Clear the searched movie result");
        clear.setTooltip(tooltip);
    }

    //help and instructions//

    @FXML
    void hoverHelp(MouseEvent event) {
        hoverEffect(event, help);
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Help and instructions for use");
        help.setTooltip(tooltip);

    }

    //The initialising method//

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        //Giving options to the combo box//

        searchResult.setEditable(false);
        t5.getItems().addAll("Action", "Biography", "Comedy", "Drama", "Educational", 
        "History", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller OR Western");

        // Adding animations to logo//

        FadeTransition ft = new FadeTransition(Duration.millis(2000), logo);
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        // making list options multiply selelcted
        list.getSelectionModel ().setSelectionMode (SelectionMode.MULTIPLE);
    }
}