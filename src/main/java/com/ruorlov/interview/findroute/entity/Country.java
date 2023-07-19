package com.ruorlov.interview.findroute.entity;

import java.util.List;

public class Country {
    private final String name;
    private final Region region;
    private final List<String> borders;

    public Country(String name, Region region, List<String> borders) {
        this.name = name;
        this.region = region;
        this.borders = borders;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public List<String> getBorders() {
        return borders;
    }
}
