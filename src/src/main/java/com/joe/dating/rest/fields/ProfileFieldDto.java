package com.joe.dating.rest.fields;

import com.joe.dating.domain.fields.FieldCategory;
import com.joe.dating.domain.fields.FieldInputType;
import com.joe.dating.domain.fields.Option;

import java.util.List;

/**
 * Created by Joe Deluca on 11/25/2016.
 */
public class ProfileFieldDto {
    private List<Option> options;
    private FieldInputType fieldInputType;
    private FieldCategory category;

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public FieldInputType getFieldInputType() {
        return fieldInputType;
    }

    public void setFieldInputType(FieldInputType fieldInputType) {
        this.fieldInputType = fieldInputType;
    }

    public FieldCategory getCategory() {
        return category;
    }

    public void setCategory(FieldCategory category) {
        this.category = category;
    }
}