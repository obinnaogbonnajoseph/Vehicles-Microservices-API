package com.udacity.boogle.maps.web;

import com.udacity.boogle.maps.entity.Address;
import com.udacity.boogle.maps.repository.AddressRepository;
import com.udacity.boogle.maps.service.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MapsController.class)
public class MapsControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Before
    public void setup() {
        var address = fetchAddress();
        address.setVehicleId(1L);
        given(addressService.getAddress(anyLong())).willReturn(address);
    }

    @Test
    public void getAddress() throws Exception {
        mockMvc.perform(get("/maps?lat=0&lon=1&vehicleId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vehicleId", is(1)));
        verify(addressService, times(1)).getAddress(1L);
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
