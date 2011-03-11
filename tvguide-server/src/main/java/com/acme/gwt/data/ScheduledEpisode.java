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
class ScheduledEpisode implements HasVersionAndId {

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

  private Episode episode;
  private TvChannel tvChannel;
  private AirTime block;

  public Episode getEpisode() {
    return episode;
  }

  public void setEpisode(Episode episode) {
    this.episode = episode;
  }

  public TvChannel getTvChannel() {
    return tvChannel;
  }

  public void setTvChannel(TvChannel tvChannel) {
    this.tvChannel = tvChannel;
  }

  public AirTime getBlock() {
    return block;
  }

  public void setBlock(AirTime block) {
    this.block = block;
  }
}
