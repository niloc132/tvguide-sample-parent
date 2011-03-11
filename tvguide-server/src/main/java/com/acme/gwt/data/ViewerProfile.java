package com.acme.gwt.data;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import com.acme.gwt.server.TvGuideService;
import com.acme.gwt.shared.defs.Geo;


/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public
@NamedQuery(name = ViewerProfile.SIMPLE_AUTH, query = "select vp from ViewerProfile vp where vp.email=:email and vp.digest=:digest")
class ViewerProfile implements HasVersionAndId {

  static final String SIMPLE_AUTH = "simpleAuth";
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

  private Geo geo;
  private String email;
  private String digest;
  private String salt;


  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
  @OrderColumn(name = "rank")
  public List<TvShow> getFavorites() {
    return favorites;
  }

  //end entity cut+paste header.  the real data below:
  private List<TvShow> favorites = new LinkedList<TvShow>();

  public void setFavorites(List<TvShow> favorites) {
    this.favorites = favorites;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
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

  // todo: @Finder (namedQuery = SIMPLE_AUTH)static ViewerProfile findViewerProfileByEmailAndDigest(String email,String digest){}

  //handwritten finder
  static ViewerProfile findViewerProfileByEmailAndDigest(String email, String digest) {
    try {
      return new TvGuideService.Em().call().createNamedQuery(SIMPLE_AUTH, ViewerProfile.class).setParameter("email", email).setParameter("digest", digest).getSingleResult();
    } catch (Exception e) {
      e.printStackTrace();  //todo: verify for a fit
    }
    return null;
  }
}