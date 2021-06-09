package com.retailapp.proto.repository;

import com.retailapp.proto.entity.WholesaleEstimate;
import com.retailapp.proto.entity.projections.WholesaleEstimateMetadata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WholesaleEstimateRepository extends JpaRepository<WholesaleEstimate, Integer> {
    
    public Page<WholesaleEstimateMetadata> findMetadataBy(Pageable pageable);

}
