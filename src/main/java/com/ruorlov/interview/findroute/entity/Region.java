package com.ruorlov.interview.findroute.entity;

import java.util.Set;

public enum Region {

	Africa,
	Americas,
	Antarctic,
	Asia,
	Europe,
	Oceania;

	private static final Set<Region> CONTINENTAL = Set.of(Africa, Asia, Europe);

	public boolean connectedWith(Region region) {
		if (this == region) {
			return true;
		} else return (CONTINENTAL.contains(this) && CONTINENTAL.contains(region));
	}
}
