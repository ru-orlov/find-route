package com.ruorlov.interview.findroute.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruorlov.interview.findroute.entity.Country;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
public class CountryServiceImpl implements CountryService {
    final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${countries.download.url}")
    private String countriesUrl;
    @PostConstruct
    @Cacheable("countries")
    public List<Country> getCountries() {
        try {
            return objectMapper.readValue(downloadCountryFile(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String downloadCountryFile() {
        RestTemplate restTemplate = new RestTemplate();
        byte[] byteContent = Objects.requireNonNull(restTemplate.getForObject(countriesUrl, String.class)).getBytes();
        InputStream resourceInputStream = new ByteArrayInputStream(byteContent);
        Scanner scanner = new Scanner(resourceInputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
