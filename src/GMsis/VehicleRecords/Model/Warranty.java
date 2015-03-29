/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecords.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Rhys Field
 */
public class Warranty {
    
    private IntegerProperty companyID;
    private StringProperty companyName;
    private StringProperty address1;
    private StringProperty address2;
    private StringProperty town;
    private StringProperty postCode;
    private StringProperty phoneNumber;
    
    public Warranty() {
        
    }

    public IntegerProperty getCompanyID() {
        return companyID;
    }

    public void setCompanyID(IntegerProperty companyID) {
        this.companyID = companyID;
    }

    public StringProperty getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringProperty companyName) {
        this.companyName = companyName;
    }

    public StringProperty getAddress1() {
        return address1;
    }

    public void setAddress1(StringProperty address1) {
        this.address1 = address1;
    }

    public StringProperty getAddress2() {
        return address2;
    }

    public void setAddress2(StringProperty address2) {
        this.address2 = address2;
    }

    public StringProperty getTown() {
        return town;
    }

    public void setTown(StringProperty town) {
        this.town = town;
    }

    public StringProperty getPostCode() {
        return postCode;
    }

    public void setPostCode(StringProperty postCode) {
        this.postCode = postCode;
    }

    public StringProperty getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringProperty phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
}
