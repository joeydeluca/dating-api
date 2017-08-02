package com.joe.dating.domain.fields;

import com.joe.dating.domain.user.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public enum ProfileFieldGroup {
    ETHNICITY(Ethnicity.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    HEIGHT_FEET(HeightFeet.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    HEIGHT_INCH(HeightInch.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    BODY_TYPE(BodyType.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    EYE_COLOR(EyeColor.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    HAIR_COLOR(HairColor.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    FAVORITE_PETS(Pet.values(), FieldCategory.DETAILS, FieldInputType.SELECT_MANY),
    LANGUAGE(Language.values(), FieldCategory.DETAILS, FieldInputType.SELECT_MANY),
    RELIGION(Religion.values(), FieldCategory.DETAILS, FieldInputType.SELECT_ONE),
    OCCUPATION(Occupation.values(), FieldCategory.DETAILS, FieldInputType.SELECT_ONE),
    SALARY(Salary.values(), FieldCategory.DETAILS, FieldInputType.SELECT_ONE),
    CHILDREN_STATUS(ChildrenStatus.values(), FieldCategory.DETAILS, FieldInputType.SELECT_ONE),
    ASTRO_SIGN(AstroSign.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    EDUCATION(Education.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    SMOKE(Smoke.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    DRINK(Drink.values(), FieldCategory.APPEARANCE, FieldInputType.SELECT_ONE),
    ;

    private final ProfileField[] profileFields;
    private final FieldCategory fieldCategory;
    private final FieldInputType fieldInputType;

    ProfileFieldGroup(ProfileField[] profileFields, FieldCategory fieldCategory, FieldInputType fieldInputType) {
        this.profileFields = profileFields;
        this.fieldCategory = fieldCategory;
        this.fieldInputType = fieldInputType;
    }

    public String getFieldName() {
        return this.name();
    }

    public FieldCategory getCategory() {
        return fieldCategory;
    }

    public FieldInputType getFieldInputType() {
        return fieldInputType;
    }

    public List<Option> getOptions(ResourceBundle resourceBundle) {
        List<Option> options = new ArrayList<>();
        for(ProfileField profileField : profileFields) {
            options.add(new Option(profileField.getId(), resourceBundle.getString(profileField.getClass().getSimpleName() + "." + profileField.getId())));
        }
        return options;
    }

}
