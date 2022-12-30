package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceUnitTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private MapsClient mapsClient;

    @Mock
    private PriceClient priceClient;

    @Before
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        given(carRepository.findAll()).willReturn(List.of(car));
        given(priceClient.getPrice(anyLong())).willReturn("USD 5000");
        given(mapsClient.getAddress(any(), anyLong())).willReturn(getLocation());
        given(carRepository.findById(anyLong())).willReturn(Optional.of(car));
        given(carRepository.save(any())).willReturn(car);
        doNothing().when(carRepository).delete(any());
    }

    @Test
    public void testListCars() {
        List<Car> cars = carService.list();
        Assertions.assertThat(cars.size()).isEqualTo(1);
        Assertions.assertThat(cars.get(0).getPrice()).isEqualTo("USD 5000");
        Assertions.assertThat(cars.get(0).getLocation().getCity()).isEqualTo("Lekki");

        verify(carRepository, times(1)).findAll();
        verify(priceClient, times(1)).getPrice(1L);
        verify(mapsClient, times(1)).getAddress(any(), anyLong());
    }

    @Test
    public void testGetCar() {
        Car car = carService.findById(1L);
        Assertions.assertThat(car.getId()).isEqualTo(1L);
        Assertions.assertThat(car.getPrice()).isEqualTo("USD 5000");
        Assertions.assertThat(car.getLocation().getCity()).isEqualTo("Lekki");

        verify(carRepository, times(1)).findById(1L);
        verify(priceClient, times(1)).getPrice(1L);
        verify(mapsClient, times(1)).getAddress(any(), anyLong());

    }

    @Test
    public void testUpdateCar() {
        Car car = getCar();
        car.setId(1L);
        Car carFromDB = carService.save(car);

        Assertions.assertThat(carFromDB.getId()).isEqualTo(1L);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    public void testSaveCar() {
        Car car = getCar();
        Car carFromDB = carService.save(car);

        Assertions.assertThat(carFromDB.getId()).isEqualTo(1L);
        verify(carRepository, times(0)).findById(anyLong());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    public void testDelete() {
        carService.delete(1L);

        verify(carRepository, times(1)).findById(1L);
        verify(carRepository, times(1)).delete(any());
    }

    private Location getLocation() {
        Location location = new Location(40.41, -70.55);
        location.setState("Lagos");
        location.setCity("Lekki");
        location.setAddress("20 Base Street");
        location.setZip("10521");
        return location;
    }

    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
