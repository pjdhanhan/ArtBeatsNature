package com.jzd.artbeatsnature.Bean;

/**
 * Created by Wxd on 2017-07-24.
 */

public class RecordLsBean {

    /**
     * Id : 82
     * Abbreviation : 简称60
     * ShipAddress : 山东青岛60
     * MaintenTime : 2017-07-24 11:54:00
     * MaintenPerson : A0003,A0002,A0001
     * Summary : 敏喔:-O
     * Pictures : 20170724115407.jpg
     * SignFile : 20170724115354.png
     * AuditStatus : 0
     * Cid : 60
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

    @Override
    public String toString() {
        return "RecordLsBean{" +
                "Id='" + Id + '\'' +
                ", Abbreviation='" + Abbreviation + '\'' +
                ", ShipAddress='" + ShipAddress + '\'' +
                ", MaintenTime='" + MaintenTime + '\'' +
                ", MaintenPerson='" + MaintenPerson + '\'' +
                ", Summary='" + Summary + '\'' +
                ", Pictures='" + Pictures + '\'' +
                ", SignFile='" + SignFile + '\'' +
                ", AuditStatus='" + AuditStatus + '\'' +
                ", Cid='" + Cid + '\'' +
                '}';
    }
}
