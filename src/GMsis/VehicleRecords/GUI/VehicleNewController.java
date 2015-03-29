/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis.VehicleRecords.GUI;

import GMsis.VehicleRecords.ErrorChecking;
import lib.DBConn;
import lib.UpdateTableView;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Rhys Field
 */
public class VehicleNewController implements Initializable {
    
    @FXML private ComboBox<String> cmbType;
    @FXML private ComboBox<String> cmbMake;
    @FXML private ComboBox<String> cmbModel;
    @FXML private ComboBox<String> cmbEngine;
    @FXML private ChoiceBox<String> cmbFuel;
    @FXML private ChoiceBox<String> cmbColour;
    @FXML private DatePicker dteService;
    @FXML private DatePicker dteMOT;
    @FXML private TextField txtMileage;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private Button btnSelect;
    @FXML private Label lblCustID;
    @FXML private Label lblCustName;
    @FXML private Label lblCustAddress1;
    @FXML private Label lblCustAddress2;
    @FXML private Label lblCustTown;
    @FXML private Label lblCustPostcode;
    @FXML private Label lblCustPhoneNum;
    @FXML private Pane custDetails;
    @FXML private TableView<?> tblCust;
    @FXML private TableView<?> tblWarnt;
    @FXML private Label lblCompID;
    @FXML private Label lblCompName;
    @FXML private Label lblCompAddress1;
    @FXML private Label lblCompAddress2;
    @FXML private Label lblCompTown;
    @FXML private Label lblCompPostcode;
    @FXML private Label lblCompPhoneNum;
    @FXML private DatePicker dteWarrantyEx;
    @FXML private Pane warDetails;
    @FXML private Button btnSelectWar;

    private Stage dialogStage;
    private boolean saveClicked = false;

    private boolean custSelected = false;
    private boolean warntSelected = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Load customer table
        UpdateTableView.buildData(tblCust, "SELECT \"Customer ID\", \"First Name\", \"Last Name\" FROM Customer;");
        
        //Load warranty table
        UpdateTableView.buildData(tblWarnt, "SELECT \"Company ID\", \"Company Name\", \"Phone Number\" FROM 'Warranty Company';");
        
        //Load make combo box
        String query = "SELECT DISTINCT Make FROM Vehicle ORDER BY Make;";
        DBConn db = DBConn.getInstance();
        db.getConn();
        ResultSet rs = db.queryDB(query);
           
        ObservableList<String> makeOptions = FXCollections.observableArrayList();        
     
        try {
            while(rs.next()) {
                makeOptions.add(rs.getString("Make"));
            }        
        } catch (Exception ex) {
            Logger.getLogger(VehicleNewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cmbMake.getSelectionModel().clearSelection();
        cmbMake.setItems(makeOptions);
        cmbMake.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateModelCombo(newValue);
         });
        
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
    
    public void updateModelCombo(String value) {
        //Construct query with currently selected make
        String query = "SELECT DISTINCT Model FROM Vehicle WHERE Vehicle.Make='" + value + "' ORDER BY Model;";
        DBConn db = DBConn.getInstance();
        db.getConn();
        ResultSet rs = db.queryDB(query);
        
        ObservableList<String> modelOptions = FXCollections.observableArrayList();       
     
        try {
            while(rs.next()) {
                modelOptions.add(rs.getString("Model"));
            }        
        } catch (Exception ex) {
            Logger.getLogger(VehicleNewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Fill combo box and add listener
        cmbModel.getSelectionModel().clearSelection();
        cmbModel.setItems(modelOptions);
        cmbModel.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateEngineCombo(value, newValue);
         });
    }
    
