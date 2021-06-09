package com.retailapp.proto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.retailapp.proto.common.MessageResolver;
import com.retailapp.proto.common.RetailDataTypes;
import com.retailapp.proto.dto.FormErrorDTO;
import com.retailapp.proto.dto.ValidationResultDTO;
import com.retailapp.proto.entity.Manufacturer;
import com.retailapp.proto.repository.ManufacturerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ManufacturerService {
    
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private MessageResolver message;

    public ValidationResultDTO validate(Manufacturer manufacturer) {
        List<FormErrorDTO> errors = new ArrayList<>();
        if (!StringUtils.hasText(manufacturer.getName())) {
            FormErrorDTO e = FormErrorDTO.fieldNonNullError(0, message, RetailDataTypes.MANUFACTURER);
            errors.add(e);
        } else {
            Optional<Manufacturer> op = manufacturerRepository.findByName(manufacturer.getName());
            if (op.isPresent()) {
                FormErrorDTO e = FormErrorDTO.alreadyExistsError(0, message, RetailDataTypes.MANUFACTURER);
                errors.add(e);
            }
        }
        return new ValidationResultDTO(errors.size() != 0, errors);
    }

    public void create(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }

    public Optional<Manufacturer> findOneByName(String name) {
        return manufacturerRepository.findByName(name);
    }

    public List<Manufacturer> listByName(String name) {
        return manufacturerRepository.findByNameContainingIgnoreCase(name);
    }

}
