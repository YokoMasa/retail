package com.retailapp.proto.controller;

import java.util.ArrayList;
import java.util.List;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.entity.Retailer;
import com.retailapp.proto.service.RetailerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retailer")
public class RetailerController {
    
    @Autowired
    private RetailerService service;

    @Autowired
    private MessageResolver message;

    @GetMapping
    public ResponseEntity<?> findByName(@RequestParam("name") String name) {
        List<Retailer> l = service.findByName(name);
        return ResponseEntity.ok().body(ApiResponse.ok(l));
    }

    @PostMapping
    public ResponseEntity<?> create(@ModelAttribute Retailer retailer) {
        List<FormErrorDTO> errors = validate(retailer);
        if (errors.size() == 0) {
            ApiResponse<List<FormErrorDTO>> r = ApiResponse.error(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD), errors);
            return ResponseEntity.badRequest().body(r);
        }

        try {
            service.create(retailer);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCodeConst.DB_INSERT, e.getMessage()));
        }
    }

    private List<FormErrorDTO> validate(Retailer retailer) {
        List<FormErrorDTO> errors = new ArrayList<>();
        if (!StringUtils.hasText(retailer.getName())) {
            String m = message.get(MessageConst.ERROR_FIELD_NON_NULL, "name");
            FormErrorDTO e = new FormErrorDTO(0, ErrorCodeConst.FIELD_NON_NULL, "name", m);
            errors.add(e);
        }
        return errors;
    }

}
