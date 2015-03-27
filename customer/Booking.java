package GMsis.customer;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Rene Birkeland on 26.03.2015.
 */
public class Booking {

    public Booking(String customer, String bookings, String vehicles, String parts, String date) {
        this.customer = new SimpleStringProperty(customer);
        this.bookings =  new SimpleStringProperty(bookings );
        this.vehicles =  new SimpleStringProperty(vehicles);
        this.parts = new SimpleStringProperty(parts);
        this.date = new SimpleStringProperty(date);
    }

    private SimpleStringProperty customer = new SimpleStringProperty();
    private SimpleStringProperty bookings = new SimpleStringProperty();
    private SimpleStringProperty vehicles = new SimpleStringProperty();
    private SimpleStringProperty parts = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getCustomer() {
        return customer.get();
    }

    public SimpleStringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public String getBookings() {
        return bookings.get();
    }

    public SimpleStringProperty bookingsProperty() {
        return bookings;
    }

    public void setBookings(String bookings) {
        this.bookings.set(bookings);
    }

    public String getVehicles() {
        return vehicles.get();
    }

    public SimpleStringProperty vehiclesProperty() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles.set(vehicles);
    }

    public String getParts() {
        return parts.get();
    }

    public SimpleStringProperty partsProperty() {
        return parts;
    }

    public void setParts(String parts) {
        if(parts == null) this.parts.set(getParts());
        else this.parts.set(getParts() + ", " + parts);
    }
}
