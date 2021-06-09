package com.retailapp.proto.common;

public enum RetailDataTypes {

    SELLING_START_DATE("$SELLING_START_DATE", MessageConst.DATA_SELLING_START_DATE, "sellingStartDate"),
    PRODUCT_CATEGORY("$PRODUCT_CATEGORY", MessageConst.DATA_PRODUCT_CATEGORY, "productCategory"),
    PRODUCT_JAN("$PRODUCT_JAN", MessageConst.DATA_PRODUCT_JAN, "productJan"),
    PRODUCT_NAME("$PRODUCT_NAME", MessageConst.DATA_PRODUCT_NAME, "productName"),
    PRODUCT_MANUFACTURER("$PRODUCT_MANUFACTURER", MessageConst.DATA_PRODUCT_MANUFACTURER, "productManufacturer"),
    PRODUCT_SPECIFICATION("$PRODUCT_SPECIFICATION", MessageConst.DATA_PRODUCT_SPECIFICATION, "productSpecification"),
    COUNT("$COUNT", MessageConst.DATA_COUNT, "count"),
    WHOLESALE_PRICE("$WHOLESALE_PRICE", MessageConst.DATA_WHOLESALE_PRICE, "wholesalePrice"),
    CONDITION("$CONDITION", MessageConst.DATA_CONDITION, "condition"),
    NET_WHOLESALE_PRICE("$NET_WHOLESALE_PRICE", MessageConst.DATA_NET_WHOLESALE_PRICE, "netWholesalePrice"),
    NOTES("$NOTES", MessageConst.DATA_NOTES, "notes"),
    MANUFACTURER("$MANUFACTURER", MessageConst.DATA_MANUFACTURER, "manufacturer"),
    UNIT_PRICE("$UNIT_PRICE", MessageConst.DATA_UNIT_PRICE, "unitPrice");

    private String placeholder;
    private String messageId;
    private String fieldName;

    public String getPlaceholder() {
        return placeholder;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getFieldName() {
        return fieldName;
    }

    private RetailDataTypes(String placeholder, String messageId, String fieldName) {
        this.placeholder = placeholder;
        this.messageId = messageId;
        this.fieldName = fieldName;
    }
}
