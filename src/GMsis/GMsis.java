/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis;

import GMsis.VehicleRecords.GUI.RootLayoutController;
import GMsis.VehicleRecords.GUI.VehicleEditController;
import GMsis.VehicleRecords.GUI.VehicleNewController;
import GMsis.VehicleRecords.GUI.VehicleRecordsMainController;
import GMsis.VehicleRecords.GUI.WarrantyController;
import GMsis.VehicleRecords.VehicleRecords;
import GMsis.booking.FXMLDocumentController;
import GMsis.customer.RunCustomer;
import GMsis.parts.PartsLauncher;
import VehicleRecords.Model.Vehicle;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.swing.JApplet;
import lib.DBConn;

/**
 *
 * @author Rhys Field
 */
public class GMsis extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @FXML private MenuItem custRecords;
    
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Garage System");
        
        // Delete out of date warranty
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String deleteWarnt = "DELETE FROM 'Warranty' WHERE  strftime('%s','Warranty'.'Expiration Date') < strftime('%s','" + dateFormat.format(date) + "');";
        
        DBConn db = DBConn.getInstance();
        
        try {
            Statement stmt = db.getConn().createStatement();
            stmt.execute(deleteWarnt);
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRecordsMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        showlogin();
        
        //custRecords.setOnAction(event -> showCustomerRecords());
    }
    
    public void showlogin() {
        try {
             FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GMsis.class.getResource("login.fxml"));
             Pane rootLayout = (Pane) loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setGMsis(this);
           
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showBookings() {
        try {
            //Load vehicle records pane
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(FXMLDocumentController.class.getResource("Sbooking.fxml"));
            TabPane bookings = (TabPane) loader.load();
            
            //Place pane in center of the root pane
            rootLayout.setCenter(bookings);
            
            //Give controller access to GarageSystem
            FXMLDocumentController mainController = loader.getController();
            
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.show();
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    //Initialises layout
    public void initRootLayout() {
        try {
            //Load fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GMsis.class.getResource("VehicleRecords/GUI/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            RootLayoutController controller = loader.getController();
            controller.setSystem(this);
            
            //Load scene
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void showVehicleRecords() {
        System.out.println(this.getPrimaryStage());
        try {                  
                //Load the fxml file and create a new stage for the popup dialog
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(GMsis.class.getResource("VehicleRecords/GUI/VehicleRecordsMain.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
            
                // Create dialog stage
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Vehicle Records");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setResizable(false);
                dialogStage.sizeToScene();
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
            
                // Set controller
                VehicleRecordsMainController controller = loader.getController();
                controller.setGaragaeSystem(new VehicleRecords(dialogStage));
                
                
                dialogStage.showAndWait();
            } catch(IOException e) {
                e.printStackTrace();
            }
    }
    
    @FXML
    public void showCustomerRecords() {
        try {
                //Load the fxml file and create a new stage for the popup dialog
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(RunCustomer.class.getResource("gui.fxml"));
                TabPane page = (TabPane) loader.load();
            
                // Create dialog stage
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Customer Accounts");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setResizable(false);
                dialogStage.sizeToScene();
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);
            
                // Set controller
                RunCustomer controller = loader.getController();
                
                dialogStage.showAndWait();
            } catch(IOException e) {
                e.printStackTrace();
            }
    }
    
    public void showParts() {
        
                Parent root = (new PartsLauncher().launch());
                Scene partsScene = new Scene(root, 800, 500);
                
                Stage partStage = new Stage();
                partStage.setScene(partsScene);
                partStage.initModality(Modality.WINDOW_MODAL);
                partStage.setTitle("Parts");
                
                partStage.initOwner(primaryStage);
                partStage.setResizable(false);
                partStage.sizeToScene();
                partStage.showAndWait();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    @FXML
    public void handleClose() {
        primaryStage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
