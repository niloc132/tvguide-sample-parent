package com.acme.gwt.shared.defs;

public enum Geo {
  CALIFORNIA(Continent.NAMERICA, Region.WEST), HK(Continent.ASIA,
      Region.SOUTH);
  private final Continent continent;
  private final Region region;

  Geo(Continent continent, Region region) {
    //todo: review for a fit
    this.continent = continent;
    this.region = region;
  }
}
