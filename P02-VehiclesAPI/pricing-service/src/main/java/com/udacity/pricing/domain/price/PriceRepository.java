package com.udacity.pricing.domain.price;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price, Long> {
    @Query("select p from Price p where p.vehicleid=:id")
    Price findPriceByVehicleId(Long id);
}
