package com.joe.dating.rest;

import com.joe.dating.domain.location.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Joe Deluca on 11/21/2016.
 */
@RestController
@RequestMapping("/api/location")
public class LocationResource {

    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> findCountries() {
        return ResponseEntity.ok(countryRepository.findAll());
    }

    @GetMapping("/regions")
    public ResponseEntity<List<Region>> findRegions(@RequestParam String countryId) {
        return ResponseEntity.ok(regionRepository.findAllByCountryId(countryId));
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> findCities(@RequestParam String regionId) {
        return ResponseEntity.ok(cityRepository.findAllByRegionId(regionId));
    }

}
