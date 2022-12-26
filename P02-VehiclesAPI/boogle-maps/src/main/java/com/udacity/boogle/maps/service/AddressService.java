package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.entity.Address;

public interface AddressService {

    Address getAddress(long vehicleId);
}
