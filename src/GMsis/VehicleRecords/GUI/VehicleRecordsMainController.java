/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis.VehicleRecords.GUI;

import GMsis.GMsis;
import GMsis.parts.DateUtils;
import GMsis.VehicleRecords.VehicleRecords;
import VehicleRecords.Model.Vehicle;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import lib.DBConn;
import lib.UpdateTableView;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Rhys Field
 */
public class VehicleRecordsMainController implements Initializable {
    
    @FXML private TabPane tabPane;
    @FXML private ComboBox<String> cmbSearch;
    @FXML private TextField txtSearch;
    @FXML private Button btnSearch;
    @FXML private TableView<?> vehicleTable;
    @FXML private TableView partsTable;
    @FXML private TableView<?> bookingsTable;
    @FXML private TableView<?> sbookingsTable;
    @FXML private Tab tabVehicle;
    @FXML private Tab tabCustomer;
    @FXML private Tab tabWarranty;
    @FXML private Tab tabParts;
    @FXML private Tab tabBookings;
    @FXML private Label lblType;
    @FXML private Label lblVehID;
    @FXML private Label lblVehMake;
    @FXML private Label lblVehModel;
    @FXML private Label lblEngineSize;
    @FXML private Label lblFuelType;
    @FXML private Label lblColour;
    @FXML private Label lblMOT;
    @FXML private Label lblLastService;
    @FXML private Label lblMileage;
    @FXML private Button btnVehNew;
    @FXML private Button btnVehEdit;
    @FXML private Button btnVehDelete;
    @FXML private Button btnWarranty;
    @FXML private Label lblWarName;
    @FXML private Label lblWarAddress1;
    @FXML private Label lblWarAddress2;
    @FXML private Label lblWarTown;
    @FXML private Label lblWarPostcode;
    @FXML private Label lblPhoneNum;
    @FXML private Label lblWarExpire;
    @FXML private Button btnWarEdit;
    @FXML private Label lblCustID;
    @FXML private Label lblCustName;
    @FXML private Label lblCustAddress1;
    @FXML private Label lblCustAddress2;
    @FXML private Label lblCustTown;
    @FXML private Label lblCustPostcode;
    @FXML private Label lblCustPhoneNum;
    @FXML private ChoiceBox cmbBookings;
    
    private Vehicle currentVehicle;
    private VehicleRecords vehicleRecords;

    @FXML
    private void search() {
        if(cmbSearch.getValue() == null) {
            return;
        } else if(txtSearch.getText() == null || txtSearch.getText().equals("")) {
            //Show all
            UpdateTableView.buildData(vehicleTable, "SELECT \"Vehicle ID\", Make, model, Colour FROM Vehicle;");
        } else {
            //Search
            setPanel();
            String query = "SELECT \"Vehicle ID\", Make, model, Colour FROM Vehicle WHERE \"" + cmbSearch.getValue() + 
                    "\" LIKE '%" + txtSearch.getText() + "%';";
            UpdateTableView.buildData(vehicleTable, query);
        }
    }
    
    @FXML
    public void handleDelete() {
        if (this.currentVehicle != null) {
            // Display confirmation window
            Action response = Dialogs.create()
                    .owner(vehicleRecords.getPrimaryStage())
                    .style(DialogStyle.NATIVE)
                    .title("Delete")
                    .message("Are you sure you wish to delete this record.")
                    .showConfirm();
            
            if(response == Dialog.Actions.YES) {
                String delete = "DELETE FROM 'Vehicle' WHERE 'Vehicle'.'Vehicle ID'='" + currentVehicle.getVehID() + "';";
                
                DBConn db = DBConn.getInstance();
                try {
                    Statement stmt = db.getConn().createStatement();
                    stmt.execute("PRAGMA foreign_keys=ON;");
                    stmt.execute(delete);
                } catch(SQLException e) {
                    Dialogs.create()
                            .owner(vehicleRecords.getPrimaryStage())
                            .style(DialogStyle.NATIVE)
                            .title("SQL Error")
                            .masthead("Error trying to delete record")
                            .message("Unable to delete record, another record may be dependent on it.")
                            .showException(e);
                }
                
                //Update tableview
                UpdateTableView.buildData(vehicleTable, "SELECT \"Vehicle ID\", Make, model, Colour FROM Vehicle;");
            }
                    
        } else {
            // Nothing selected
            Dialogs.create()
                    .owner(vehicleRecords.getPrimaryStage())
                    .style(DialogStyle.NATIVE)
                    .title("No Selection")
                    .masthead("No Vehicle Selected")
                    .message("Please select a vehicle in the table.")
                    .showError();
        }
    }
    
