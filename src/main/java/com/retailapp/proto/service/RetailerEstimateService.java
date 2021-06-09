package com.retailapp.proto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.common.RetailDataTypes;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.dto.RetailerEstimateDTO;
import com.retailapp.proto.dto.RetailerEstimateDetailDTO;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.entity.RetailerEstimate;
import com.retailapp.proto.entity.RetailerEstimateDetail;
import com.retailapp.proto.repository.RetailerEstimateDetailRepository;
import com.retailapp.proto.repository.RetailerEstimateRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RetailerEstimateService {
    
    @Autowired
    private RetailerEstimateRepository retailerEstimateRepository;

    @Autowired
    private MessageResolver message;

    @Autowired
    private RetailerEstimateDetailRepository retailerEstimateDetailRepository;

    public Optional<RetailerEstimateDTO> getById(int id) {
        Optional<RetailerEstimate> op = retailerEstimateRepository.findById(id);
        if (!op.isPresent()) {
            return Optional.empty();
        }

        RetailerEstimate e = op.get();
        RetailerEstimateDTO dto = new RetailerEstimateDTO();
        dto.setId(e.getId());
        dto.setAddress(e.getAddress());
        dto.setDueDate(e.getDueDate());
        dto.setIssueDate(e.getIssueDate());
        dto.setNotes(e.getNotes());
        dto.setRetailer(e.getRetailer());
        dto.setTotal(e.getTotal());
        dto.setTotalCost(e.getTotalCost());
        dto.setTotalTax(e.getTotalTax());
        dto.setTotalWithoutTax(e.getTotalWithoutTax());

        for (RetailerEstimateDetail detail: e.getDetails()) {
            RetailerEstimateDetailDTO detailDto = new RetailerEstimateDetailDTO();
            detailDto.setId(detail.getId());
            detailDto.setCount(detail.getCount());
            detailDto.setOrderNo(detail.getOrderNo());
            detailDto.setUnitPrice(detail.getUnitPrice());
            detailDto.setProductCategory(detail.getProductCategory());
            detailDto.setProductJan(detail.getProductJan());
            detailDto.setProductManufacturer(detail.getProductManufacturer());
            detailDto.setProductName(detail.getProductName());
            detailDto.setProductSpecification(detail.getProductSpecification());
            detailDto.setSellingStartDate(detail.getSellingStartDate());
            detailDto.setTaxRate(detail.getTaxRate());
            detailDto.setWholesalePrice(detail.getWholesalePrice());
            dto.addDetail(detailDto);
        }

        return Optional.of(dto);
    }

    public int persist(RetailerEstimateDTO dto) {
        RetailerEstimate e = new RetailerEstimate();
        e.setId(dto.getId());
        e.setAddress(dto.getAddress());
        e.setDueDate(dto.getDueDate());
        e.setIssueDate(dto.getIssueDate());
        e.setNotes(dto.getNotes());
        e.setRetailer(dto.getRetailer());
        e.setTotal(dto.getTotal());
        e.setTotalCost(dto.getTotalCost());
        e.setTotalTax(dto.getTotalTax());
        e.setTotalWithoutTax(dto.getTotalWithoutTax());
        e = retailerEstimateRepository.save(e);

        for (int i = 0; i < dto.getDetails().size(); i++) {
            RetailerEstimateDetailDTO detailDto = dto.getDetails().get(i);
            RetailerEstimateDetail detail = new RetailerEstimateDetail();
            detail.setId(detailDto.getId());
            detail.setCount(detailDto.getCount());
            detail.setOrderNo(i);
            detail.setUnitPrice(detailDto.getUnitPrice());
            detail.setProductCategory(detailDto.getProductCategory());
            detail.setProductJan(detailDto.getProductJan());
            detail.setProductManufacturer(detailDto.getProductManufacturer());
            detail.setProductName(detailDto.getProductName());
            detail.setProductSpecification(detailDto.getProductSpecification());
            detail.setRetailerEstimate(e);
            detail.setSellingStartDate(detailDto.getSellingStartDate());
            detail.setTaxRate(detailDto.getTaxRate());
            detail.setWholesalePrice(detailDto.getWholesalePrice());
            retailerEstimateDetailRepository.save(detail);
        }

        return e.getId();
    }

    public ValidationResultDTO validate(RetailerEstimateDTO dto) {
        List<FormErrorDTO> errors = new ArrayList<>();

        for (int i = 0; i < dto.getDetails().size(); i++) {
            RetailerEstimateDetailDTO detail = dto.getDetails().get(i);
            validateDetail(i, detail, errors);
        }
        return new ValidationResultDTO(errors.size() != 0, errors);
    }

    private void validateDetail(int index, RetailerEstimateDetailDTO detail, List<FormErrorDTO> errors) {
        if (!StringUtils.hasText(detail.getProductJan())) {
            errors.add(FormErrorDTO.fieldNonNullError(index, message, RetailDataTypes.PRODUCT_JAN));
        }

        if (!StringUtils.hasText(detail.getProductName())) {
            errors.add(FormErrorDTO.fieldNonNullError(index, message, RetailDataTypes.PRODUCT_NAME));
        }

        if (detail.getUnitPrice() == 0) {
            errors.add(FormErrorDTO.fieldNonZeroError(index, message, RetailDataTypes.UNIT_PRICE));
        }

        if (detail.getCount() == 0) {
            errors.add(FormErrorDTO.fieldNonZeroError(index, message, RetailDataTypes.COUNT));
        }
    }

}
