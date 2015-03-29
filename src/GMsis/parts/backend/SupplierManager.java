package GMsis.parts.backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lib.DBConn;
import GMsis.parts.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by filipt on 2/17/15.
 */

public class SupplierManager {

    private ObservableList<Supplier> suppliers;
    private ObservableList<PartOrder> partsOnOrder;
    private DBConn db;

    public SupplierManager() {
        db = DBConn.getInstance();
        ResultSet ids = db.queryDB("SELECT \"Supplier ID\" FROM Suppliers;");
        ResultSet orderIds = db.queryDB("SELECT \"Order ID\" FROM \"Parts on Order\";");
        suppliers = FXCollections.observableArrayList();
        partsOnOrder = FXCollections.observableArrayList();

        try {
            while (ids.next()) {
                suppliers.add(new Supplier(ids.getInt("Supplier ID")));
            }

            while (orderIds.next()) {
//                int id = orderIds.getInt("Part ID");
                partsOnOrder.add(new PartOrder(orderIds.getInt("Order ID")));
            }


        } catch (SQLException e) {
            System.out.println("Failure getting Supplier id's");
            e.printStackTrace();
        }
    }

    public void addSupplier(int id, String name, String firstAddress, String secondAddress,
                            String city, String postCode, int phoneNumber) {

        String values = String.format("%d, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', %d",
                                       id, name, firstAddress, secondAddress, city,
                                       postCode, phoneNumber);
        String sql = String.format("INSERT INTO Suppliers VALUES (%s);", values);
        db.updateDB(sql);
        //The constructor will fetch from the database where data was just written
        suppliers.add(new Supplier(id));
    }

    //for editing
    public Supplier getSupplier(int id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getID() == id) {
                return supplier;
            }
        }

        return null;
    }

    public void deleteSupplier(Supplier supplier) {
        deleteSupplier(supplier.getID());

    }

    public void deleteSupplier(int id) {
        String sql = String.format("DELETE FROM Suppliers WHERE \"Supplier ID\" = %d;", id);
        db.updateDB(sql);

        for (Supplier supplier : suppliers) {
            if (supplier.getID() == id) {
                suppliers.remove(supplier);
                break;
            }
        }

    }

    public void createSummaryList() {

    }

    public boolean supplierExists(Integer id) {
        for (Supplier s : suppliers) {
            if (s.getID().equals(id))
                return true;
        }
        return false;
    }

    public void orderPart(int id, int partID, Date date, int quantity) {
        String sql = String.format("INSERT INTO \"Parts On Order\" VALUES(%d, %d, %d, %d);",
                                    id, partID, DateUtils.milli2sec(date.getTime()), quantity);
        db.updateDB(sql);
        partsOnOrder.add(new PartOrder(id));

    }

    public PartOrder getOrder(Integer id) {
        for (PartOrder po : partsOnOrder) {
            if (po.getPartID().equals(id))
                return po;
        }

        throw new NullPointerException("No such order: " + id);
    }

    public void removeOrder(PartOrder po) {
        removeOrder(po.getPartID());
    }

    public void removeOrder(Integer id) {
        for (PartOrder po : partsOnOrder)
            if (po.getPartID().equals(id)) {
                partsOnOrder.remove(po);
                break;
            }

        db.updateDB("DELETE FROM \"Parts On Order\" WHERE \"Part ID\"=" + id + ";");
    }

    public ObservableList<Supplier> getSuppliers() {
        return suppliers;
    }

    public ObservableList<PartOrder> getPartsOnOrder() {
        return partsOnOrder;
    }
}
