package com.retailapp.proto.entity.projections;

import java.util.Date;

import com.retailapp.proto.entity.Manufacturer;
import com.retailapp.proto.entity.Retailer;

public interface WholesaleEstimateMetadata {
    public int getId();

    public String getTitle();

    public Date getEstimateDate();

    public Retailer getRetailer();

    public Manufacturer getManufacturer();
}
