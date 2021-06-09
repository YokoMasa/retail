package com.retailapp.proto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WholesalePrice {

    public static final int DEFAULT_PRICE = 1;
    public static final int NON_DEFAULT_PRICE = 0;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String jan;
    private double wholesalePrice;
    private String conditions;
    private double netWholesalePrice;
    private int defaultPriceFlag;

    public boolean isDefaultPrice() {
        return defaultPriceFlag == DEFAULT_PRICE;
    }

    public int getDefaultPriceFlag() {
        return this.defaultPriceFlag;
    }

    public void setDefaultPriceFlag(int defaultPriceFlag) {
        this.defaultPriceFlag = defaultPriceFlag;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJan() {
        return this.jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public double getWholesalePrice() {
        return this.wholesalePrice;
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public String getConditions() {
        return this.conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public double getNetWholesalePrice() {
        return this.netWholesalePrice;
    }

    public void setNetWholesalePrice(double netWholesalePrice) {
        this.netWholesalePrice = netWholesalePrice;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", jan='" + getJan() + "'" +
            ", wholesalePrice='" + getWholesalePrice() + "'" +
            ", conditions='" + getConditions() + "'" +
            ", netWholesalePrice='" + getNetWholesalePrice() + "'" +
            ", defaultPriceFlag='" + getDefaultPriceFlag() + "'" +
            "}";
    }

}
