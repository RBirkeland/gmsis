package GMsis.parts.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by filipt on 2/17/15.
 */

//Package-private
public class Supplier {

    private IntegerProperty ID;
    private StringProperty name;
    private StringProperty firstLineAddress;
    private StringProperty secondLineAddress;
    private StringProperty city;
    private StringProperty postCode;
    private IntegerProperty phoneNumber;

    private final String NAME_COL = "Supplier Name";
    private final String FIRST_ADDR_COL = "1st Line Address";
    private final String SECOND_ADDR_COL = "2nd Line Address";
    private final String CITY_COL = "Town/City";
    private final String POST_COL = "Post Code";
    private final String PHONE_COL = "Phone Number";

    private DatabaseRecord supplierRecord;

    public Supplier(int ID) {
        this.ID = new SimpleIntegerProperty(ID);
        supplierRecord = new DatabaseRecord(ID, "Suppliers", "Supplier ID");
        ResultSet rs = supplierRecord.getCells();

        try {
            name = new SimpleStringProperty(rs.getString(NAME_COL));
            firstLineAddress = new SimpleStringProperty(rs.getString(FIRST_ADDR_COL));
            secondLineAddress = new SimpleStringProperty(rs.getString(SECOND_ADDR_COL));
            city = new SimpleStringProperty(rs.getString(CITY_COL));
            postCode= new SimpleStringProperty(rs.getString(POST_COL));
            phoneNumber = new SimpleIntegerProperty(rs.getInt(PHONE_COL));
        } catch (SQLException e) {
            System.out.println("Failure to read data from Suppliers table");
            e.printStackTrace();
        }
    }

    public Integer getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
        supplierRecord.writeCell(NAME_COL, name);
    }

    public String getFirstLineAddress() {
        return firstLineAddress.get();
    }

    public void setFirstLineAddress(String firstLineAddress) {
        this.firstLineAddress.set(firstLineAddress);
        supplierRecord.writeCell(FIRST_ADDR_COL, firstLineAddress);
    }

    public String getSecondLineAddress() {
        return secondLineAddress.get();
    }

    public void setSecondLineAddress(String secondLineAddress) {
        this.secondLineAddress.set(secondLineAddress);
        supplierRecord.writeCell(SECOND_ADDR_COL, secondLineAddress);
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
        supplierRecord.writeCell(CITY_COL, city);
    }

    public String getPostCode() {
        return postCode.get();
    }

    public void setPostCode(String postCode) {
        this.postCode.set(postCode);
        supplierRecord.writeCell(POST_COL, postCode);
    }

    public Integer getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
        supplierRecord.writeCell(PHONE_COL, phoneNumber);
    }

    public IntegerProperty IDProperty() {
        return ID;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty firstLineAddressProperty() {
        return firstLineAddress;
    }

    public StringProperty secondLineAddressProperty() {
        return secondLineAddress;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty postCodeProperty() {
        return postCode;
    }

    public IntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }
}
