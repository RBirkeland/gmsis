package GMsis.booking;

import javafx.beans.property.*;
import java.sql.Date;
import java.sql.Time;

public class SMB {
    //local variables of sm booking class
    private SimpleIntegerProperty bookingNo;
    private SimpleIntegerProperty customerID;
    private SimpleIntegerProperty vehicleID;
    private SimpleIntegerProperty currentmileage; // on booking
    private boolean ispaid;
    private boolean ismilebase;// true = mile base    false = time base  
    private int mile_inter;// between 10000 and 12000  mile
    //private Date time_inter;//between 1 and 2  years
    private SimpleStringProperty type;// true = MOT     false = service
    private SimpleStringProperty failure;
    private ObjectProperty<Date> date;// open hours  Mon-Fri  9am to 5.3 pm   Sat 9am - 12noon   cant be holidays;
    private ObjectProperty<Time> time;
    private SimpleIntegerProperty bayNo;//  6 in total, 1 to 6

    //class constructor need 9 variables  SMB(int,int,int,String,Date,Time,int,int,String)
    public SMB(int ini_bookingNo, int ini_customerID, int ini_vehicleID, String ini_type, Date ini_date, Time ini_time, int ini_mile, int ini_bayNo, String ini_failure) {
        bookingNo = new SimpleIntegerProperty(ini_bookingNo);
        customerID = new SimpleIntegerProperty(ini_customerID);
        vehicleID = new SimpleIntegerProperty(ini_vehicleID);
        type = new SimpleStringProperty(ini_type);
        date = new SimpleObjectProperty<Date>(ini_date);
        time = new SimpleObjectProperty<Time>(ini_time);
        currentmileage = new SimpleIntegerProperty(ini_mile);
        failure = new SimpleStringProperty(ini_failure);
        bayNo = new SimpleIntegerProperty(ini_bayNo);
    }

    public int getBookingNo() {
        return bookingNo.get();
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public int getVehicleID() {
        return vehicleID.get();
    }

    public String getType() {
        return type.get();
    }

    public Date getDate() {
        return date.get();
    }

    public Time getTime() {
        return time.get();
    }

    public int getCurrentmileage() {
        return currentmileage.get();
    }

    public String getFailure() {
        return failure.get();
    }

    public int getBayNo() {
        return bayNo.get();
    }

    public void setFailure(String f) {
        failure.set(f);
    }

    public void addFailure(String f) {
        failure.set(failure + f);
    }

    public String getRenewType()//return "milebase"   or "timebase"  
    {
        if (ismilebase) {
            return "malebase";
        } else {
            return "timebase";
        }
    }

    public void setTime(Date d, Time ini_time) {
        date.set(d);
        time.set(ini_time);
    }
}
