package com.retailapp.proto.excel;

import com.retailapp.proto.common.RetailDataTypes;

import org.apache.poi.ss.util.CellAddress;

public class MappingInfo {

    private RetailDataTypes type;
    private CellAddress cellAddress;

    public RetailDataTypes getType() {
        return this.type;
    }

    public CellAddress getCellAddress() {
        return this.cellAddress;
    }

    public MappingInfo(RetailDataTypes type, CellAddress cellAddress) {
        this.type = type;
        this.cellAddress = cellAddress;
    }

}
