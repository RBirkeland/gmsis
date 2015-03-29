package GMsis.parts.backend;

import javafx.beans.property.*;
import lib.DBConn;
import GMsis.parts.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by filipt on 26/03/15.
 */
public class VehiclePart {

    private Integer ID;

    private IntegerProperty vehicleID;
    private IntegerProperty partID;
    private IntegerProperty bookingID;
    private ObjectProperty<Date> warrantyExpiration;

    private DatabaseRecord record;

    private final String PART_ID_COL = "Part ID";
    private final String VEHICLE_ID_COL = "Vehicle ID";
    private final String BOOKING_ID_COL = "Booking ID";
    private final String EXP_COL = "Warranty Expiration Date";

    public VehiclePart(int ID) {
        record = new DatabaseRecord(ID, "Vehicle Parts", "Vehicle Part ID");

        this.ID = ID;
        this.vehicleID = new SimpleIntegerProperty();
        this.partID = new SimpleIntegerProperty();
        this.bookingID = new SimpleIntegerProperty();
        this.warrantyExpiration = new SimpleObjectProperty<>();

        ResultSet rs = record.getCells();


        try {
            this.bookingID.set(rs.getInt(BOOKING_ID_COL));
            this.partID.set(rs.getInt(PART_ID_COL));
            this.vehicleID.set(rs.getInt(VEHICLE_ID_COL));
            long millis = DateUtils.sec2milli(rs.getLong(EXP_COL));
            this.warrantyExpiration.set(new Date(millis));
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error loading from Vehicle Parts table of db");
        }
    }

    public void setWarrantyExpiration(Date warrantyExpiration) {
        this.warrantyExpiration.set(warrantyExpiration);
        record.writeCell(EXP_COL, warrantyExpiration);
    }

    public void setBookingID(int bookingID) {
        this.bookingID.set(bookingID);
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public Integer getID() {
        return ID;
    }

    public IntegerProperty vehicleIDProperty() {
        return vehicleID;
    }

    public Integer getVehicleID() {
        return vehicleID.get();
    }

    public Integer getPartID() {
        return partID.get();
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }

    public Integer getBookingID() {
        return bookingID.get();
    }

    public IntegerProperty bookingIDProperty() {
        return bookingID;
    }

    public Date getWarrantyExpiration() {
        return warrantyExpiration.get();
    }

    public ObjectProperty<Date> warrantyExpirationProperty() {
        return warrantyExpiration;
    }

    public StringProperty warrantyExpirationPropertyToStringProperty() {
        return new SimpleStringProperty(DateUtils.date2str(warrantyExpiration.get()));
    }
}
