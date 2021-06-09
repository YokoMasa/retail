package com.retailapp.proto.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WholesaleEstimateDetail {
    
    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /** 見積 */
    @ManyToOne
    private WholesaleEstimate wholesaleEstimate;
    /** 発売日 */
    private Date sellingStartDate;
    /** 部門 */
    private String productCategory;
    /** JANコード */
    private String productJan;
    /** 商品名 */
    private String productName;
    /** メーカー */
    private String productManufacturer;
    /** 規格 */
    private String productSpecification;
    /** 入数 */
    private String count;
    /** 仕切 */
    private double wholesalePrice;
    /** 条件 */
    private String conditions;
    /** NET */
    private double netWholesalePrice;
    /** 備考 */
    private String notes;
    
    public WholesaleEstimate getEstimate() {
        return this.wholesaleEstimate;
    }

    public void setEstimate(WholesaleEstimate estimate) {
        this.wholesaleEstimate = estimate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCount() {
        return this.count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }    

}
