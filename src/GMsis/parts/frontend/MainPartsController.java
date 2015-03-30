package GMsis.parts.frontend;

import java.util.Date;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import GMsis.parts.backend.*;
import GMsis.parts.frontend.forms.OrderFormController;
import GMsis.parts.frontend.forms.PartsFormController;
import GMsis.parts.frontend.forms.SuppliersFormController;
import GMsis.parts.frontend.forms.Form;

import org.controlsfx.dialog.Dialogs;


/**
 * Created by filipt on 3/3/15.
 */
public class MainPartsController implements Initializable{

    private PartsGarage partsGarage;

    @FXML private Accordion mainAccordion;

    @FXML private TableView<Supplier> suppliersTable;
    @FXML private TableView<Part> partsTable;
    @FXML private TableView<PartOrder> partsOnOrder;
    @FXML private TableView<VehiclePart> vehicleParts;

    @FXML private TableColumn<Part, Number> partsID;
    @FXML private TableColumn<Part, Number> partsSupplierID;
    @FXML private TableColumn<Part, String> partsName;
    @FXML private TableColumn<Part, String> partsDescr;
    @FXML private TableColumn<Part, Number> partsStock;
    @FXML private TableColumn<Part, Number> partsCost;

    @FXML private TableColumn<Supplier, Number> supplierID;
    @FXML private TableColumn<Supplier, String> supplierName;
    @FXML private TableColumn<Supplier, String> supplierFirstAddr;
    @FXML private TableColumn<Supplier, String> supplierSecondAddr;
    @FXML private TableColumn<Supplier, String> suppliersTown;
    @FXML private TableColumn<Supplier, String> suppliersPost;
    @FXML private TableColumn<Supplier, Number> suppliersPhone;

    @FXML private TableColumn<PartOrder, Number> orderID;
    @FXML private TableColumn<PartOrder, Number> orderPartID;
    @FXML private TableColumn<PartOrder, String> orderDelivery;
    @FXML private TableColumn<PartOrder, Number> orderQuantity;

    @FXML private TableColumn<VehiclePart, Number> vehicleID;
    @FXML private TableColumn<VehiclePart, Number> vehiclePartID;
    @FXML private TableColumn<VehiclePart, Number> vehicleBookingID;
    @FXML private TableColumn<VehiclePart, String> vehicleWarrantyExp;

    //@FXML private Button repairBtn;
    @FXML private Button addPartBtn;
    @FXML private Button removePartBtn;
    @FXML private Button editPartBtn;

    @FXML private Button addSupplierBtn;
    @FXML private Button deleteSupplierBtn;
    @FXML private Button editSupplierBtn;

    @FXML private Button replacePartBtn;

    @FXML private Button summaryBtn;
    @FXML private Button orderPartBtn;
    @FXML private Button orderRemoveBtn;
    @FXML private Button orderEditBtn;

    private Map<String, Form> subControllers;


    @Override
    public void initialize(URL url, ResourceBundle resources) {

        mainAccordion.setExpandedPane(mainAccordion.getPanes().get(0));
        partsGarage = new PartsGarage();

        filterOrders();
        assignCellFactories();
        updateTableLists();
        setListeners();

        subControllers = new HashMap<>(3);
        subControllers.put("parts", new PartsFormController(this));
        subControllers.put("suppliers", new SuppliersFormController(this));
        subControllers.put("orders", new OrderFormController(this));
    }

    public PartsGarage getPartsGarage() {
        return partsGarage;
    }

    private void filterOrders() {
        Date now = new Date();

        ObservableList<PartOrder> orders = partsGarage.supplierManager.getPartsOnOrder();

        for (int i = 0; i < orders.size(); i++) {
            PartOrder current = orders.get(i);
            if (current.getExpectedDelivery().compareTo(now) <= 0) {
               current.processOrder(partsGarage.getPartsInStock());
               partsGarage.supplierManager.removeOrder(current);
               i--;
           }
        }
    }

    private void assignCellFactories() {
        //boilerplate ;(
        partsID.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
        partsSupplierID.setCellValueFactory(cellData -> cellData.getValue().supplierIDProperty());
        partsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partsDescr.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        partsStock.setCellValueFactory(cellData -> cellData.getValue().currentStockProperty());
        partsCost.setCellValueFactory(cellData -> cellData.getValue().costProperty());

        supplierID.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
        supplierName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        supplierFirstAddr.setCellValueFactory(cellData -> cellData.getValue().firstLineAddressProperty());
        supplierSecondAddr.setCellValueFactory(cellData -> cellData.getValue().secondLineAddressProperty());
        suppliersTown.setCellValueFactory(cellData -> cellData.getValue().cityProperty());
        suppliersPost.setCellValueFactory(cellData -> cellData.getValue().postCodeProperty());
        suppliersPhone.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());

