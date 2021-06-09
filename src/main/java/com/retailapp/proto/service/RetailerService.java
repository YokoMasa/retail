package com.retailapp.proto.service;

import java.util.List;

import com.retailapp.proto.entity.Retailer;
import com.retailapp.proto.repository.RetailerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetailerService {
    
    @Autowired
    private RetailerRepository repository;

    public List<Retailer> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    public Retailer create(Retailer retailer) {
        return repository.save(retailer);
    }

}
