package com.retailapp.proto.repository;

import java.util.List;
import java.util.Optional;

import com.retailapp.proto.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
    public Optional<Product> findByJan(String jan);

    public List<Product> findByNameContainingIgnoreCase(String name);

    public List<Product> findByJanContainingIgnoreCase(String jan);

}
