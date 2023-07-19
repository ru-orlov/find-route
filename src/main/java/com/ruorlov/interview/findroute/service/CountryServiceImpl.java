package com.ruorlov.interview.findroute.service;

import com.ruorlov.interview.findroute.entity.Country;
import jakarta.annotation.PostConstruct;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class CountryServiceImpl implements CountryService{
    @PostConstruct
    @Cacheable("countries")
    @Override
    public List<Country> getCountries() {


            System.out.println(">>>length" + downloadCountryFile().length());

        return null;
    }

    private String downloadCountryFile() {
        String url = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";

        RestTemplate restTemplate = new RestTemplate();
        byte[] byteContent = Objects.requireNonNull(restTemplate.getForObject(url, String.class)).getBytes();
        InputStream resourceInputStream = new ByteArrayInputStream(byteContent);
        Scanner s = new Scanner(resourceInputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
