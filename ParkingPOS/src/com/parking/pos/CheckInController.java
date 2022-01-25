/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.pos;

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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author raiha
 */
public class CheckInController implements Initializable {

    @FXML
    private Label labelUser;
    @FXML
    private Button btnCheckOut;
    @FXML
    private Button btnExit;
    @FXML
    private TextField tfPlatNomor;
    @FXML
    private Button btnCetak;
    @FXML
    private ChoiceBox<String> cbJenisKendaraan;
    
    JdbcDataObj jdbc;
    
    @FXML
    private Label date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbJenisKendaraan.getItems().add("Mobil");
        cbJenisKendaraan.getItems().add("Motor");
        
        jdbc = new JdbcDataObj();
        
        initClock();
    }    
    
    public void setUsername(String username){
        labelUser.setText(username);
    }
    
    @FXML
    private void cetakTiket(ActionEvent event) throws SQLException {
        String platNomor = tfPlatNomor.getText();
        String jenisKendaraan = cbJenisKendaraan.getSelectionModel().getSelectedItem();
        Window owner = (Stage) tfPlatNomor.getScene().getWindow();

        if (platNomor.isEmpty() || jenisKendaraan.isEmpty()){
            showAlert(Alert.AlertType.ERROR, owner, "Isi Form Dengan Benar.", "Form Error!");
        }else{
            String query = "SELECT * FROM transaksi WHERE plat_nomor = '"+ platNomor +"' AND waktu_checkout IS NULL";                
            ResultSet rs = jdbc.excuteQuery(query);
            boolean isExist = false;
            while(rs.next()){
                isExist = true;
            }
            if(!isExist){
                query = "INSERT INTO transaksi(plat_nomor, jenis_kendaraan) VALUES('" + platNomor + "', '" + jenisKendaraan + "')";
                jdbc.executeUpdate(query);
                showAlert(Alert.AlertType.INFORMATION, owner, "Check In Succesful!", "Success!");
                tfPlatNomor.clear();
                cbJenisKendaraan.valueProperty().set(null);
            }else{
                showAlert(Alert.AlertType.ERROR, owner, "Plate Number Already Exist!", "Error!");
            }
        }
    }
    
    @FXML
    private void checkOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckOut.fxml"));
        Parent root = fxmlLoader.load();
                
        Stage stage = new Stage();
        stage.setTitle("POS | Check Out");
        stage.setScene(new Scene(root));
        CheckOutController controller = (CheckOutController) fxmlLoader.getController();
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    
    
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            date.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    public static void showAlert(Alert.AlertType alertType, Window owner, String message, String title) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}
