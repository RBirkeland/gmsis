/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis.VehicleRecords.GUI;

import GMsis.GMsis;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Rhys
 */
public class RootLayoutController implements Initializable {
    
    @FXML private MenuItem vehRecords;
    @FXML private MenuItem custRecords;
    @FXML private MenuItem partsRecords;
    @FXML private MenuItem close;
    
    private GMsis system;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void showVehicleRecords() {
        system.showVehicleRecords();
    }
    
    @FXML
    public void showCustomerRecords() {
        system.showCustomerRecords();
    }
    
    @FXML
    public void showPartsRecords() {
        system.showParts();
    }
    
    @FXML
    public void handleClose() {
        system.handleClose();
    }
    
    public void setSystem(GMsis garage) {
        system = garage;
    }
    
}
