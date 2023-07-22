package com.ruorlov.interview.findroute.tools;

import com.ruorlov.interview.findroute.entity.Country;
import com.ruorlov.interview.findroute.exception.NoPathException;

import java.util.*;
import java.util.stream.Collectors;

public class WalkAround {

	private final Map<String, Country> countries;
	private final Country origin;
	private final Country destination;

	private final Map<Country, Boolean> visited = new HashMap<>();
	private final Map<Country, Country> previous = new HashMap<>();

	public WalkAround(Map<String, Country> countries, Country origin, Country destination) {
		this.countries = countries;
		this.origin = origin;
		this.destination = destination;
	}

	public final List<String> paths() {
		var currentCountry = origin;

		Queue<Country> pivot = new ArrayDeque<>();
		pivot.add(currentCountry);

		visited.put(currentCountry, true);

		OUTER: while (!pivot.isEmpty()) {
			currentCountry = pivot.remove();
			if (currentCountry.equals(destination)) {
				break;
			} else {
				for (var neighbour : currentCountry.getBorders()) {
					var neighbourCountry = countries.get(neighbour);
					if(!visited.containsKey(neighbourCountry)){
						pivot.add(neighbourCountry);
						visited.put(neighbourCountry, true);
						previous.put(neighbourCountry, currentCountry);
						if (neighbourCountry.equals(destination)) {
							currentCountry = neighbourCountry;
							break OUTER;
						}
					} else {
						//log.debug("... skipping neighbour " + neighbourCountry.getName());
					}
				}
			}
		}

		if (!currentCountry.equals(destination)){
			throw new NoPathException("Cannot reach the path");
		}

		List<Country> path = new ArrayList<>();
		for (Country node = destination; node != null; node = previous.get(node)) {
			path.add(node);
		}
		return path.stream()
			.map(Country::getCca3)
			.collect(Collectors.toList());
	}
}
