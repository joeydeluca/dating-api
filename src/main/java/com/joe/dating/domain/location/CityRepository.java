package com.joe.dating.domain.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Joe Deluca on 11/25/2016.
 */
public interface CityRepository extends JpaRepository<City, String> {
    List<City> findAllByRegionId(String regionId);
}
