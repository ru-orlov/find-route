package com.ruorlov.interview.findroute.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private String cca3;
    private Region region;
    private List<String> borders;

    public String getCca3() {
        return cca3;
    }

    public Region getRegion() {
        return region;
    }

    public List<String> getBorders() {
        return borders;
    }
}
