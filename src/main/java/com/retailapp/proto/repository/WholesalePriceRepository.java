package com.retailapp.proto.repository;

import java.util.List;
import java.util.Optional;

import com.retailapp.proto.entity.WholesalePrice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WholesalePriceRepository extends JpaRepository<WholesalePrice, Integer> {
    
    public List<WholesalePrice> findByJanOrderById(String jan);

    public Optional<WholesalePrice> findByJanAndDefaultPriceFlag(String jan, int defaltPriceFlag);

    @Query("select p from WholesalePrice p where p.jan = ?1 and p.wholesalePrice = ?2 and p.netWholesalePrice = ?3")
    public List<WholesalePrice> findExactSame(String jan, double wholesalePrice, double netWholesalePrice);

    @Transactional
    @Modifying
    @Query(value = "insert into retailer_wholesale_price (wholesale_price_id, retailer_id) values (?1, ?2)", nativeQuery = true)
    public int insertRetailerWholesalePrice(int wholesalePriceId, int retailerId);

    @Query(value = "select count(*) from retailer_wholesale_price where wholesale_price_id = ?1 and retailer_id = ?2", nativeQuery = true)
    public int getCountOfRetailerWholesalePrice(int wholesalePriceId, int retailerId);

    @Query(value = "select * from wholesale_price wp join retailer_wholesale_price rwp on rwp.wholesale_price_id = wp.id where wp.jan = ?1 and rwp.retailer_id = ?2 limit 1", nativeQuery = true)
    public Optional<WholesalePrice> getPriceForRetailer(String jan, int retailerId);

}
