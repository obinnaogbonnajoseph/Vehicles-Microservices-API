package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.entity.Address;
import com.udacity.boogle.maps.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceUnitTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    public void getAddress_withExistingVehicleId() {
        var address = fetchAddress();
        address.setVehicleId(1L);
        given(addressRepository.findByVehicleId(anyLong())).willReturn(address);
        var addressFromDB = addressService.getAddress(1L);
        assertThat(addressFromDB.getZip()).isEqualTo(address.getZip());
        assertThat(addressFromDB.getCity()).isEqualTo(address.getCity());
        assertThat(addressFromDB.getState()).isEqualTo(address.getState());

        verify(addressRepository, times(1))
                .findByVehicleId(1L);
    }

    @Test
    public void getAddress_withNonExistingVehicleId() {
        var address = fetchAddress();
        address.setVehicleId(1L);
        address.setId(1L);
        given(addressRepository.findByVehicleId(anyLong())).willReturn(null);
        given(addressRepository.save(any())).willReturn(address);
        var addressFromDB = addressService.getAddress(1L);
        assertThat(addressFromDB.getZip()).isEqualTo(address.getZip());
        assertThat(addressFromDB.getCity()).isEqualTo(address.getCity());
        assertThat(addressFromDB.getState()).isEqualTo(address.getState());

        verify(addressRepository, times(1))
                .save(any());
    }

    private Address fetchAddress() {
        String address = "30 Memorial Drive, Avon MA 2322";

        String[] addressParts = address.split(",");
        String streetAndNumber = addressParts[0];
        String cityStateAndZip = addressParts[1];

        String[] cityStateAndZipParts = cityStateAndZip.trim().split(" ");

        LinkedList<String> list =
                Arrays.stream(cityStateAndZipParts).map(String::trim)
                        .collect(Collectors.toCollection(LinkedList::new));

        String zip = list.pollLast();
        String state = list.pollLast();
        String city = String.join(" ", list);

        return new Address(streetAndNumber, city, state, zip);
    }
}
