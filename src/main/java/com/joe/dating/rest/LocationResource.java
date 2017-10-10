package com.joe.dating.rest;

import com.joe.dating.domain.location.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.joe.dating.config.CacheConfig.LOCATION_CACHE;

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

    @Cacheable(cacheNames = LOCATION_CACHE, key = "#root.methodName")
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> findCountries() {
        return ResponseEntity.ok(countryRepository.findAll());
    }

    @Cacheable(LOCATION_CACHE)
    @GetMapping("/regions")
    public ResponseEntity<List<Region>> findRegions(@RequestParam String countryId) {
        return ResponseEntity.ok(regionRepository.findAllByCountryId(countryId));
    }

    @Cacheable(LOCATION_CACHE)
    @GetMapping("/cities")
    public ResponseEntity<List<City>> findCities(@RequestParam String regionId) {
        return ResponseEntity.ok(cityRepository.findAllByRegionId(regionId));
    }

}
