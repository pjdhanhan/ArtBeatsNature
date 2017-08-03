package com.jzd.artbeatsnature.Bean;

import java.util.List;

/**
 * Created by Wxd on 2017-07-20.
 */

public class KhInforationt {

    /**
     * ShipDate : 2017-07-19 00:00:00
     * Salesman : 业务员60
     * lsgood : [{"InventoryCode":"","InventoryName":"货物60","Specification":"10X25","InventoryNumber":100,"TaxPrice":1000,"MaintenTime":"2017-07-19 17:37:19"},{"InventoryCode":"","InventoryName":"货物59","Specification":"6X25","InventoryNumber":100,"TaxPrice":1000,"MaintenTime":"2017-07-19 17:37:19"},{"InventoryCode":"","InventoryName":"货物58","Specification":"2X21","InventoryNumber":100,"TaxPrice":1000,"MaintenTime":"2017-07-19 17:37:19"}]
     */

    private String ShipDate;
    private String Salesman;
    private List<LsgoodBean> lsgood;

    public String getShipDate() {
        return ShipDate;
    }

    public void setShipDate(String ShipDate) {
        this.ShipDate = ShipDate;
    }

    public String getSalesman() {
        return Salesman;
    }

    public void setSalesman(String Salesman) {
        this.Salesman = Salesman;
    }

    public List<LsgoodBean> getLsgood() {
        return lsgood;
    }

    public void setLsgood(List<LsgoodBean> lsgood) {
        this.lsgood = lsgood;
    }

    public  class LsgoodBean {
        /**
         * InventoryCode :
         * InventoryName : 货物60
         * Specification : 10X25
         * InventoryNumber : 100
         * TaxPrice : 1000.0
         * MaintenTime : 2017-07-19 17:37:19
         */

        private String InventoryCode;
        private String InventoryName;
        private String Specification;
        private String InventoryNumber;
        private double TaxPrice;
        private String MaintenTime;

        public String getInventoryCode() {
            return InventoryCode;
        }

        public void setInventoryCode(String InventoryCode) {
            this.InventoryCode = InventoryCode;
        }

        public String getInventoryName() {
            return InventoryName;
        }

        public void setInventoryName(String InventoryName) {
            this.InventoryName = InventoryName;
        }

        public String getSpecification() {
            return Specification;
        }

        public void setSpecification(String Specification) {
            this.Specification = Specification;
        }

        public String getInventoryNumber() {
            return InventoryNumber;
        }

        public void setInventoryNumber(String InventoryNumber) {
            this.InventoryNumber = InventoryNumber;
        }

        public double getTaxPrice() {
            return TaxPrice;
        }

        public void setTaxPrice(double TaxPrice) {
            this.TaxPrice = TaxPrice;
        }

        public String getMaintenTime() {
            return MaintenTime;
        }

        public void setMaintenTime(String MaintenTime) {
            this.MaintenTime = MaintenTime;
        }
    }
}
