package com.joe.dating.domain.validaiton;

/**
 * Created by Joe Deluca on 11/29/2016.
 */
public class ValidationException extends RuntimeException {
    private String fieldName;

    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
