/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lib.DBConn;
import org.controlsfx.dialog.Dialogs;
/**
 *
 * @author Rene
 */


public class LoginController implements Initializable{
 @FXML private TextField username;
 @FXML private PasswordField password;
 @FXML private Button login;
 
 private GMsis mainApp;
 private boolean logged;
 
  private static DBConn connection;
 public void initialize(URL u, ResourceBundle rbj) {
     connection = DBConn.getInstance();
     connection.getConn();
     
     login.setOnAction((event) -> login());
 }
 
 private void login() {
     try {
     String u = username.getText();
     String p = password.getText();
     if(u.equals("") || p.equals("")) {
         showError("Username or Password is empty");
     } else {
         String query = "SELECT * FROM Login";
         ResultSet rs = connection.queryDB(query);
         
         while(rs.next()) {
             if(u.equals(rs.getString("Username")) && p.equals(rs.getString("Password"))) {
                 //TODO LOGIN
                 logged = true;
                 mainApp.initRootLayout();
                 mainApp.showBookings();
             }
         }
         if (!logged)
            showError("Username or Password is incorrect");
     }
    } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
}
 
 public void setGMsis(GMsis gm) {
     mainApp = gm;
 }
 
 
 private void showError(String msg) {
     Dialogs.create()
             .title("Notification")
             .masthead("Login Error")
             .message(msg)
             .showWarning();
            }
}
