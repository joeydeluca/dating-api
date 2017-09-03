package com.joe.dating.domain.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Joe Deluca on 11/25/2016.
 */
public interface RegionRepository extends JpaRepository<Region, String> {
    List<Region> findAllByCountryId(String countryId);
}
