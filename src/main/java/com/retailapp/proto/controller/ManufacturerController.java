package com.retailapp.proto.controller;

import java.util.List;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.entity.Manufacturer;
import com.retailapp.proto.service.ManufacturerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {
    
    @Autowired
    private ManufacturerService service;

    @Autowired
    private MessageResolver message;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam("name") String name) {
        List<Manufacturer> l = service.listByName(name);
        return ResponseEntity.ok().body(ApiResponse.ok(l));
    }

    @PostMapping
    public ResponseEntity<?> create(@ModelAttribute Manufacturer manufacturer) {
        ValidationResultDTO validationResult = service.validate(manufacturer);
        if (validationResult.hasError()) {
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD), validationResult.getErrors());
            return ResponseEntity.badRequest().body(r);
        } else {
            service.create(manufacturer);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(null));
        }
    }

}
