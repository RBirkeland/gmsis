package GMsis.parts.backend;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by filipt on 2/26/15.
 */
public class Part {

    private IntegerProperty ID;
    private IntegerProperty supplierID;
    private StringProperty name;
    private StringProperty description;
    private IntegerProperty currentStock;
    private DoubleProperty cost;

    private final String SUPPLIER_COL = "Supplier ID";
    private final String NAME_COL = "Name";
    private final String DESC_COL = "Description";
    private final String STOCK_COL = "Current Stock";
    private final String COST_COL = "Part Cost";

    private DatabaseRecord partRecord;

    public Part(int ID) {
        this.ID = new SimpleIntegerProperty(ID);
        partRecord = new DatabaseRecord(ID, "Parts", "Part ID");
        ResultSet rs = partRecord.getCells();

        try {
            supplierID  = new SimpleIntegerProperty(rs.getInt(SUPPLIER_COL));
            name = new SimpleStringProperty(rs.getString(NAME_COL));
            description = new SimpleStringProperty(rs.getString(DESC_COL));
            currentStock = new SimpleIntegerProperty(rs.getInt(STOCK_COL));
            cost = new SimpleDoubleProperty(rs.getDouble(COST_COL));
        } catch (SQLException e) {
            System.out.println("Failure to read data from Parts table");
            e.printStackTrace();
        }
    }

    public Double getCost() {
        return cost.get();
    }

    public void acc(int n) {
        setCurrentStock(currentStock.get() + n);
    }

    public void setCost(double cost) {
        this.cost.set(cost);
        partRecord.writeCell(COST_COL, cost);
    }

    public Integer getCurrentStock() {
        return currentStock.get();
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock.set(currentStock);
        partRecord.writeCell(STOCK_COL, currentStock);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
        partRecord.writeCell(DESC_COL, description);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
        partRecord.writeCell(NAME_COL, name);
    }

    public Integer getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public Integer getSupplierID() {
        return supplierID.get();
    }

    public void setSupplierID(int supplierID) {
        this.supplierID.set(supplierID);
        partRecord.writeCell(SUPPLIER_COL, supplierID);
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public IntegerProperty supplierIDProperty() {
        return supplierID;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public IntegerProperty currentStockProperty() {
        return currentStock;
    }

    public DoubleProperty costProperty() {
        return cost;
    }
}
