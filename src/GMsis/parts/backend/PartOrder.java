package GMsis.parts.backend;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import lib.DBConn;
import GMsis.parts.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by filipt on 2/17/15.
 */
public class PartOrder {

    private IntegerProperty ID;
    private IntegerProperty partID;
    private ObjectProperty<Date> expectedDelivery;
    private IntegerProperty quantity;


//Because java date class accepts time in milliseconds whereas SQLite deals with seconds
    private final String PART_ID_COL = "Part ID";
    private final String DATE_COL = "Expected Delivery";
    private final String QUANTITY_COL = "Quantity";

    private DatabaseRecord orderRecord;

    public PartOrder(int ID) {
        this.ID = new SimpleIntegerProperty();
        this.partID = new SimpleIntegerProperty();
        expectedDelivery = new SimpleObjectProperty<>();
        quantity = new SimpleIntegerProperty();
        this.ID.set(ID);

        orderRecord = new DatabaseRecord(ID, "Parts on Order", "Order ID");
        ResultSet rs = DBConn.getInstance().queryDB(String.format("SELECT * FROM \"Parts on Order\" WHERE \"Order ID\"=%d;", ID));

        try {
            this.partID.set(rs.getInt(PART_ID_COL));
            long millis = DateUtils.sec2milli(rs.getLong(DATE_COL));
            expectedDelivery.set(new Date(millis));
            this.quantity.set(rs.getInt(QUANTITY_COL));
        } catch (SQLException e) {
            System.out.println("Error loading expected delivery");
            e.printStackTrace();
        }
    }

    public Integer getPartID() {
        return partID.get();
    }


    public void setDate(Date date) {
        expectedDelivery.set(date);
        orderRecord.writeCell(DATE_COL, date);
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }

    public Date getExpectedDelivery() {
        return expectedDelivery.get();
    }

    public ObjectProperty<Date> expectedDeliveryProperty() {
        return expectedDelivery;
    }

    public StringProperty deliveryToStringProperty() {
        return new SimpleStringProperty(DateUtils.date2str(expectedDelivery.get()));
    }

    public void processOrder(ObservableList<Part> parts) {
        for (Part part : parts) {
            if (part.getID().equals(partID.get())) {
                part.acc(quantity.get());
                break;
            }
        }
    }

    public Integer getID() {
        return ID.get();
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public void setQuantity(int quantity) {
        orderRecord.writeCell(QUANTITY_COL, quantity);
        this.quantity.set(quantity);
    }

    public Integer getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

}
