package com.example;

public class Movie{
    static String name;
    static String number;
    static String date;
    static String loc;
    static String genre;
    static String rat;
    static String dur;
    static String price;
    static String pswd;
    static String userName;

    //making array
    
    public String[] getArray(String s){ 
        String[]token = s.split("    ");
        name = token[0];
        number = token[1];
        date = token[2];
        loc = token[3];
        genre = token[4];
        rat = token[5];
        dur = token[6];
        price = token[7];
        return token;
    }
}