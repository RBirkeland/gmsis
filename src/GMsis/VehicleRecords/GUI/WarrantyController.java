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
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.DialogStyle;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author Rhys Field
 */
public class WarrantyController implements Initializable {
    
    @FXML private TextField txtCompany;
    @FXML private TextField txtAddress1;
    @FXML private TextField txtTown;
    @FXML private TextField txtAddress2;
    @FXML private TextField txtPostCode;
    @FXML private TextField txtPhone;
    @FXML private Button btnNew;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;
    @FXML private TableView tblWarranty;
    @FXML private Pane paneDetails;
    @FXML private StackPane paneTable;
    
    private Stage dialogStage;
    private boolean saveClicked = false;
    private boolean newRecord = false;
    private String editID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        //Load warranty table
        UpdateTableView.buildData(tblWarranty, "SELECT \"Company ID\", \"Company Name\", \"Phone Number\" FROM \"Warranty Company\";");
    }
    
    @FXML
    public void newWarranty() {
        paneTable.setVisible(false);
        paneDetails.setVisible(true);
        newRecord = true;
    }
    
    @FXML
    public void editWarranty() {
        if(tblWarranty.getSelectionModel().getSelectedItems().get(0) != null) {
            //Get companys details
            ObservableList<String> compID = (ObservableList<String>) tblWarranty.getSelectionModel().getSelectedItems().get(0);
            editID = compID.get(0);
            String warrantyQuery = "SELECT * FROM \"Warranty Company\" WHERE \"Company ID\"=" + compID.get(0) +";";
            
            //Load details into pane
            try {
                DBConn db = DBConn.getInstance();
                db.getConn();
                ResultSet warrs = db.queryDB(warrantyQuery);
                
                txtCompany.setText(warrs.getString("Company Name").trim());
                txtAddress1.setText(warrs.getString("1st Line Address").trim());
                txtAddress2.setText(warrs.getString("2nd Line Address"));
                txtTown.setText(warrs.getString("Town/City").trim());
                txtPostCode.setText(warrs.getString("Post Code").trim());
                txtPhone.setText(warrs.getString("Phone Number").trim());
                
            } catch(SQLException e) {
                System.out.println(e);
            }
            
            paneTable.setVisible(false);
            paneDetails.setVisible(true);
        }
    }
    
    @FXML
    public void handleSave() {
        // Keep check on if save is valid
        boolean save = true;
        
        // Error Checking
        if(!ErrorChecking.checkString("Company Name", txtCompany.getText().trim(), 50, dialogStage)) {
            save = false;
        }
        if(save && !ErrorChecking.checkString("1st Line Address", txtAddress1.getText().trim(), 50, dialogStage)) {
            save = false;
        }
        if(save && !ErrorChecking.simpleCheckString("2nd Line Address", txtAddress2.getText().trim(), 50, dialogStage)) {
            save = false;
        }
        if(save && !ErrorChecking.checkString("Town/City", txtTown.getText().trim(), 30, dialogStage)) {
            save = false;
        }
        if(save && !ErrorChecking.postcodeCheck(txtPostCode.getText().trim(), dialogStage)) {
            save = false;
        }
        if(save && !ErrorChecking.phoneNumberCheck(txtPhone.getText().trim(), dialogStage)) {
            save = false;
        }
        
        DBConn db = DBConn.getInstance();
        db.getConn();
        
        // If save is valid enter into database
        if(save && newRecord) {
            String insert = "INSERT INTO \"Warranty Company\" "
                    + "('Company Name', '1st Line Address', '2nd Line Address', 'Town/City', 'Post Code', 'Phone Number')"
                    + "VALUES (\"" + 
                    txtCompany.getText().trim() + "\", \"" + 
                    txtAddress1.getText().trim() + "\", \"" + 
                    txtAddress2.getText() + "\", \"" + 
                    txtTown.getText().trim() + "\", \"" + 
                    txtPostCode.getText().trim() + "\", \"" + 
                    txtPhone.getText().trim() + "\");";
            
           
            db.updateDB(insert);
            
            // Close window
            this.dialogStage.close();
        } else if(save) {
            String update = "UPDATE \"Warranty Company\" "
                    + "SET 'Company Name'='" + txtCompany.getText().trim() 
                    + "', '1st Line Address'='" + txtAddress1.getText().trim()
                    + "', '2nd Line Address'='" + txtAddress2.getText()
                    + "', 'Town/City'='" + txtTown.getText().trim()
                    + "', 'Post Code'='" + txtPostCode.getText().trim()
                    + "', 'Phone Number'='" + txtPhone.getText().trim()
                    + "' WHERE 'Warranty Company'.'Company ID'=" + editID + ";";
            
            db.updateDB(update);
            
            // Close window
            this.dialogStage.close();
        }
        
    }
    
    @FXML
    public void deleteWarranty() {
        //Get companys details
        ObservableList<String> compID = (ObservableList<String>) tblWarranty.getSelectionModel().getSelectedItems().get(0);

        //Display confirmation window
        Action response = Dialogs.create()
                .owner(dialogStage)
                .style(DialogStyle.NATIVE)
                .title("Delete")
                .message("Are you sure you wish to delete this record.")
                .showConfirm();
        
        // If yes delete the record
        if(response == Dialog.Actions.YES) {
            String delete = "DELETE FROM 'Warranty Company' WHERE 'Warranty Company'.'Company ID'=" + compID.get(0) + ";";
            
            DBConn db = DBConn.getInstance();
            try {
                Statement stmt = db.getConn().createStatement();
                stmt.execute("PRAGMA foreign_keys=ON;");
                stmt.execute(delete);
            } catch(SQLException e) {
                Dialogs.create()
                        .owner(dialogStage)
                        .style(DialogStyle.NATIVE)
                        .title("SQL Error")
                        .masthead("Error trying to delete record")
                        .message("Unable to delete record, another record may be dependent on it.")
                        .showException(e);
            }
            
            
            // Update tableview
            UpdateTableView.buildData(tblWarranty, "SELECT \"Company ID\", \"Company Name\", \"Phone Number\" FROM \"Warranty Company\";");
        }
    }
    
    public boolean isSaveClicked() {
        return saveClicked;
    }
    
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        dialogStage.close();
    }
    
}
