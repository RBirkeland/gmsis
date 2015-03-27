package customer;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 * Created by Rene on 26/03/2015.
 */
public class Account {

    private SimpleStringProperty customer = new SimpleStringProperty();
    private SimpleStringProperty bookings = new SimpleStringProperty();
    private SimpleStringProperty vehicles = new SimpleStringProperty();
    private SimpleStringProperty parts = new SimpleStringProperty();

    public String getCustomer() {
        return customer.get();
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = new SimpleStringProperty(customer);
    }

    public String getBookings() {
        return bookings.get();
    }

    public SimpleStringProperty bookingsProperty() {
        return bookings;
    }

    public void setBookings(String bookings) {
        this.bookings = new SimpleStringProperty(bookings);
    }

    public String getVehicles() {
        return vehicles.get();
    }

    public SimpleStringProperty vehiclesProperty() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = new SimpleStringProperty(vehicles);
    }

    public String getParts() {
        return parts.get();
    }

    public SimpleStringProperty partsProperty() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = new SimpleStringProperty(parts);
    }
}
