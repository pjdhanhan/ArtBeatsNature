package com.jzd.artbeatsnature.Bean;

import java.io.Serializable;

/**
 * Created by Wxd on 2017-07-22.
 */

public class RecordBean implements Serializable{

    /**
     * Id : 79
     * Abbreviation : 简称55
     * ShipAddress : 山东青岛55
     * MaintenTime : 2017-07-22 13:49:00
     * MaintenPerson : A0002
     * Summary : 你今年
     * Pictures : 20170722134921.jpg
     * SignFile : 20170722134911.png
     * AuditStatus : 0
     * Cid : 55
     */

    private String Id;
    private String Abbreviation;
    private String ShipAddress;
    private String MaintenTime;
    private String MaintenPerson;
    private String Summary;
    private String Pictures;
    private String SignFile;
    private String AuditStatus;
    private String Cid;

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

    public String getMaintenTime() {
        return MaintenTime;
    }

    public void setMaintenTime(String MaintenTime) {
        this.MaintenTime = MaintenTime;
    }

    public String getMaintenPerson() {
        return MaintenPerson;
    }

    public void setMaintenPerson(String MaintenPerson) {
        this.MaintenPerson = MaintenPerson;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getPictures() {
        return Pictures;
    }

    public void setPictures(String Pictures) {
        this.Pictures = Pictures;
    }

    public String getSignFile() {
        return SignFile;
    }

    public void setSignFile(String SignFile) {
        this.SignFile = SignFile;
    }

    public String getAuditStatus() {
        return AuditStatus;
    }

    public void setAuditStatus(String AuditStatus) {
        this.AuditStatus = AuditStatus;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String Cid) {
        this.Cid = Cid;
    }
}
