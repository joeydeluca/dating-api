package com.joe.dating.domain.user;

import com.joe.dating.DatingApplication;
import com.joe.dating.domain.location.City;
import com.joe.dating.domain.location.Country;
import com.joe.dating.domain.location.CountryRepository;
import com.joe.dating.domain.user.models.Gender;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.domain.validaiton.ValidationException;
import com.joe.dating.security.AuthContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {DatingApplication.class})
public class UserServiceIT {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        this.userRepository.deleteAll();
    }

    @Test(expected = ValidationException.class)
    public void testDuplicateEmail() {
        userService.create("email", "username1", "password", Gender.M, Gender.W);
        userService.create("email", "username2", "password", Gender.M, Gender.W);
    }

    @Test(expected = ValidationException.class)
    public void testDuplicateUsername() {
        userService.create("email1", "username", "password", Gender.M, Gender.W);
        userService.create("email2", "username", "password", Gender.M, Gender.W);
    }

    @Test
    public void updateProfileTest() {
        AuthContext authContext = userService.create("email", "username", "password", Gender.M, Gender.W);
        Profile profile = new Profile();
        profile.setAboutMe("about me");
        Country country = new Country();
        country.setCountryId("1");
        profile.setCountry(country);
        City city = new City();
        city.setCityId("1");
        profile.setCity(city);

        User updatedUser = userService.updateProfile(authContext.getUserId(), profile);

        assertNotNull(updatedUser);
        assertEquals("Afghanistan", updatedUser.getProfile().getCountry().getCountryName());
        assertEquals("about me", updatedUser.getProfile().getAboutMe());
    }

}
