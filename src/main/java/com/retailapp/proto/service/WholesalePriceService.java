package com.retailapp.proto.service;

import java.util.List;
import java.util.Optional;

import com.retailapp.proto.entity.WholesalePrice;
import com.retailapp.proto.exception.AppException;
import com.retailapp.proto.repository.WholesalePriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WholesalePriceService {
    
    @Autowired
    private WholesalePriceRepository repository;

    public List<WholesalePrice> getPriceOfJan(String jan) {
        return repository.findByJanOrderById(jan);
    }

    public Optional<WholesalePrice> getPriceForRetailer(String jan, int retailerId) {
        Optional<WholesalePrice> op = repository.getPriceForRetailer(jan, retailerId);
        if (op.isPresent()) {
            return op;
        } else {
            return repository.findByJanAndDefaultPriceFlag(jan, WholesalePrice.DEFAULT_PRICE);
        }
    }

    public WholesalePrice createWholesalePrice(WholesalePrice price) throws AppException {
        Optional<WholesalePrice> o = repository.findByJanAndDefaultPriceFlag(price.getJan(), WholesalePrice.DEFAULT_PRICE);
        if (!o.isPresent()) {
            price.setDefaultPriceFlag(WholesalePrice.DEFAULT_PRICE);
        }
        return repository.save(price);
    }

    public void createRetailerWholesalePrice(int wholesalePriceId, int retailerId) {
        repository.insertRetailerWholesalePrice(wholesalePriceId, retailerId);
    }

    public List<WholesalePrice> findExactSame(String jan, double wholesalePrice, double netWholesalePrice) {
        return repository.findExactSame(jan, wholesalePrice, netWholesalePrice);
    }

}
