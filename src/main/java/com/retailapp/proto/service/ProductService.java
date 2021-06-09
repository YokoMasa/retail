package com.retailapp.proto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.common.RetailDataTypes;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.dto.PagedListDTO;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.entity.Product;
import com.retailapp.proto.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductService {

    public static final int PRODUCT_PAGE_SIZE = 20;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageResolver message;

    public ValidationResultDTO validate(Product product) {
        List<FormErrorDTO> errors = new ArrayList<>();
        if (!StringUtils.hasText(product.getJan())) {
            errors.add(FormErrorDTO.fieldNonNullError(0, message, RetailDataTypes.PRODUCT_JAN));
        } else {
            Optional<Product> op = productRepository.findByJan(product.getJan());
            if (op.isPresent()) {
                errors.add(FormErrorDTO.alreadyExistsError(0, message, RetailDataTypes.PRODUCT_JAN));
            }
        }

        if (!StringUtils.hasText(product.getName())) {
            errors.add(FormErrorDTO.fieldNonNullError(0, message, RetailDataTypes.PRODUCT_NAME));
        }
        return new ValidationResultDTO(errors.size() != 0, errors);
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * 商品のページを返す
     * 
     * @param page 1起点のページNo.
     */
    public PagedListDTO<Product> list(int page) {
        page--; // 0起点のpageNoに直す
        Page<Product> p = productRepository.findAll(PageRequest.of(page, PRODUCT_PAGE_SIZE));
        return PagedListDTO.create(p);
    }

    public Optional<Product> getByJan(String jan) {
        return productRepository.findByJan(jan);
    }

    public List<Product> listByJan(String jan) {
        return productRepository.findByJanContainingIgnoreCase(jan);
    }

    public List<Product> listByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

}
