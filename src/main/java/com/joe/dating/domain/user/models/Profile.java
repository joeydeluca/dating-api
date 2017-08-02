package com.joe.dating.domain.user.models;

import com.joe.dating.domain.StringListConverter;
import com.joe.dating.domain.location.City;
import com.joe.dating.domain.location.Country;
import com.joe.dating.domain.location.Region;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Joe Deluca on 11/24/2016.
 */
@Embeddable
public class Profile {
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

    @Embedded
    private Height height;

    @Column(name = "zip")
    private String zip;

    @Column(name = "body_type")
    private String bodyType;
    @Column(name = "partner_body_type")
    private String partnerBodyType;
    @Column(name = "smoke")
    private String smoke;
    @Column(name = "partner_smoke")
    private String partnerSmoke;
    @Column(name = "drink")
    private String drink;
    @Column(name = "partner_drink")
    private String partnerDrink;
    @Column(name = "hair_color")
    private String hairColor;
    @Column(name = "partner_hair_color")
    private String partnerHairColor;
    @Column(name = "religion")
    private String religion;
    @Column(name = "partner_religion")
    private String partnerReligion;
    @Column(name = "pet")
    private String pet;
    @Column(name = "partner_pet")
    private String partnerPet;
    @Column(name = "language")
    private String language;
    @Column(name = "partner_language")
    private String partnerLanguage;
    @Convert(converter = StringListConverter.class)
    @Column(name = "ethnicity")
    private List<String> ethnicity;
    @Column(name = "partner_ethnicity")
    private String partnerEthnicity;
    @Column(name = "occupation")
    private String occupation;
    @Column(name = "partner_occupation")
    private String partnerOccupation;
    @Column(name = "salary")
    private String salary;
    @Column(name = "partner_salary")
    private String partnerSalary;
    @Column(name = "children_status")
    private String childrenStatus;
    @Column(name = "partner_children_status")
    private String partnerChildrenStatus;
    @Column(name = "astro_sign")
    private String astroSign;
    @Column(name = "partner_astro_sign")
    private String partnerAstroSign;
    @Column(name = "education")
    private String education;
    @Column(name = "partner_education")
    private String partnerEducation;
    @Column(name = "eye_color")
    private String eyeColor;
    @Column(name = "partner_eye_color")
    private String partnerEyeColor;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "partner_description")
    private String partnerDescription;
    @Column(name = "perfect_date")
    private String perfectDate;

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

    public String getPartnerBodyType() {
        return partnerBodyType;
    }

    public void setPartnerBodyType(String partnerBodyType) {
        this.partnerBodyType = partnerBodyType;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getPartnerSmoke() {
        return partnerSmoke;
    }

    public void setPartnerSmoke(String partnerSmoke) {
        this.partnerSmoke = partnerSmoke;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getPartnerDrink() {
        return partnerDrink;
    }

    public void setPartnerDrink(String partnerDrink) {
        this.partnerDrink = partnerDrink;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getPartnerHairColor() {
        return partnerHairColor;
    }

    public void setPartnerHairColor(String partnerHairColor) {
        this.partnerHairColor = partnerHairColor;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPartnerReligion() {
        return partnerReligion;
    }

    public void setPartnerReligion(String partnerReligion) {
        this.partnerReligion = partnerReligion;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getPartnerPet() {
        return partnerPet;
    }

    public void setPartnerPet(String partnerPet) {
        this.partnerPet = partnerPet;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPartnerLanguage() {
        return partnerLanguage;
    }

    public void setPartnerLanguage(String partnerLanguage) {
        this.partnerLanguage = partnerLanguage;
    }

    public List<String> getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(List<String> ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getPartnerEthnicity() {
        return partnerEthnicity;
    }

    public void setPartnerEthnicity(String partnerEthnicity) {
        this.partnerEthnicity = partnerEthnicity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPartnerOccupation() {
        return partnerOccupation;
    }

    public void setPartnerOccupation(String partnerOccupation) {
        this.partnerOccupation = partnerOccupation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPartnerSalary() {
        return partnerSalary;
    }

    public void setPartnerSalary(String partnerSalary) {
        this.partnerSalary = partnerSalary;
    }

    public String getChildrenStatus() {
        return childrenStatus;
    }

    public void setChildrenStatus(String childrenStatus) {
        this.childrenStatus = childrenStatus;
    }

    public String getPartnerChildrenStatus() {
        return partnerChildrenStatus;
    }

    public void setPartnerChildrenStatus(String partnerChildrenStatus) {
        this.partnerChildrenStatus = partnerChildrenStatus;
    }

    public String getAstroSign() {
        return astroSign;
    }

    public void setAstroSign(String astroSign) {
        this.astroSign = astroSign;
    }

    public String getPartnerAstroSign() {
        return partnerAstroSign;
    }

    public void setPartnerAstroSign(String partnerAstroSign) {
        this.partnerAstroSign = partnerAstroSign;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPartnerEducation() {
        return partnerEducation;
    }

    public void setPartnerEducation(String partnerEducation) {
        this.partnerEducation = partnerEducation;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getPartnerEyeColor() {
        return partnerEyeColor;
    }

    public void setPartnerEyeColor(String partnerEyeColor) {
        this.partnerEyeColor = partnerEyeColor;
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
}
