package com.parking.pos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author raiha
 */
public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void actionLogin(ActionEvent event) {
        Window owner = txtUsername.getScene().getWindow();
        System.out.println(txtUsername.getText());
        System.out.println(txtPassword.getText());
        
        if(txtUsername.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Please enter a valid username","Form error!");
            return;
        }
        
        if(txtPassword.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Please enter a valid password","Form error!");
            return;
        }
        
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        JdbcDataObj jdbcDao = new JdbcDataObj();
        boolean flag = jdbcDao.validate(username, password);
        if(!flag){
            infoBox("Please enter correct username and password", null,"Failed");
        }else{
            infoBox("Login successful!", null, "Success");
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckIn.fxml"));
                Parent root = fxmlLoader.load();
                
                Stage stage = new Stage();
                stage.setTitle("Parking POS | Check In");
                stage.setScene(new Scene(root));
                CheckInController controller = (CheckInController) fxmlLoader.getController();
                controller.setUsername(username);
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    public static void showAlert(Alert.AlertType alertType,Window owner, String message, String title){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        alert.show();
    }
}
