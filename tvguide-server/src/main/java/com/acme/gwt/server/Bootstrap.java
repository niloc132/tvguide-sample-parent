package com.acme.gwt.server;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.acme.gwt.data.TvShow;
import com.acme.gwt.data.ViewerProfile;
import com.acme.gwt.shared.defs.Geo;
import com.thoughtworks.xstream.XStream;
import org.apache.openjpa.persistence.OpenJPAEntityManager;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 1:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrap {

  /**
   * creates a user and some shows
   *
   * @param args
   */
  public static void main(String args[]) {
    EntityManager entityManager = Persistence.createEntityManagerFactory(null).createEntityManager();


    OpenJPAEntityManager em = (OpenJPAEntityManager) entityManager;
    em.begin();

    ViewerProfile viewerProfile = new ViewerProfile();
    final Collection a = new LinkedHashSet();

    viewerProfile.setEmail("you@example.com");
    viewerProfile.setDigest("sa");
    viewerProfile.setSalt("unused");
    viewerProfile.setGeo(Geo.CALIFORNIA);
    viewerProfile.setFavorites(new LinkedList<TvShow>(new LinkedList<TvShow>() {{
      TvShow tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #1");
      tvShow.setName("show1");
      a.add(tvShow);//diverted to the master list.
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #2");
      tvShow.setName("show2");
      a.add(tvShow);//diverted from the master list
      // this will excercise cascade=PERSIST
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #3");
      tvShow.setName("show3");
      a.add(tvShow);//sent to both lists
      add(tvShow);  //sent to both lists
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #4");
      tvShow.setName("show4");
      a.add(tvShow);
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #5");
      tvShow.setName("show5");
      a.add(tvShow);
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #6");
      tvShow.setName("show6");
      a.add(tvShow);
    }}));
    try {
      em.merge(viewerProfile);
      em.commit();
    } catch (Exception e) {
      if (em.isActive()) em.rollback();
      e.printStackTrace();
    } finally {
      entityManager.close();
    }

    XStream xStream = new XStream();
    xStream.autodetectAnnotations(true);
    xStream.toXML(a, System.err);

  }

}