    @FXML
    public void editVehicle() throws SQLException {
        if (this.currentVehicle != null) {
            boolean okClicked = vehicleRecords.showEditVehicle(currentVehicle);
            if (okClicked) {
                //refresh table
                UpdateTableView.buildData(vehicleTable, "SELECT \"Vehicle ID\", Make, model, Colour FROM Vehicle;");
                vehicleTable.getSelectionModel().clearSelection();
                setPanel();
            }

        } else {
            // Nothing selected
            Dialogs.create()
                    .owner(vehicleRecords.getPrimaryStage())
                    .style(DialogStyle.NATIVE)
                    .title("No Selection")
                    .masthead("No Vehicle Selected")
                    .message("Please select a vehicle in the table.")
                    .showError();
        }
    }
    
    @FXML
    public void newVehicle() {
        boolean okClicked = vehicleRecords.showNewVehicle();
        if (okClicked) {
            //refresh table
            UpdateTableView.buildData(vehicleTable, "SELECT \"Vehicle ID\", Make, model, Colour FROM Vehicle;");
        }
    }
    
    @FXML
    public void warranty() {
        boolean okClicked = vehicleRecords.showWarranty();
        if (okClicked) {
            //refresh table
            UpdateTableView.buildData(vehicleTable, "SELECT \"Vehicle ID\", Make, model, Colour FROM Vehicle;");
        }
    }
    
    public void setGaragaeSystem(VehicleRecords records) {
        this.vehicleRecords = records;       
    }
    
