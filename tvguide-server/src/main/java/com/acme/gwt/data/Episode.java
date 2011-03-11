package com.acme.gwt.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public
@Entity
class Episode implements HasVersionAndId {

  private Long id;

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

  private TvShow tvShow;
  private Integer season;
  private Integer episodeNumber;
  private String name;

  public TvShow getTvShow() {
    return tvShow;
  }

  public void setTvShow(TvShow tvShow) {
    this.tvShow = tvShow;
  }

  public Integer getSeason() {
    return season;
  }

  public void setSeason(Integer season) {
    this.season = season;
  }

  public Integer getEpisodeNumber() {
    return episodeNumber;
  }

  public void setEpisodeNumber(Integer episodeNumber) {
    this.episodeNumber = episodeNumber;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
