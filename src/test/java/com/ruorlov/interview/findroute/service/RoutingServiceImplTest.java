package com.ruorlov.interview.findroute.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruorlov.interview.findroute.entity.Country;
import com.ruorlov.interview.findroute.entity.Region;
import com.ruorlov.interview.findroute.exception.NoPathException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class RoutingServiceImplTest {

	final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private RoutingService routingService;

	@MockBean
	private CountryService countryService;

	@BeforeEach
	private void beforeEach() {
		Mockito.clearInvocations(countryService);
	}

	@Test
	void routeThrowExceptionUnknownOrigin() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e2\", \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\", \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\", \"e2\" ]\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
			var exception = Assertions.assertThrows(NoPathException.class,
					() -> routingService.route("BLA", "e3"));
			Assertions.assertEquals("Unknown origin country BLA", exception.getMessage());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void routeThrowExceptionUnknownDestination() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e2\", \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\", \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\", \"e2\" ]\n" +
				"} ]";
		try {
		List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
		Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		var exception = Assertions.assertThrows(NoPathException.class,
			() -> routingService.route("e1", "BLA"));
		Assertions.assertEquals("Unknown destination country BLA",
			exception.getMessage());
	}

	@Test
	void routeThrowExceptionIsolatedOrigin() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : []\n" +
				"}, {\n" +
				"  \"cca3\" : \"e2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e2\" ]\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		var exception = Assertions.assertThrows(NoPathException.class,
			() -> routingService.route("e1", "e3"));
		Assertions.assertEquals("Origin e1 is isolated", exception.getMessage());
	}

	@Test
	void routeThrowExceptionIsolatedDestination() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e2\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : []\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		var exception = Assertions.assertThrows(NoPathException.class,
			() -> routingService.route("e1", "e3"));
		Assertions.assertEquals("Destination e3 is isolated", exception.getMessage());
	}

	@Test
	void routeSameOriginAndDestination() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e2\", \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\", \"e3\" ]\n" +
				"}, {\n" +
				"  \"cca3\" : \"e3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [ \"e1\", \"e2\" ]\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		Assertions.assertEquals(List.of("e1"),
			routingService.route("e1", "e1"));
	}

	@Test
	void routeSameIsolatedOriginAndDestination() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : []\n" +
				"}, {\n" +
				"  \"cca3\" : \"e2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : []\n" +
				"}, {\n" +
				"  \"cca3\" : \"e3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : []\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		Assertions.assertEquals(List.of("e1"),
			routingService.route("e1", "e1"));
	}

	@Test
	void routeThrowExceptionNonContinentalRoute() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"e1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : []\n" +
				"}, {\n" +
				"  \"cca3\" : \"a1\",\n" +
				"  \"region\" : \"Antarctic\",\n" +
				"  \"borders\" : []\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		var exception = Assertions.assertThrows(NoPathException.class,
			() -> routingService.route("e1", "a1"));
		Assertions.assertEquals(Region.Europe + " (e1) is not connected with " + Region.Antarctic + " (a1) by land",
			exception.getMessage());
	}

	@Test
	void routeSampleRoute() {
		String countriesStr = "[ {\n" +
				"  \"cca3\" : \"a1\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [\"a2\"]\n" +
				"}, {\n" +
				"  \"cca3\" : \"a2\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [\"a1\", \"a3\"]\n" +
				"}, {\n" +
				"  \"cca3\" : \"a3\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [\"a2\", \"a4\"]\n" +
				"}, {\n " +
				"  \"cca3\" : \"a4\",\n" +
				"  \"region\" : \"Europe\",\n" +
				"  \"borders\" : [\"a3\"]\n" +
				"} ]";
		try {
			List<Country> countries = objectMapper.readValue(countriesStr, new TypeReference<>() {});
			Mockito.when(countryService.getCountries()).thenReturn(countries);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		Assertions.assertEquals(List.of("a4", "a3", "a2", "a1"),
			routingService.route("a1", "a4"));
	}
}