    private void setPanel() {
        lblType.setText("");
        lblVehID.setText("");
        lblVehMake.setText("");
        lblVehModel.setText("");
        lblEngineSize.setText("");
        lblFuelType.setText("");
        lblColour.setText("");
        lblMOT.setText("");
        lblLastService.setText("");
        lblMileage.setText(""); 
        tabCustomer.setDisable(true);
        tabWarranty.setDisable(true);
        tabParts.setDisable(true);
        tabBookings.setDisable(true);
    }
    
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {         
        // Initialise table view
        UpdateTableView.buildData(vehicleTable, "SELECT \"Vehicle ID\", Make, Model, Colour FROM Vehicle;");
        
        // Clear details
        setPanel();
        
        //Load search combo box
        ObservableList<String> options = 
            FXCollections.observableArrayList(
                "Make",
                "Model",
                "Engine Size",
                "Fuel Type",
                "Colour"
            );
        cmbSearch.setItems(options);
        
        // Listen for selection changes and display current selection details
        vehicleTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
            try {
                updatePanel((ObservableList<String>) newValue);
            } catch (Exception ex) {
                vehicleTable.getSelectionModel().clearSelection();
            }
        });     
    }
    
    private void updatePanel(ObservableList<String> list) throws SQLException, Exception {
        
        tabPane.getSelectionModel().select(0);
        
        //Load vehicle details
        String vehicleQuery = "SELECT * FROM Vehicle WHERE \"Vehicle ID\"=" + list.get(0) + ";";
        
        DBConn db = DBConn.getInstance();
        db.getConn();
        ResultSet vehrs = db.queryDB(vehicleQuery);
        
        String type = "";
        switch(Integer.parseInt(vehrs.getString("Vehicle Type").trim())) {
            case 0: type = "Car";
                break;
            case 1: type = "Van";
                break;
            case 2: type = "Truck";
                break;
        }
          
        lblType.setText(type);
        lblVehID.setText(vehrs.getString("Vehicle ID").trim());
        lblVehMake.setText(vehrs.getString("Make").trim());
        lblVehModel.setText(vehrs.getString("Model").trim());
        lblEngineSize.setText(vehrs.getString("Engine Size").trim());
        lblFuelType.setText(vehrs.getString("Fuel Type").trim());
        lblColour.setText(vehrs.getString("Colour").trim());
        lblMOT.setText(vehrs.getString("MOT Renewal").trim());
        lblLastService.setText(vehrs.getString("Last Service").trim());
        lblMileage.setText(vehrs.getString("Mileage").trim());

        //Update current vehicle object
        currentVehicle = new Vehicle();
        currentVehicle.setType(type);
        currentVehicle.setVehID(Integer.parseInt(vehrs.getString("Vehicle ID")));
        currentVehicle.setCustomerID(Integer.parseInt(vehrs.getString("Customer ID")));
        currentVehicle.setVehModel(vehrs.getString("Model").trim());
        currentVehicle.setVehMake(vehrs.getString("Make").trim());
        currentVehicle.setVehEngineSize(vehrs.getString("Engine Size").trim());
        currentVehicle.setVehFuelType(vehrs.getString("Fuel Type").trim());
        currentVehicle.setVehColour(vehrs.getString("Colour").trim());
        currentVehicle.setVehMOTRenewal(vehrs.getString("MOT Renewal").trim());
        currentVehicle.setVehLastService(vehrs.getString("Last Service").trim());
        currentVehicle.setVehMileage(Integer.parseInt(vehrs.getString("Mileage").trim()));
        
        //Load customer details
        String custQuery = "SELECT Customer.* FROM Vehicle, Customer WHERE Vehicle.\"Vehicle ID\"=" + vehrs.getString("Vehicle ID") + 
                " AND Customer.\"Customer ID\"=" + vehrs.getString("Customer ID") + ";";
        
        ResultSet custrs = db.queryDB(custQuery);
        
        tabCustomer.setDisable(false);
        
        lblCustID.setText(custrs.getString("Customer ID").trim());
        lblCustName.setText(custrs.getString("First Name").trim() + " " + custrs.getString("Last Name").trim());
        lblCustAddress1.setText(custrs.getString("1st Line Address").trim());
        lblCustAddress2.setText(custrs.getString("2nd Line Address"));
        lblCustTown.setText(custrs.getString("Town").trim());
        lblCustPostcode.setText(custrs.getString("Postal Code").trim());
        lblCustPhoneNum.setText(custrs.getString("Phone Number").trim());
        
        //Load warranty details
        String warntQuery = "SELECT * FROM Warranty a, 'Warranty Company' b WHERE a.'Vehicle ID'=" + lblVehID.getText() + " AND a.'Company ID'=b.'Company ID';";
        
        ResultSet warntrs = db.queryDB(warntQuery);
        
        if(warntrs.next()) {
            tabWarranty.setDisable(false);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(warntrs.getString("Expiration Date"));
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            
            lblWarName.setText(warntrs.getString("Company Name").trim());
            lblWarAddress1.setText(warntrs.getString("1st Line Address").trim());
            lblWarAddress2.setText(warntrs.getString("2nd Line Address").trim());
            lblWarTown.setText(warntrs.getString("Town/City").trim());
            lblWarPostcode.setText(warntrs.getString("Post Code").trim());
            lblPhoneNum.setText(warntrs.getString("Phone Number").trim());
            lblWarExpire.setText(format.format(date));
        } else {
            tabWarranty.setDisable(true);
        }
        
        //Load parts
        String partsQuery = "SELECT b.'Name', b.'Description' c.'Date of Booking' AS 'Date Installed', a.'Warranty Expirate Date' FROM 'Vehicle Parts' a, 'Parts' b, 'Bookings' c WHERE a.'Vehicle ID'="
                + lblVehID.getText() +" AND a.'Part ID'=b.'Part ID' AND a.'Booking ID'=c.'BookingID';";
        
        ResultSet partsrs = db.queryDB(partsQuery);
        
        if(partsrs.next()) {
            tabParts.setDisable(false);
            
            for(int i = 0; i < partsrs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                
                TableColumn col = new TableColumn(partsrs.getMetaData().getColumnName(i + 1));
                
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                   
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                             
                        return new SimpleStringProperty(param.getValue().get(j).toString());                       
                    }                   
                });

                partsTable.getColumns().addAll(col);
            }
            
            ObservableList<ObservableList> data = FXCollections.observableArrayList();
            
            //Add data to observable list
            while(partsrs.next()) {
                //Iterate rows
                ObservableList<String> row = FXCollections.observableArrayList();
                
                for(int i = 1; i <= partsrs.getMetaData().getColumnCount(); i++) {
                    if(i == 4) {
                        Long date = partsrs.getLong(i);
                        date = DateUtils.sec2milli(date);
                        Date d = DateUtils.str2date(date.toString());
                        row.add(DateUtils.date2str(d));
                    } else {
                        row.add(partsrs.getString(i).trim());
                    }  
                }
                
                data.add(row);
            }
            
            //Add data to table
            partsTable.setItems(data);
        } else {
            tabParts.setDisable(true);
        }
        //Load bookings
        
    }
    
}
