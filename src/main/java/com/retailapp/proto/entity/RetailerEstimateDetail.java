package com.retailapp.proto.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class RetailerEstimateDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderNo;

    @ManyToOne
    private RetailerEstimate retailerEstimate;
    private Date sellingStartDate;
    private String productCategory;
    private String productJan;
    private String productName;
    private String productManufacturer;
    private String productSpecification;
    private double count;
    private double unitPrice;
    private double taxRate;
    private double wholesalePrice;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public RetailerEstimate getRetailerEstimate() {
        return this.retailerEstimate;
    }

    public void setRetailerEstimate(RetailerEstimate retailerEstimate) {
        this.retailerEstimate = retailerEstimate;
    }

    public Date getSellingStartDate() {
        return this.sellingStartDate;
    }

    public void setSellingStartDate(Date sellingStartDate) {
        this.sellingStartDate = sellingStartDate;
    }

    public String getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductJan() {
        return this.productJan;
    }

    public void setProductJan(String productJan) {
        this.productJan = productJan;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductManufacturer() {
        return this.productManufacturer;
    }

    public void setProductManufacturer(String productManufacturer) {
        this.productManufacturer = productManufacturer;
    }

    public String getProductSpecification() {
        return this.productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public double getCount() {
        return this.count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(double price) {
        this.unitPrice = price;
    }

    public double getTaxRate() {
        return this.taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getWholesalePrice() {
        return this.wholesalePrice;
    }

    public void setWholesalePrice(double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

}
