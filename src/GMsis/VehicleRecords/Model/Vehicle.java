/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VehicleRecords.Model;

/**
 * Vehicle model class for storing vehicle data from the database
 * 
 * @author Rhys Field
 */
public class Vehicle {
    
    //Vehicle properties
    private String vehType;
    private Integer vehID;
    private Integer customerID;
    private String vehMake;
    private String vehModel;
    private String vehEngineSize;
    private String vehFuelType;
    private String vehColour;
    private String vehMOTRenewal;
    private String vehLastService;
    private Integer vehMileage;
    
    public Vehicle() {
        
    }
    
    public String getType() {
        return vehType;
    }
    
    public void setType(String type) {
        this.vehType = type;
    }

    public Integer getVehID() {
        return vehID;
    }

    public void setVehID(Integer vehID) {
        this.vehID = vehID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getVehMake() {
        return vehMake;
    }

    public void setVehMake(String vehMake) {
        this.vehMake = vehMake;
    }

    public String getVehModel() {
        return vehModel;
    }

    public void setVehModel(String vehModel) {
        this.vehModel = vehModel;
    }

    public String getVehEngineSize() {
        return vehEngineSize;
    }

    public void setVehEngineSize(String vehEngineSize) {
        this.vehEngineSize = vehEngineSize;
    }

    public String getVehFuelType() {
        return vehFuelType;
    }

    public void setVehFuelType(String vehFuelType) {
        this.vehFuelType = vehFuelType;
    }

    public String getVehColour() {
        return vehColour;
    }

    public void setVehColour(String vehColour) {
        this.vehColour = vehColour;
    }

    public String getVehMOTRenewal() {
        return vehMOTRenewal;
    }

    public void setVehMOTRenewal(String vehMOTRenewal) {
        this.vehMOTRenewal = vehMOTRenewal;
    }

    public String getVehLastService() {
        return vehLastService;
    }

    public void setVehLastService(String vehLastService) {
        this.vehLastService = vehLastService;
    }

    public Integer getVehMileage() {
        return vehMileage;
    }

    public void setVehMileage(Integer vehMileage) {
        this.vehMileage = vehMileage;
    }
     
}
