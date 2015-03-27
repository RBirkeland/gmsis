package customer;

import java.util.ArrayList;

/**
 * Created by Rene Birkeland on 26.03.2015.
 */
public class myVehicle {

    public myVehicle() {
        this.parts = new ArrayList<Integer>();
        this.bookings = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Integer> parts) {
        this.parts = parts;
    }

    public ArrayList<Integer> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Integer> bookings) {
        this.bookings = bookings;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public void addPart(int p) {
        parts.add(p);
    }

    public void addBooking(int b) {
        bookings.add(b);
    }

    private int vehicle;
    private ArrayList<Integer> parts;
    private ArrayList<Integer> bookings;
    private int customer;


}
