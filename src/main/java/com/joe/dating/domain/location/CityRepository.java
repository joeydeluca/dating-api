package com.joe.dating.domain.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Joe Deluca on 11/25/2016.
 */
public interface CityRepository extends JpaRepository<City, String> {
    List<City> findAllByRegionId(String regionId);

    @Query(value = "SELECT c.CityId, c.City, r.RegionID, r.Region, cn.CountryId, cn.Country,  (    3959 *    acos(cos(radians(?1)) *     cos(radians(Latitude)) *     cos(radians(Longitude) -     radians(?2)) +     sin(radians(?1)) *     sin(radians(Latitude))) ) AS distance  " +
            "FROM Cities c " +
            "join Regions r on r.RegionID = c.RegionID " +
            "join Countries cn on cn.CountryID = r.CountryID " +
            "group by c.CityId " +
            "HAVING distance < 28  " +
            "ORDER BY distance LIMIT 1", nativeQuery = true)
    List<Object[]> getLocation(float latitude, float longitude);

}
