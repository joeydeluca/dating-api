package com.joe.dating.domain.user.models;

import com.joe.dating.domain.StringListConverter;
import com.joe.dating.domain.location.City;
import com.joe.dating.domain.location.Country;
import com.joe.dating.domain.location.Region;
import com.joe.dating.domain.photo.Photo;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Joe Deluca on 11/24/2016.
 */
@Embeddable
public class Profile {
    private static final String DEFAULT_PROFILE_PHOTO_URL = "https://storage.googleapis.com/dating-176518.appspot.com/noimage.jpeg";

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne()
    @JoinColumn(name="city_id")
    private City city;

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne()
    @JoinColumn(name="state_id")
    private Region region;

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne()
    @JoinColumn(name="country_id")
    private Country country;

    @OrderBy("isProfilePhoto DESC")
    @OneToMany()
    @JoinColumn(name="profile_id")
    private List<Photo> photos;

    @Embedded
    private Height height;

    @Column(name = "zip")
    private String zip;

    @Column(name = "body_type")
    private String bodyType;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_body_type")
    private List<String> partnerBodyType;
    @Column(name = "smoke")
    private String smoke;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_smoke")
    private List<String> partnerSmoke;
    @Column(name = "drink")
    private String drink;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_drink")
    private List<String> partnerDrink;
    @Column(name = "hair_color")
    private String hairColor;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_hair_color")
    private List<String> partnerHairColor;
    @Column(name = "religion")
    private String religion;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_religion")
    private List<String> partnerReligion;
    @Convert(converter = StringListConverter.class)
    @Column(name = "pet")
    private List<String> pet;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_pet")
    private List<String> partnerPet;
    @Convert(converter = StringListConverter.class)
    @Column(name = "language")
    private List<String> language;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_language")
    private List<String> partnerLanguage;
    @Convert(converter = StringListConverter.class)
    @Column(name = "ethnicity")
    private List<String> ethnicity;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_ethnicity")
    private List<String> partnerEthnicity;
    @Column(name = "occupation")
    private String occupation;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_occupation")
    private List<String> partnerOccupation;
    @Column(name = "salary")
    private String salary;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_salary")
    private List<String> partnerSalary;
    @Column(name = "children_status")
    private String childrenStatus;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_children_status")
    private List<String> partnerChildrenStatus;
    @Column(name = "astro_sign")
    private String astroSign;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_astro_sign")
    private List<String> partnerAstroSign;
    @Column(name = "education")
    private String education;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_education")
    private List<String> partnerEducation;
    @Column(name = "eye_color")
    private String eyeColor;
    @Convert(converter = StringListConverter.class)
    @Column(name = "partner_eye_color")
    private List<String> partnerEyeColor;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "partner_description")
    private String partnerDescription;
    @Column(name = "perfect_date")
    private String perfectDate;

    public List<String> getPartnerBodyType() {
        return partnerBodyType;
    }

    public void setPartnerBodyType(List<String> partnerBodyType) {
        this.partnerBodyType = partnerBodyType;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public List<String> getPartnerSmoke() {
        return partnerSmoke;
    }

    public void setPartnerSmoke(List<String> partnerSmoke) {
        this.partnerSmoke = partnerSmoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public List<String> getPartnerDrink() {
        return partnerDrink;
    }

    public void setPartnerDrink(List<String> partnerDrink) {
        this.partnerDrink = partnerDrink;
    }

    public List<String> getPartnerHairColor() {
        return partnerHairColor;
    }

    public void setPartnerHairColor(List<String> partnerHairColor) {
        this.partnerHairColor = partnerHairColor;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public List<String> getPartnerReligion() {
        return partnerReligion;
    }

    public void setPartnerReligion(List<String> partnerReligion) {
        this.partnerReligion = partnerReligion;
    }

    public List<String> getPet() {
        return pet;
    }

    public void setPet(List<String> pet) {
        this.pet = pet;
    }

    public List<String> getPartnerPet() {
        return partnerPet;
    }

    public void setPartnerPet(List<String> partnerPet) {
        this.partnerPet = partnerPet;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public List<String> getPartnerLanguage() {
        return partnerLanguage;
    }

    public void setPartnerLanguage(List<String> partnerLanguage) {
        this.partnerLanguage = partnerLanguage;
    }

    public List<String> getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(List<String> ethnicity) {
        this.ethnicity = ethnicity;
    }

    public List<String> getPartnerEthnicity() {
        return partnerEthnicity;
    }

    public void setPartnerEthnicity(List<String> partnerEthnicity) {
        this.partnerEthnicity = partnerEthnicity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public List<String> getPartnerOccupation() {
        return partnerOccupation;
    }

    public void setPartnerOccupation(List<String> partnerOccupation) {
        this.partnerOccupation = partnerOccupation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public List<String> getPartnerSalary() {
        return partnerSalary;
    }

    public void setPartnerSalary(List<String> partnerSalary) {
        this.partnerSalary = partnerSalary;
    }

    public String getChildrenStatus() {
        return childrenStatus;
    }

    public void setChildrenStatus(String childrenStatus) {
        this.childrenStatus = childrenStatus;
    }

    public List<String> getPartnerChildrenStatus() {
        return partnerChildrenStatus;
    }

    public void setPartnerChildrenStatus(List<String> partnerChildrenStatus) {
        this.partnerChildrenStatus = partnerChildrenStatus;
    }

    public List<String> getPartnerAstroSign() {
        return partnerAstroSign;
    }

    public void setPartnerAstroSign(List<String> partnerAstroSign) {
        this.partnerAstroSign = partnerAstroSign;
    }

    public List<String> getPartnerEducation() {
        return partnerEducation;
    }

    public void setPartnerEducation(List<String> partnerEducation) {
        this.partnerEducation = partnerEducation;
    }

    public List<String> getPartnerEyeColor() {
        return partnerEyeColor;
    }

    public void setPartnerEyeColor(List<String> partnerEyeColor) {
        this.partnerEyeColor = partnerEyeColor;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }


    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }


    public String getAstroSign() {
        return astroSign;
    }

    public void setAstroSign(String astroSign) {
        this.astroSign = astroSign;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getPartnerDescription() {
        return partnerDescription;
    }

    public void setPartnerDescription(String partnerDescription) {
        this.partnerDescription = partnerDescription;
    }

    public String getPerfectDate() {
        return perfectDate;
    }

    public void setPerfectDate(String perfectDate) {
        this.perfectDate = perfectDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    @JsonIgnore
    public String getProfilePhotoUrl() {
        if(this.photos == null) return null;
        return this.photos.stream().filter(p -> p.isProfilePhoto()).findFirst().map(p -> p.getMediumUrl()).orElse(DEFAULT_PROFILE_PHOTO_URL);
    }
}
