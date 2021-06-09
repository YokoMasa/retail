package com.retailapp.proto.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class WholesaleEstimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne
    private Retailer retailer;

    @OneToOne
    private Manufacturer manufacturer;

    private String title;

    private Date estimateDate;

    @OneToMany
    @JoinColumn(name = "wholesale_estimate_id")
    private List<WholesaleEstimateDetail> details;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Retailer getRetailer() {
        return this.retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEstimateDate() {
        return this.estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public List<WholesaleEstimateDetail> getDetails() {
        return this.details;
    }

    public void addDetail(WholesaleEstimateDetail detail) {
        if (details == null) {
            details = new ArrayList<>();
        }
        details.add(detail);
    }

    public void setDetails(List<WholesaleEstimateDetail> details) {
        this.details = details;
    }

}
