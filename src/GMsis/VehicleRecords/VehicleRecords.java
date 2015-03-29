/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GMsis.VehicleRecords;

import GMsis.GMsis;
import GMsis.VehicleRecords.GUI.VehicleEditController;
import GMsis.VehicleRecords.GUI.VehicleNewController;
import GMsis.VehicleRecords.GUI.VehicleRecordsMainController;
import GMsis.VehicleRecords.GUI.WarrantyController;
import VehicleRecords.Model.Vehicle;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author rf300
 */
public class VehicleRecords {
    
    private Stage primaryStage;

    
    public VehicleRecords(Stage primary){
        primaryStage = primary;
    }
    
    public boolean showEditVehicle(Vehicle vehicle) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GMsis.class.getResource("VehicleRecords/GUI/VehicleEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Vehicle");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            dialogStage.sizeToScene();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the vehicle into the controller.
            VehicleEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setVehicle(vehicle);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showNewVehicle() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GMsis.class.getResource("VehicleRecords/GUI/VehicleNew.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Vehicle");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            dialogStage.sizeToScene();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the v into the controller.
            VehicleNewController controller = loader.getController();
            controller.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showWarranty() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GMsis.class.getResource("VehicleRecords/GUI/Warranty.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Warranty");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            dialogStage.sizeToScene();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the v into the controller.
            WarrantyController controller = loader.getController();
            controller.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
