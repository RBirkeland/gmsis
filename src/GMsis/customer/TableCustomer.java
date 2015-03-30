/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GMsis.customer;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rene
 */
public class TableCustomer {
    
    private SimpleStringProperty ID = new SimpleStringProperty();
    private SimpleStringProperty firstName = new SimpleStringProperty();
    private SimpleStringProperty lastName = new SimpleStringProperty();
    
    TableCustomer(String id, String fn, String ln) {
        ID = new SimpleStringProperty(id);
        firstName = new SimpleStringProperty(fn);
        lastName = new SimpleStringProperty(ln);
    }
    
     public String getID() {
        return ID.get();
    }

    public SimpleStringProperty IDProperty() {
        return ID;
    }

    public void setCustomer(String id) {
        this.ID.set(id);
    }
    
     public String getfirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String fn) {
        this.firstName.set(fn);
    }
    
     public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String ln) {
        this.lastName.set(ln);
    }
    
    
}
