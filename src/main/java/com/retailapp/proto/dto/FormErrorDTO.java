package com.retailapp.proto.dto;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.common.RetailDataTypes;

public class FormErrorDTO {
    
    private int itemNo;
    private String elementName;
    private String errorCode;
    private String message;

    public int getItemNo() {
        return this.itemNo;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getElementName() {
        return this.elementName;
    }

    public String getMessage() {
        return this.message;
    }
    
    public FormErrorDTO(int itemNo, String errorCode, String elementName, String message) {
        this.itemNo = itemNo;
        this.errorCode = errorCode;
        this.elementName = elementName;
        this.message = message;
    }

    public static FormErrorDTO fieldNonNullError(int itemNo, MessageResolver r, RetailDataTypes type) {
        String typeDisplayName = r.get(type.getMessageId());
        String message = r.get(MessageConst.ERROR_FIELD_NON_NULL, typeDisplayName);
        return new FormErrorDTO(itemNo, ErrorCodeConst.FIELD_NON_NULL, type.getFieldName(), message);
    }

    public static FormErrorDTO fieldNonZeroError(int itemNo, MessageResolver r, RetailDataTypes type) {
        String typeDisplayName = r.get(type.getMessageId());
        String message = r.get(MessageConst.ERROR_FIELD_NON_ZERO, typeDisplayName);
        return new FormErrorDTO(itemNo, ErrorCodeConst.FIELD_NON_NULL, type.getFieldName(), message);
    }

    public static FormErrorDTO alreadyExistsError(int itemNo, MessageResolver r, RetailDataTypes type) {
        String typeDisplayName = r.get(type.getMessageId());
        String message = r.get(MessageConst.ERROR_RECORD_EXISTS, typeDisplayName);
        return new FormErrorDTO(itemNo, ErrorCodeConst.RECORD_EXISTS, type.getFieldName(), message);
    }
    
}