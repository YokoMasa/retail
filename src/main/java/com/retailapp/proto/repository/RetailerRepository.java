package com.retailapp.proto.repository;

import java.util.List;

import com.retailapp.proto.entity.Retailer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailerRepository extends JpaRepository<Retailer, Integer> {

    public List<Retailer> findByNameContainingIgnoreCase(String name);
    
}
