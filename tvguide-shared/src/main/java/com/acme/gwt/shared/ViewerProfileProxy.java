package com.acme.gwt.shared;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import com.acme.gwt.data.Show;
import com.acme.gwt.shared.defs.Geo;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ViewerProfileProxy {
  @Id
  Long getId();

  @Version
  Integer getVersion();

  void setId(Long id);

  void setVersion(Integer version);

  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @JoinTable(name = "user_favorite_shows")
  @OrderColumn(name = "rank")
  List<Show> getFavoriteShows();

  void setFavoriteShows(List<Show> favoriteShows);

  String getName();

  void setName(String name);

  String getDigest();

  void setDigest(String digest);

  String getSalt();

  void setSalt(String salt);

  @Enumerated(EnumType.STRING)
  Geo getGeo();

  void setGeo(Geo geo);
}
