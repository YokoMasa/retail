package com.retailapp.proto.repository;

import java.util.List;
import java.util.Optional;

import com.retailapp.proto.entity.Manufacturer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
    
    public Optional<Manufacturer> findByName(String name);

    public List<Manufacturer> findByNameContainingIgnoreCase(String name);

}
