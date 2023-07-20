package com.ruorlov.interview.findroute.tools;

import com.ruorlov.interview.findroute.entity.Country;

import java.util.*;

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
			//log.debug("Visiting " + currentCountry.getName());
			if (currentCountry.equals(destination)) {
				//log.debug("Origin and destination are equal");
				break;
			} else {
				for (var neighbour : currentCountry.getBorders()) {
					var neighbourCountry = countries.get(neighbour);
					if(!visited.containsKey(neighbourCountry)){
						//log.debug("... registering neighbour " + neighbourCountry.getName());
						pivot.add(neighbourCountry);
						visited.put(neighbourCountry, true);
						previous.put(neighbourCountry, currentCountry);
						if (neighbourCountry.equals(destination)) {
							//log.debug("Shortest path found");
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
			System.out.println("Cannot reach the path");
			//throw new NoPathException("Cannot reach the path");
		}

		List<Country> path = new ArrayList<>();
		for (Country node = destination; node != null; node = previous.get(node)) {
			path.add(node);
		}
		return null;
//		return path.stream()
//			.map(Country::getName)
//			.collect(MyCollectors.reversing());
	}
}
