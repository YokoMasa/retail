package com.retailapp.proto.repository;

import com.retailapp.proto.entity.RetailerEstimateDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RetailerEstimateDetailRepository extends JpaRepository<RetailerEstimateDetail, Integer> {
    
}