        vehicleID.setCellValueFactory(cellData -> cellData.getValue().vehicleIDProperty());
        vehiclePartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        vehicleBookingID.setCellValueFactory(cellData -> cellData.getValue().bookingIDProperty());
        vehicleWarrantyExp.setCellValueFactory(cellData -> cellData.getValue().warrantyExpirationPropertyToStringProperty());

        orderID.setCellValueFactory(cellData -> cellData.getValue().IDProperty());
        orderPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        orderDelivery.setCellValueFactory(cellData -> cellData.getValue().deliveryToStringProperty());
        orderQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());

    }

    public void updateTableLists() {
        suppliersTable.setItems(partsGarage.supplierManager.getSuppliers());
        partsOnOrder.setItems(partsGarage.supplierManager.getPartsOnOrder());
        partsTable.setItems(partsGarage.getPartsInStock());
        vehicleParts.setItems(partsGarage.getVehicleParts());
    }

    private void setListeners() {

        replacePartBtn.setOnAction((event) -> {
            VehiclePart selected = vehicleParts.getSelectionModel().getSelectedItem();

            if (selected == null) {
                alertNotSelected();
                return;
            }

            Part corresponding = null;

            for (Part p : partsGarage.getPartsInStock()) {
                if (p.getID().equals(selected.getPartID())) {
                    corresponding = p;
                    break;
                }
            }

            if (corresponding == null) {
                Dialogs.create()
                        .title("Notification")
                        .message("There is no corresponding part type in the garage")
                        .showInformation();

                return;
            }

            if (corresponding.getCurrentStock() < 1){
                Dialogs.create()
                        .title("Notification")
                        .masthead("There are no more parts of this type in stock.")
                        .message("The part needs to be ordered, use the Parts On Order table to achieve this.")
                        .showWarning();
            }

            corresponding.acc(-1);
            partsGarage.replaceVehiclePart(selected);
            updateTableLists();
        });

        addPartBtn.setOnAction((event) -> {
            subControllers.get("parts").show();
        } );
        addSupplierBtn.setOnAction((event) -> {
            subControllers.get("suppliers").show();
        });
        orderPartBtn.setOnAction((event) -> {
            subControllers.get("orders").show();
        });

        editPartBtn.setOnAction((event) -> {
            Part toEdit = partsTable.getSelectionModel().getSelectedItem();

            try {
                subControllers.get("parts").show(toEdit.getID());
            } catch (NullPointerException npe) {
                alertNotSelected();
            }
        });

        editSupplierBtn.setOnAction((event) -> {
            Supplier toEdit = suppliersTable.getSelectionModel().getSelectedItem();

            try {
                subControllers.get("suppliers").show(toEdit.getID());
            } catch (NullPointerException e) {
                alertNotSelected();
            }
        });

        orderEditBtn.setOnAction((event) -> {
             PartOrder toEdit = partsOnOrder.getSelectionModel().getSelectedItem();

             if (toEdit == null) {
                 System.out.println("It's null aaarhghghg it burnzz");
             }
             
            try {
                //System.out.println("fail");
                subControllers.get("orders").show(toEdit.getID());
            } catch (NullPointerException e) {
                alertNotSelected();
            }
        });

        removePartBtn.setOnAction((event) -> {
            Part toRemove = partsTable.getSelectionModel().getSelectedItem();

            if (toRemove == null) {
                alertNotSelected();
                return;
            }

            partsGarage.deletePart(toRemove);
            updateTableLists();
        });

        deleteSupplierBtn.setOnAction((event) -> {
            Supplier toRemove = suppliersTable.getSelectionModel().getSelectedItem();

            if (toRemove == null) {
                alertNotSelected();
                return;
            }

            partsGarage.supplierManager.deleteSupplier(toRemove);
            updateTableLists();
        });

        summaryBtn.setOnAction((event) -> {
                partsGarage.supplierManager.createSummaryList();
        });

        orderRemoveBtn.setOnAction((event) -> {
            PartOrder toRemove = partsOnOrder.getSelectionModel().getSelectedItem();

            if (toRemove == null) {
                alertNotSelected();
                return;
            }

            partsGarage.supplierManager.removeOrder(toRemove);
            updateTableLists();
        });


    }

    private void alertNotSelected() {
        Dialogs.create()
                .title("Error Message")
                .message("You have not selected any record in the table.")
                .showInformation();
    }

}
