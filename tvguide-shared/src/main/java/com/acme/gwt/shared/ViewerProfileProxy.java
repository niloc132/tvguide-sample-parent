package com.acme.gwt.shared;

import java.util.List;

import com.acme.gwt.data.ViewerProfile;
import com.acme.gwt.server.InjectingServiceLocator;
import com.acme.gwt.shared.defs.Geo;
import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.Service;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ViewerProfileProxy extends EntityProxy {

  Long getId();

  Integer getVersion();

  void setId(Long id);

  void setVersion(Integer version);

  List<ShowProxy> getFavoriteShows();

  void setFavoriteShows(List<ShowProxy> favoriteShows);

  String getName();

  void setName(String name);

  String getDigest();

  void setDigest(String digest);

  String getSalt();

  void setSalt(String salt);

  Geo getGeo();

  void setGeo(Geo geo);
}

@Service(value = ViewerProfile.class, locator = InjectingServiceLocator.class)
interface ViewerProfileRequest extends Request<ViewerProfileProxy> {
  //replace with controller
  Request<ViewerProfileProxy> authenticate(String email, String digest);
}