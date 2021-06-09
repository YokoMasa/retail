package com.retailapp.proto.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.dto.RetailerEstimateDTO;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.service.RetailerEstimateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retailerEstimate")
public class RetailerEstimateController {
    
    @Autowired
    private RetailerEstimateService service;

    @Autowired
    private MessageResolver message;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") int id) {
        Optional<RetailerEstimateDTO> op = service.getById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok().body(ApiResponse.ok(op.get()));
        } else {
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.NOT_FOUND, message.get(MessageConst.ERROR_NOT_FOUND));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RetailerEstimateDTO dto) {
        ValidationResultDTO validationResult = service.validate(dto);
        if (validationResult.hasError()) {
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD), validationResult.getErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(r);
        } else {
            int id = service.persist(dto);
            Map<String, Integer> body = new HashMap<>();
            body.put("id", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(body));
        }
    }

}
