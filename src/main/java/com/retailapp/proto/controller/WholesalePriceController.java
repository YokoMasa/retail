package com.retailapp.proto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.entity.WholesalePrice;
import com.retailapp.proto.service.WholesalePriceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wholesalePrice")
public class WholesalePriceController {
    
    @Autowired
    private WholesalePriceService service;

    @Autowired
    private MessageResolver message;

    @GetMapping
    public ResponseEntity<?> getPriceOfJan(@RequestParam("jan") String jan, @RequestParam(value = "retailer_id", required = false) Integer retailerId) {
        if (retailerId == null) {
            List<WholesalePrice> priceList = service.getPriceOfJan(jan);
            return ResponseEntity.ok().body(ApiResponse.ok(priceList));
        } else {
            Optional<WholesalePrice> op = service.getPriceForRetailer(jan, retailerId);
            if (op.isPresent()) {
                return ResponseEntity.ok().body(ApiResponse.ok(op.get()));
            } else {
                return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(ErrorCodeConst.JAN_NOT_FOUND, message.get(MessageConst.ERROR_JAN_NOT_FOUND)));
            }
        }
    }

    @PostMapping
    public ResponseEntity<?> createWholesalePrice(@ModelAttribute WholesalePrice price) {
        List<FormErrorDTO> errors = validate(price);
        if (errors.size() != 0) {
            ApiResponse<List<FormErrorDTO>> r = ApiResponse.error(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD), errors);
            return ResponseEntity.badRequest().body(r);
        }

        try {
            service.createWholesalePrice(price);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.DB_INSERT, e.getMessage());
            return ResponseEntity.badRequest().body(r);
        }
    }

    @PostMapping("/{id}/retailer")
    public ResponseEntity<?> createRetailerWholesalePrice(@PathVariable("id") int wholesalePriceId, @RequestParam("retailerId") int retailerId) {
        try {
            service.createRetailerWholesalePrice(wholesalePriceId, retailerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(null));
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.DB_INSERT, e.getMessage());
            return ResponseEntity.badRequest().body(r);
        }
    }

    private List<FormErrorDTO> validate(WholesalePrice price) {
        List<FormErrorDTO> errors = new ArrayList<>();
        if (!StringUtils.hasText(price.getJan())) {
            String m = message.get(MessageConst.ERROR_FIELD_NON_NULL, "jan");
            FormErrorDTO e = new FormErrorDTO(0, ErrorCodeConst.FIELD_NON_NULL, "jan", m);
            errors.add(e);
        }

        if (price.getWholesalePrice() == 0) {
            String m = message.get(MessageConst.ERROR_PRICE_ZERO);
            FormErrorDTO e = new FormErrorDTO(0, ErrorCodeConst.PRICE_ZERO, "wholesalePrice", m);
            errors.add(e);
        }

        if (price.getNetWholesalePrice() == 0) {
            String m = message.get(MessageConst.ERROR_PRICE_ZERO);
            FormErrorDTO e = new FormErrorDTO(0, ErrorCodeConst.PRICE_ZERO, "netWholesalePrice", m);
            errors.add(e);
        }

        return errors;
    }

}
