/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.parking.pos;

/**
 *
 * @author raiha
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDataObj {    
    private static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/parkingpos?useSSL=false";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "password";
    private static final String SELECT_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";

    public boolean validate(String username, String password){
    
        try{
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
               return true;
            }
        }catch(SQLException e){
            printSQLException(e);
        }
        return false;
    }
    
    public void executeUpdate(String query) {
        Connection conn = this.getConnection();
        Statement st;
        System.out.println(query);
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch(Exception ex){
            System.out.println("error while inserting record.");
            ex.printStackTrace();
        } 
    }
    
    public ResultSet excuteQuery(String query){
        Connection conn = this.getConnection();
        Statement st;
        ResultSet rs;
        System.out.println(query);
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            
            return rs;
        }catch(Exception ex){
            //System.out.println("error while inserting record.");
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
            return connection;
        }catch(SQLException ex){
            printSQLException(ex);
        }
        return null;
    }

    public static void printSQLException(SQLException ex){
        for(Throwable e: ex){
            if(ex instanceof SQLException){
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException)e).getSQLState());
                System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
                System.err.println("Message: " + ex.getMessage());
                Throwable t = ex.getCause();
                while(t != null){
                    System.err.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
