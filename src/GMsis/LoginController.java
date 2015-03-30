/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lib.DBConn;
import org.controlsfx.dialog.Dialogs;
/**
 *
 * @author Rene
 */


public class LoginController {
 @FXML private TextField username;
 @FXML private PasswordField password;
 @FXML private Button login;
 
  private static DBConn connection;
 public LoginController() {
     connection = DBConn.getInstance();
     connection.getConn();
 }
 
 private void login() {
     try {
     String u = username.getText();
     String p = password.getText();
     if(u.equals("") || p.equals("")) {
         //TODO ERROR
     } else {
         String query = "SELECT * FROM Login";
         ResultSet rs = connection.queryDB(query);
         
         while(rs.next()) {
             if(u.equals(rs.getString("Username")) && p.equals(rs.getString("Password"))) {
                 //TODO LOGIN
             }
         }
         
         //TODO ERROR
     }
    } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
}
}
