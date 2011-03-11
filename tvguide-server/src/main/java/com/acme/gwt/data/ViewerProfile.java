package com.acme.gwt.data;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import com.acme.gwt.defs.Geo;


/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
public
@Entity
class ViewerProfile implements HasVersionAndId {

  private Long id;
  private List<Show> favoriteShows;
  private Geo geo;

  @Id
  public Long getId() {
    return id;
  }


  private Integer version;

  @Version
  public Integer getVersion() {
    return version;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  private String name;
  private String digest;
  private String salt;


  @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @JoinTable(name = "user_favorite_shows")
  @OrderColumn(name = "rank")
  public List<Show> getFavoriteShows() {
    return favoriteShows;
  }

  public void setFavoriteShows(List<Show> favoriteShows) {
    this.favoriteShows = favoriteShows;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDigest() {
    return digest;
  }

  public void setDigest(String digest) {
    this.digest = digest;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  @Enumerated(EnumType.STRING)
  public Geo getGeo() {
    return geo;
  }

  public void setGeo(Geo geo) {
    this.geo = geo;
  }
}