package com.joe.dating.domain.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Option {
    private String id;
    @JsonProperty("name")
    private String display;

    public Option() {
    }

    public Option(String id, String display) {
        this.id = id;
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
