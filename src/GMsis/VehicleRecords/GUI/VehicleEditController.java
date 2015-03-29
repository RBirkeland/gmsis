/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis.VehicleRecords.GUI;

import GMsis.VehicleRecords.ErrorChecking;
import VehicleRecords.Model.Vehicle;
import lib.DBConn;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Rhys Field
 */
public class VehicleEditController implements Initializable {
    
    @FXML private ComboBox<String> cmbType;
    @FXML private TextField txtMake;
    @FXML private TextField txtModel;
    @FXML private ComboBox<String> cmbFuel;
    @FXML private TextField txtEngine;
    @FXML private ComboBox<String> cmbColour;
    @FXML private DatePicker dteService;
    @FXML private DatePicker dteMOT;
    @FXML private TextField txtMileage;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    
    private Vehicle vehicle;
    private Stage dialogStage;
    private boolean saveClicked = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Load vehicle types
        ObservableList<String> typeOptions = 
            FXCollections.observableArrayList(
                "Car",
                "Van",
                "Truck"
            );
        cmbType.setItems(typeOptions);
        
        //Load colour combo box
        ObservableList<String> colourOptions = 
            FXCollections.observableArrayList(
                "Black",
                "Grey",
                "White",
                "Red",
                "Orange",
                "Yellow",
                "Green",
                "Blue",
                "Purple",
                "Brown",
                "Magenta",
                "Cyan",
                "Turquoise",
                "Silver",
                "Lime",
                "Teal",
                "Pink"
            );
        cmbColour.setItems(colourOptions);
        
        //Load fuel combo box
        ObservableList<String> fuelOptions = 
            FXCollections.observableArrayList(
                "Super Unleaded",
                "Premium Fuel",
                "Diesel",
                "LPG",
                "Biofuel",
                "Hybrid",
                "Electric"
            );
        cmbFuel.setItems(fuelOptions);
    }
    
    @FXML
    public void handleSave() {
        // Keep check on if save if valid
        boolean save = true;
        
        // Error Checking
        if(!ErrorChecking.checkString("Vehicle make", txtMake.getText().trim(), 20, dialogStage)) {
            save = false;
        } else if(!ErrorChecking.checkString("Vehicle model", txtModel.getText().trim(), 20, dialogStage)){
            save = false;
        } else if(!ErrorChecking.engineSizeCheck(txtEngine.getText().trim(), dialogStage)) {
            save = false;
        } else if(cmbFuel.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Fuel Type Given")
                    .message("Please select a fuel type.")
                    .showError();
        } else if(cmbColour.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Colour Given")
                    .message("Please select a colour.")
                    .showError();
        } else if(dteService.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Last Service Given")
                    .message("Please select the date of the last service.")
                    .showError();
        } else if(dteMOT.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No MOT Renewal Given")
                    .message("Please select the date of MOT renewal.")
                    .showError();
        } else if(!ErrorChecking.checkDigit("Mileage", txtMileage.getText().trim(), 20, dialogStage)) {
            save = false;
        }
        
        // If save is still valid excute sql
        if(save) {
            int type = 0;
            
            if(cmbType.getValue().equals("Car")) {
                type = 0;
            } else if(cmbType.getValue().equals("Van")) {
                type = 1;
            } else if(cmbType.getValue().equals("Truck")) {
                type = 2;
            }
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            String insert = "UPDATE \"Vehicle\" "
                    + "SET 'Vehicle Type'='" + type
                    + "', 'Make'='" + txtMake.getText().trim() 
                    + "', 'Model'='" + txtModel.getText().trim()
                    + "', 'Fuel Type'='" + cmbFuel.getValue()
                    + "', 'Engine Size'='" + txtEngine.getText().trim()
                    + "', 'Colour'='" + cmbColour.getValue()
                    + "', 'Last Service'='" + dtf.format(dteService.getValue())
                    + "', 'MOT Renewal'='" + dtf.format(dteMOT.getValue())
                    + "', 'Mileage'='" + txtMileage.getText().trim()
                    + "' WHERE 'Vehicle'.'Vehicle ID'=" + vehicle.getVehID() + ";";
            
            DBConn db = DBConn.getInstance();
            db.getConn();
            db.updateDB(insert);
            
            saveClicked = true;
            this.dialogStage.close();
        }
    }
    
    public boolean isSaveClicked() {
        return saveClicked;
    }
    
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setVehicle(Vehicle veh) {
        
        //Load vehicle properties into fields  
        this.vehicle = veh;
        
        //Date formater
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        cmbType.setValue(vehicle.getType());
        txtMake.setText(vehicle.getVehMake());
        txtModel.setText(vehicle.getVehModel());
        cmbFuel.setValue(vehicle.getVehFuelType());
        txtEngine.setText(vehicle.getVehEngineSize());
        cmbColour.setValue(vehicle.getVehColour());
        dteService.setValue(LocalDate.parse(vehicle.getVehLastService(), dtf));
        dteMOT.setValue(LocalDate.parse(vehicle.getVehMOTRenewal(), dtf));
        txtMileage.setText(vehicle.getVehMileage().toString());
    }
    
}
