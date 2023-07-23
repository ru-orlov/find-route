package com.ruorlov.interview.findroute.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountryServiceImplTest {
    @Autowired
    private CountryService countryService;
    @Test
    void downloadCountryFile() {
        Assertions.assertNotNull(countryService.getCountries());
    }
}
