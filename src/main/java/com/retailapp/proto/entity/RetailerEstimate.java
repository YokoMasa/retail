package com.retailapp.proto.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class RetailerEstimate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    @JoinColumn(name = "retailer_estimate_id")
    @OrderBy("orderNo ASC")
    private List<RetailerEstimateDetail> details;

    @ManyToOne
    private Retailer retailer;
    private String address;
    private Date issueDate;
    private Date dueDate;
    private double total;
    private double totalWithoutTax;
    private double totalTax;
    private double totalCost;
    private String notes;
    
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

    public List<RetailerEstimateDetail> getDetails() {
        return this.details;
    }

    public void setDetails(List<RetailerEstimateDetail> details) {
        this.details = details;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalWithoutTax() {
        return this.totalWithoutTax;
    }

    public void setTotalWithoutTax(double totalWithoutTax) {
        this.totalWithoutTax = totalWithoutTax;
    }

    public double getTotalTax() {
        return this.totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
