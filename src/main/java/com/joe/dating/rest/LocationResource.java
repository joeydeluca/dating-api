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

import static com.joe.dating.config.CacheConfig.*;

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

    @Cacheable(REGION_CACHE)
    @GetMapping("/regions")
    public ResponseEntity<List<Region>> findRegions(@RequestParam String countryId) {
        return ResponseEntity.ok(regionRepository.findAllByCountryId(countryId));
    }

    @Cacheable(CITY_CACHE)
    @GetMapping("/cities")
    public ResponseEntity<List<City>> findCities(@RequestParam String regionId) {
        return ResponseEntity.ok(cityRepository.findAllByRegionId(regionId));
    }

    @GetMapping("/findme")
    public ResponseEntity<CurrentLocation> getLocation(@RequestParam float latitude, @RequestParam float longitude) {

        List<Object[]> location = cityRepository.getLocation(latitude, longitude);
        if(location != null && location.size() > 0 && location.get(0) != null && location.get(0).length >= 6) {
            Object[] row = location.get(0);

            CurrentLocation currentLocation = new CurrentLocation();
            currentLocation.cityId = row[0].toString();
            currentLocation.cityName = row[1].toString();
            currentLocation.regionId = row[2].toString();
            currentLocation.regionName = row[3].toString();
            currentLocation.countryId = row[4].toString();
            currentLocation.countryName = row[5].toString();

            return ResponseEntity.ok(currentLocation);
        }

        return ResponseEntity.notFound().build();
    }

    static class CurrentLocation {
        public String countryName;
        public String countryId;
        public String regionName;
        public String regionId;
        public String cityName;
        public String cityId;
    }

}
