package com.udacity.vehicles.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Stores information about a given location.
 * Latitude and longitude must be provided, while other
 * location information must be gathered each time from
 * the maps API.
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Location {

    @NotNull
    @Setter(AccessLevel.PRIVATE)
    private Double lat;

    @NotNull
    @Setter(AccessLevel.PRIVATE)
    private Double lon;

    @Transient
    private String address;

    @Transient
    private String city;

    @Transient
    private String state;

    @Transient
    private String zip;

    public Location(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
