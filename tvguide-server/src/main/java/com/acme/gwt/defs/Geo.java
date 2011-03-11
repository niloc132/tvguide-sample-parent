package com.acme.gwt.defs;

import com.acme.gwt.defs.Continent;
import com.acme.gwt.defs.Region;

public enum Geo {
  CALIFORNIA(Continent.NAMERICA, Region.WEST),
  HK(Continent.ASIA, Region.SOUTH);
  private final Continent continent;
  private final Region region;

  Geo(Continent continent, Region region) {
    //todo: review for a fit
    this.continent = continent;
    this.region = region;
  }
}
