package com.udacity.boogle.maps.repository;

import com.udacity.boogle.maps.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {

    @Query("select a from Address a where a.vehicleId=:id")
    Address findByVehicleId(long id);
}
