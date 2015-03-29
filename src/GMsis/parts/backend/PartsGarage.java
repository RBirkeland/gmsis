package GMsis.parts.backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lib.DBConn;
import GMsis.parts.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by filipt on 2/17/15.
 */

public class PartsGarage {

    public SupplierManager supplierManager;
    private ObservableList<Part> partsInStock;
    private ObservableList<VehiclePart> vehicleParts;

    private int bookingMax;

    private final DBConn db = DBConn.getInstance();

    public PartsGarage() {
        supplierManager = new SupplierManager();
        partsInStock = FXCollections.observableArrayList();
        vehicleParts = FXCollections.observableArrayList();
        bookingMax = 0;

        ResultSet partIds = db.queryDB("SELECT \"Part ID\" FROM Parts;");
        ResultSet vehiclePartIds = db.queryDB("SELECT \"Vehicle Part ID\" FROM \"Vehicle Parts\";");

        try {
            while (partIds.next()) {
                partsInStock.add(new Part(partIds.getInt("Part ID")));
            }

            while (vehiclePartIds.next()) {
                VehiclePart vp = new VehiclePart(vehiclePartIds.getInt("Vehicle Part ID"));
                if (bookingMax < vp.getBookingID())
                    bookingMax = vp.getBookingID();
                vehicleParts.add(vp);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<VehiclePart> getVehicleParts() {
        return vehicleParts;
    }

    public void deletePart(int id) {
        String sql = String.format("DELETE FROM Suppliers WHERE \"Supplier ID\" = %d;", id);
        db.updateDB(sql);

        for (Part part : partsInStock) {
            if (part.getID() == id) {
                partsInStock.remove(part);
                break;
            }
        }
    }

    public Part getPart(int id) {
        for (Part p : partsInStock) {
            if (p.getID() == id) {
                return p;
            }
        }

        throw new NullPointerException("Part with id " + id + " not found");
    }

    public boolean partExists(Integer id) {

        for (Part p: partsInStock)
            if(p.getID().equals(id))
                return true;
        return false;
    }

    public void replaceVehiclePart(VehiclePart toReplace) {

        for (int i = 0; i < vehicleParts.size(); i++) {
            VehiclePart current = vehicleParts.get(i);
            if (current.getID().equals(toReplace.getID())) {
                String sql = "UPDATE \"Vehicle Parts\" SET \"Vehicle ID\"=%d, \"Part ID\"=%d, \"Booking ID\"=%d, " +
                        "\"Warranty Expiration Date\"=%d WHERE \"Vehicle Part ID\"=" + toReplace.getID() + ";";
                Date d = new Date();
                d.setYear(d.getYear() + 1);
                long seconds = DateUtils.milli2sec(d.getTime());
                sql = String.format(sql, toReplace.getVehicleID(), toReplace.getPartID(), ++bookingMax, seconds);
                db.updateDB(sql);
                vehicleParts.set(i, new VehiclePart(toReplace.getID()));
                return;
            }
        }

        throw new RuntimeException("No vehicle part found that would have same id and booking id");
    }

    public void deletePart(Part toRemove) {
        deletePart(toRemove.getID());
    }

    public ObservableList<Part> getPartsInStock() {
        return partsInStock;
    }

    public void addPart(int id, int supplierID, String name, String desc, int stock, double cost) {
        String values = String.format("%d, %d, \'%s\', \'%s\', %d, %f",
                id, supplierID, name, desc,
                stock, cost);
        String sql = String.format("INSERT INTO Parts VALUES (%s);", values);
        db.updateDB(sql);

        partsInStock.add(new Part(id));
    }


}
