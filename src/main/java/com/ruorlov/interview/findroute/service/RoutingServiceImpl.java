package com.ruorlov.interview.findroute.service;

import com.ruorlov.interview.findroute.entity.Country;
import com.ruorlov.interview.findroute.exception.NoPathException;
import com.ruorlov.interview.findroute.tools.WalkAround;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoutingServiceImpl implements RoutingService {

	private final CountryService countryService;

	public RoutingServiceImpl(CountryService countryService) {
		this.countryService = countryService;
	}

	@Override
	public List<String> route(String origin, String destination) {

		var countries = countryService.getCountries().stream()
			.collect(Collectors.toMap(Country::getCca3, Function.identity()));

		var originCountry = Optional.ofNullable(countries.get(origin))
			.orElseThrow(() -> new NoPathException(String.format("Unknown origin country %s", origin)));

		var destinationCountry = Optional.ofNullable(countries.get(destination))
			.orElseThrow(() -> new NoPathException(String.format("Unknown destination country %s", destination)));

		if (!originCountry.getRegion().connectedWith(destinationCountry.getRegion())) {
			throw new NoPathException(String.format(
				"%s (%s) is not connected with %s (%s) by land",
				originCountry.getRegion(), origin,
				destinationCountry.getRegion(), destination));
		}

		if (!origin.equals(destination)) {
			if (originCountry.getBorders().isEmpty()) {
				throw new NoPathException(String.format("Origin %s is isolated", origin));
			}

			if (destinationCountry.getBorders().isEmpty()) {
				throw new NoPathException(String.format("Destination %s is isolated", destination));
			}
		}

		var routes = new WalkAround(countries, originCountry, destinationCountry).paths();

		return routes;
	}
}