    public void updateEngineCombo(String value, String newValue) {
        //Construct query with currently selected make and model
        String query = "SELECT DISTINCT \"Engine Size\" FROM Vehicle WHERE Vehicle.Make='" + value + "' AND Vehicle.Model='"
                + newValue + "';";
        DBConn db = DBConn.getInstance();
        db.getConn();
        ResultSet rs = db.queryDB(query);
        
        ObservableList<String> engineOptions = FXCollections.observableArrayList();       
     
        try {
            while(rs.next()) {
                engineOptions.add(rs.getString("Engine Size"));
            }        
        } catch (Exception ex) {
            Logger.getLogger(VehicleNewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //fill combo box
        cmbEngine.getSelectionModel().clearSelection();
        cmbEngine.setItems(engineOptions);
    }
    
    @FXML
    public void selectCustomer() {
        
        if(tblCust.getSelectionModel().getSelectedItems().get(0) != null) {
            //Get selected customer details
            ObservableList<String> custID = (ObservableList<String>) tblCust.getSelectionModel().getSelectedItems().get(0);
            String custQuery = "SELECT * FROM Customer WHERE \"Customer ID\"=" + custID.get(0) + ";";
        
            //Load details into pane
            try {
                DBConn db = DBConn.getInstance();
                db.getConn();
                ResultSet custrs = db.queryDB(custQuery);
        
                lblCustID.setText(custrs.getString("Customer ID").trim());
                lblCustName.setText(custrs.getString("First Name").trim() + " " + custrs.getString("Last Name").trim());
                lblCustAddress1.setText(custrs.getString("1st Line Address").trim());
                lblCustAddress2.setText(custrs.getString("2nd Line Address"));
                lblCustTown.setText(custrs.getString("Town").trim());
                lblCustPostcode.setText(custrs.getString("Postal Code").trim());
                lblCustPhoneNum.setText(custrs.getString("Phone Number").trim());
            } catch(SQLException e) {
                System.out.println(e);
            }
        
            //hide table and show details
            tblCust.setVisible(false);
            btnSelect.setVisible(false);
            custDetails.setVisible(true);
            
            custSelected = true;
        } else {
            // Nothing selected
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("No Selection")
                    .masthead("No Customer Selected")
                    .message("Please select a customer in the table.")
                    .showError();
        }      
    }
    
    @FXML
    public void selectWarranty() {
         if(tblWarnt.getSelectionModel().getSelectedItems().get(0) != null) {
            //Get selected warranty details
            ObservableList<String> compID = (ObservableList<String>) tblWarnt.getSelectionModel().getSelectedItems().get(0);
            String warntQuery = "SELECT * FROM 'Warranty Company' WHERE \"Company ID\"=" + compID.get(0) + ";";
        
            //Load details into pane
            try {
                DBConn db = DBConn.getInstance();
                db.getConn();
                ResultSet warntrs = db.queryDB(warntQuery);
        
                lblCompID.setText(warntrs.getString("Company ID").trim());
                lblCompName.setText(warntrs.getString("Company Name").trim());
                lblCompAddress1.setText(warntrs.getString("1st Line Address").trim());
                lblCompAddress2.setText(warntrs.getString("2nd Line Address"));
                lblCompTown.setText(warntrs.getString("Town/City").trim());
                lblCompPostcode.setText(warntrs.getString("Post Code").trim());
                lblCompPhoneNum.setText(warntrs.getString("Phone Number").trim());
            } catch(SQLException e) {
                System.out.println(e);
            }
        
            //hide table and show details
            tblWarnt.setVisible(false);
            btnSelectWar.setVisible(false);
            warDetails.setVisible(true);
            
            warntSelected = true;
        } else {
            // Nothing selected
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("No Selection")
                    .masthead("No Company Selected")
                    .message("Please select a company in the table.")
                    .showError();
        }
    }
    
    @FXML
    public void handleSave() {
        // Keep check on if save is valid
        boolean save = true;
        
        // Error Checking
        if(!custSelected) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Customer Selected")
                    .message("Please select a customer.")
                    .showError();
        } else if(cmbType.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Vehicle Type Given")
                    .message("Please select a vehicle type.")
                    .showError();
        } else if(cmbMake.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Vehicle Make Given")
                    .message("Please select a vehicle make.")
                    .showError();
        } else if(!ErrorChecking.checkString("Vehicle make", cmbMake.getValue().trim(), 20, dialogStage)) {
            save = false;
        } else if(cmbModel.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Vehicle Model Given")
                    .message("Please select a vehicle model.")
                    .showError();
        } else if(!ErrorChecking.checkString("Vehicle make", cmbModel.getValue().trim(), 20, dialogStage)) {
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
        } else if(cmbEngine.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Engine Size Given")
                    .message("Please enter an engine size.")
                    .showError();
        } else if(!ErrorChecking.engineSizeCheck(cmbEngine.getValue().trim(), dialogStage)) {
            save = false;
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
        } else if(!ErrorChecking.checkDigit("Mileage", txtMileage.getText().trim(), 10, dialogStage)) {
            save = false;
        } else if(warntSelected && dteWarrantyEx.getValue() == null) {
            save = false;
            Dialogs.create()
                    .owner(dialogStage)
                    .style(DialogStyle.NATIVE)
                    .title("Error")
                    .masthead("No Warranty Expiration Given")
                    .message("Please select the date of warranty expiration.")
                    .showError();
        }
        
        if(save) {
            DBConn db = DBConn.getInstance();
            db.getConn();
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            int type = 0;
            
            if(cmbType.getValue().equals("Car")) {
                type = 0;
            } else if(cmbType.getValue().equals("Van")) {
                type = 1;
            } else if(cmbType.getValue().equals("Truck")) {
                type = 2;
            }
            
            String insert = "INSERT INTO 'Vehicle' "
                    + "('Customer ID', 'Vehicle Type', 'Make', 'Model', 'Engine Size', 'Fuel Type', 'Colour', 'MOT Renewal', 'Last Service', 'Mileage') "
                    + "VALUES (\"" + 
                    lblCustID.getText().trim() + "\", \"" + 
                    type + "\", \"" + 
                    cmbMake.getValue().trim() + "\", \"" + 
                    cmbModel.getValue().trim() + "\", \"" + 
                    cmbEngine.getValue().trim() + "\", \"" +
                    cmbFuel.getValue().trim() + "\", \"" + 
                    cmbColour.getValue().trim() + "\", \"" +
                    dtf.format(dteMOT.getValue()) + "\", \"" + 
                    dtf.format(dteService.getValue()) + "\", \"" + 
                    txtMileage.getText().trim() + "\");";
            
            db.updateDB(insert);
            
            if(warntSelected) {           
                String warnt = "INSERT INTO 'Warranty' "
                        + "('Vehicle ID', 'Company ID', 'Expiration Date') "
                        + "VALUES (last_insert_rowid(), \"" + 
                    lblCompID.getText().trim() + "\", \"" + 
                    dteWarrantyEx.getValue() + "\");";
                
                db.updateDB(warnt);
            }
            
            this.saveClicked = true;
            
            // Close window
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
    
}
