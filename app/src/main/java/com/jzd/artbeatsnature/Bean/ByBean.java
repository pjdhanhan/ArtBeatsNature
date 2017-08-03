package com.jzd.artbeatsnature.Bean;

import java.io.Serializable;

/**
 * Created by Wxd on 2017-07-19.
 */

public class ByBean implements Serializable{

    /**
     * Id : 52
     * Abbreviation : 简称52
     * ShipAddress : 山东青岛52
     * ContactNumber : 13853249915
     * ShipDate : 2017-07-20
     * MaintenTime : 2017-07-22
     */

    private String Id;
    private String Abbreviation;
    private String ShipAddress;
    private String ContactNumber;
    private String ShipDate;
    private String MaintenTime;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getAbbreviation() {
        return Abbreviation;
    }

    public void setAbbreviation(String Abbreviation) {
        this.Abbreviation = Abbreviation;
    }

    public String getShipAddress() {
        return ShipAddress;
    }

    public void setShipAddress(String ShipAddress) {
        this.ShipAddress = ShipAddress;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String ShipDate) {
        this.ShipDate = ShipDate;
    }

    public String getMaintenTime() {
        return MaintenTime;
    }

    public void setMaintenTime(String MaintenTime) {
        this.MaintenTime = MaintenTime;
    }
}
