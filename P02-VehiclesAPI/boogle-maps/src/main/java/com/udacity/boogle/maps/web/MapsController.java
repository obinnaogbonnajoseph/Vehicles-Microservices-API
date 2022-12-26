package com.udacity.boogle.maps.web;

import com.udacity.boogle.maps.entity.Address;
import com.udacity.boogle.maps.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
public class MapsController {

    private AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon, @RequestParam Long vehicleId) {
        return addressService.getAddress(vehicleId);
    }
}
