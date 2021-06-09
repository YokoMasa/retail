package com.retailapp.proto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.retailapp.proto.common.ErrorCodeConst;
import com.retailapp.proto.common.MessageConst;
import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.dto.PagedListDTO;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.entity.Product;
import com.retailapp.proto.service.ProductService;

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
@RequestMapping("product")
public class ProductController {
    
    @Autowired
    private ProductService service;

    @Autowired
    private MessageResolver message;

    @GetMapping
    public ResponseEntity<?> list(@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        PagedListDTO<Product> l = service.list(page);
        return ResponseEntity.ok().body(ApiResponse.ok(l));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "jan", required = false) String jan, @RequestParam(name = "name", required = false) String name) {
        List<Product> l = new ArrayList<>();
        if (StringUtils.hasText(jan)) {
            l = service.listByJan(jan);
        } else if (StringUtils.hasText(name)) {
            l = service.listByName(name);
        }
        return ResponseEntity.ok().body(ApiResponse.ok(l));
    }


    @GetMapping("/{jan}")
    public ResponseEntity<?> get(@PathVariable("jan") String jan) {
        Optional<Product> op = service.getByJan(jan);
        if (op.isPresent()) {
            return ResponseEntity.ok().body(ApiResponse.ok(op.get()));
        } else {
            ApiResponse<?> r = ApiResponse.error(ErrorCodeConst.JAN_NOT_FOUND, message.get(MessageConst.ERROR_JAN_NOT_FOUND));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(r);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@ModelAttribute Product product) {
        ValidationResultDTO r = service.validate(product);
        if (r.hasError()) {
            ApiResponse<List<FormErrorDTO>> apiResponse = ApiResponse.error(ErrorCodeConst.FIELD_ERROR, message.get(MessageConst.ERROR_FIELD), r.getErrors());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        try {
            service.create(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ErrorCodeConst.DB_SELECT, message.get(MessageConst.ERROR_DATABASE_SELECT)));
        }
    }

}
