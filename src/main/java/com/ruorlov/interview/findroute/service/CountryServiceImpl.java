package com.ruorlov.interview.findroute.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruorlov.interview.findroute.dto.CountryDto;
import com.ruorlov.interview.findroute.entity.Country;
import com.ruorlov.interview.findroute.mapper.CountryMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class CountryServiceImpl implements CountryService {
    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    @Cacheable("countries")
    public List<Country> getCountries() {
        try {
            String countriesStr = downloadCountryFile();
            return objectMapper.readValue(countriesStr, new TypeReference<List<Country>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
