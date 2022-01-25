/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.pos;

import static com.parking.pos.CheckInController.showAlert;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ChoiceBox;
import java.text.*;
import java.util.*;
import javafx.scene.control.Alert;
import javafx.stage.Window;
/**
 * FXML Controller class
 *
 * @author raiha
 */
public class CheckOutController implements Initializable {

    @FXML
    private Label labelUser;
    @FXML
    private Button btnCheckIn;
    @FXML
    private Label date;
    @FXML
    private TextField tfPlatNomor;
    @FXML
    private Button btnPay;
    @FXML
    private Button btnSubmit;
    @FXML
    private Label labelPlatNomor;
    @FXML
    private Label labelJenisKendaraan;
    @FXML
    private Label labelWaktuCheckIn;
    @FXML
    private Label labelWaktuCheckOut;
    @FXML
    private Label labelMetodePembayaran;
    
    JdbcDataObj jdbc;
    @FXML
    private ChoiceBox<String> cbMetodePembayaran;
    @FXML
    private Label labelDuration;
    @FXML
    private Label labelTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbMetodePembayaran.getItems().add("Cash");
        cbMetodePembayaran.getItems().add("Cashless");
        
        jdbc = new JdbcDataObj();
        
        initClock();
    }    
    
    public void setUsername(String username){
        labelUser.setText(username);
    }
    
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            date.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    
    @FXML
    private void checkIn(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckIn.fxml"));
        Parent root = fxmlLoader.load();
                
        Stage stage = new Stage();
        stage.setTitle("POS | Check In");
        stage.setScene(new Scene(root));
        CheckInController controller = (CheckInController) fxmlLoader.getController();
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void btnSubmit(ActionEvent event) throws SQLException, ParseException {
        String waktuCheckIn = "";
        
        Window owner = (Stage) tfPlatNomor.getScene().getWindow();
        String platNomor = tfPlatNomor.getText();
        String query = "SELECT plat_nomor, jenis_kendaraan, waktu_checkin FROM transaksi WHERE plat_nomor = '" + platNomor +"' AND waktu_checkout IS NULL";
        
        ResultSet rs = jdbc.excuteQuery(query);
        
        boolean isExist = false;
        
        while(rs.next()){
            isExist = true;
            
            labelPlatNomor.setText(String.format("%20s", rs.getString("plat_nomor")));
            labelJenisKendaraan.setText(String.format("%20s", rs.getString("jenis_kendaraan")));
            waktuCheckIn = rs.getString("waktu_checkin");
            labelWaktuCheckIn.setText(String.format("%20s", rs.getString("waktu_checkin")));            
        }
        
        if(!isExist){
            showAlert(Alert.AlertType.ERROR, owner, "Plate Number Not Found!", "404 Not Found!");
        }else{
        
            //Counting Rate per Hour based on Time Difference( time checkout - time checkin)
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String waktuCheckOut = dateTime.format(formatDate);
            labelWaktuCheckOut.setText(String.format("%20s", waktuCheckOut));

            String metodePembayaran = cbMetodePembayaran.getSelectionModel().getSelectedItem();
            labelMetodePembayaran.setText(String.format("%20s", metodePembayaran));


            //Counting Rate Hour Based On Time Difference
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Parsing the Time Period
            Date date1 = simpleDateFormat.parse(waktuCheckIn);
            Date date2 = simpleDateFormat.parse(waktuCheckOut);

            // Calculating the difference in milliseconds
            long differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());

            // Calculating the difference in Hours
            long differenceInHours
                = (differenceInMilliSeconds / (60 * 60 * 1000))
                  % 24;

            // Calculating the difference in Minutes
            long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;

            // Calculating the difference in Seconds
            long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;

            // Printing the answer
            labelDuration.setText(
                differenceInHours + " h "
                + differenceInMinutes + " m "
                + differenceInSeconds + " s ");

            long total;

            if(differenceInHours == 0){
                total = 3000;
            }else{
                total = differenceInHours*3000;
            }

            //Merubah Format Kurs
            DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

            formatRp.setCurrencySymbol("Rp. ");
            formatRp.setMonetaryDecimalSeparator(',');
            formatRp.setGroupingSeparator('.');

            kursIndonesia.setDecimalFormatSymbols(formatRp);

            labelTotal.setText(kursIndonesia.format(total));

            tfPlatNomor.clear();
            cbMetodePembayaran.valueProperty().set(null);
        }
        
    }
    
    @FXML
    private void pay(ActionEvent event) {
        Window owner = (Stage) btnPay.getScene().getWindow();
        String platNomor = labelPlatNomor.getText();
        String waktuCheckOut = labelWaktuCheckOut.getText();
        String metodePembayaran = labelMetodePembayaran.getText();
        
        String query = "UPDATE transaksi SET waktu_checkout = '"+ waktuCheckOut.trim() +"', metode_pembayaran = '"+ metodePembayaran.trim() +"' WHERE plat_nomor = '"+ platNomor.trim() +"' AND waktu_checkout IS NULL";
        
        jdbc.executeUpdate(query);
        
        showAlert(Alert.AlertType.INFORMATION, owner, "Payement Succesful!", "Info");
        
        labelPlatNomor.setText("**");
        labelJenisKendaraan.setText("**");
        labelWaktuCheckIn.setText("**");
        labelWaktuCheckOut.setText("**");
        labelMetodePembayaran.setText("**");
        labelTotal.setText("Rp. 0,00");
        labelDuration.setText("0h 0m 0s");
    }    
}
