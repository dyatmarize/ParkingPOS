package com.parking.pos;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author raiha
 */
public class Login extends Application{
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            Parent parentRoot = FXMLLoader.load(getClass().getResource("Login.fxml"));
            primaryStage.setTitle("Parking POS Login");
            primaryStage.setResizable(false);
            primaryStage.setIconified(false);
            primaryStage.setScene(new Scene(parentRoot));
            primaryStage.show();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }
    
    public static void main(String[]args){
        launch(args);
    }
}
