package com.joe.dating.domain.user;

import com.joe.dating.domain.location.City;
import com.joe.dating.domain.location.Country;
import com.joe.dating.domain.location.Region;
import com.joe.dating.domain.user.models.CompletionStatus;
import com.joe.dating.domain.user.models.Profile;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class UserSpecification {

    public static Specification<User> hasCommonFields() {
        return (root, query, cb) ->
                    cb.and(
                            cb.equal(root.get("isDeleted"), "N"),
                            cb.equal(root.get("completionStatus"), CompletionStatus.COMPLETE.getId()),
                            cb.equal(root.get("siteId"), 3)
                    );
    }

    public static Specification<User> hasCountry(String countryId) {
        return (root, query, cb) -> {
            Join<Profile, Country> country =
                    root.join("profile").join("country");

            return cb.equal(country.get("countryId"), countryId);
        };
    }

    public static Specification<User> hasRegion(String regionId) {
        return (root, query, cb) -> {
            Join<Profile, Region> region =
                    root.join("profile").join("region");

            return cb.equal(region.get("regionId"), regionId);
        };
    }

    public static Specification<User> hasCity(String cityId) {
        return (root, query, cb) -> {
            Join<Profile, City> city =
                    root.join("profile").join("city");

            return cb.equal(city.get("cityId"), cityId);
        };
    }

    public static Specification<User> ageFrom(int age) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("birthDate"), java.sql.Date.valueOf(LocalDate.now().minusYears(age)));
    }

    public static Specification<User> ageTo(int age) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("birthDate"), getDate(LocalDateTime.now().minusYears(age)));
    }


    public static Specification<User> gender(String gender) {
        return (root, query, cb) -> cb.equal(root.get("gender"), gender);
    }

    public static Specification<User> genderSeeking(String genderSeeking) {
        return (root, query, cb) -> cb.equal(root.get("genderSeeking"), genderSeeking);
    }

    private static Date getDate(LocalDateTime localDate) {
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }
}
