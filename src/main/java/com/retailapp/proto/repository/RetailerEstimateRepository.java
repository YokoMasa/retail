package com.retailapp.proto.repository;

import com.retailapp.proto.entity.RetailerEstimate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailerEstimateRepository extends JpaRepository<RetailerEstimate, Integer> {
    
}
