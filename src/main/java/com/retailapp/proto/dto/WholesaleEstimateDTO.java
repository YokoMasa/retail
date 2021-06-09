package com.retailapp.proto.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.retailapp.proto.entity.Manufacturer;
import com.retailapp.proto.entity.Retailer;

public class WholesaleEstimateDTO {
    
    @JsonInclude(Include.NON_NULL)
    private Retailer retailer;
    private Manufacturer manufacturer;
    private String title;
    private Date estimateDate;

    @JsonInclude(Include.NON_NULL)
    private List<WholesaleEstimateDetailDTO> detailList = new ArrayList<>();

    public void addDetail(WholesaleEstimateDetailDTO detail) {
        detailList.add(detail);
    }

    public void setDetailList(List<WholesaleEstimateDetailDTO> detailList) {
        this.detailList = detailList;
    }

    public List<WholesaleEstimateDetailDTO> getDetailList() {
        return detailList;
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

}
