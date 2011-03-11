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
class TvChannel implements HasVersionAndId {

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

  private String name;
  private String icon;
  private Integer channelNumber;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getChannelNumber() {
    return channelNumber;
  }

  public void setChannelNumber(Integer channelNumber) {
    this.channelNumber = channelNumber;
  }
}
