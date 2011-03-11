package com.acme.gwt.shared;

import java.util.List;

import com.acme.gwt.data.TvViewer;
import com.acme.gwt.server.InjectingServiceLocator;
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
public interface TvViewerProxy extends EntityProxy {

  Long getId();

  Integer getVersion();

  void setId(Long id);

  void setVersion(Integer version);

  List<TvShowProxy> getFavoriteShows();

  void setFavoriteShows(List<TvShowProxy> favoriteTvShows);

  String getName();

  void setName(String name);

  String getDigest();

  void setDigest(String digest);

  String getSalt();

  void setSalt(String salt);
/*
  Geo getGeo();

  void setGeo(Geo geo);*/
}

@Service(value = TvViewer.class, locator = InjectingServiceLocator.class)
interface TvViewerRequest extends Request<TvViewerProxy> {
  //replace with controller
  Request<TvViewerProxy> authenticate(String email, String digest);
}