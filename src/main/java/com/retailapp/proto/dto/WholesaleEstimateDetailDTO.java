package com.retailapp.proto.dto;

import java.util.Date;

/**
 * 卸向けの見積もり明細の1レコード
 */
public class WholesaleEstimateDetailDTO {

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
    private String condition;

    /** NET */
    private double netWholesalePrice;

    /** 備考 */
    private String notes;


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

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
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

    @Override
    public String toString() {
        return "{" +
            " sellingStartDate='" + getSellingStartDate() + "'" +
            ", productCategory='" + getProductCategory() + "'" +
            ", productJan='" + getProductJan() + "'" +
            ", productName='" + getProductName() + "'" +
            ", productManufacturer='" + getProductManufacturer() + "'" +
            ", productSpecification='" + getProductSpecification() + "'" +
            ", count='" + getCount() + "'" +
            ", wholesalePrice='" + getWholesalePrice() + "'" +
            ", condition='" + getCondition() + "'" +
            ", netWholesalePrice='" + getNetWholesalePrice() + "'" +
            ", notes='" + getNotes() + "'" +
            "}";
    }

}
