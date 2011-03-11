package com.acme.gwt.server;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.acme.gwt.data.TvShow;
import com.acme.gwt.data.TvViewer;
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
    EntityManager entityManager = Persistence.createEntityManagerFactory("tvgtest").createEntityManager();


    final OpenJPAEntityManager em = (OpenJPAEntityManager) entityManager;
    em.begin();
    TvViewer tvViewer = null;
    try {
      tvViewer = new TvViewer();


      tvViewer.setEmail("you@example.com");
      tvViewer.setDigest("sa");
      tvViewer.setSalt("unused");

      em.persist(tvViewer);

      TvShow tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #1");
      tvShow.setName("show1");
      em.persist(tvShow);
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #2");
      tvShow.setName("show2");
      em.persist(tvShow);
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #3");
      tvShow.setName("show3");
      em.persist(tvShow);
      tvViewer.getFavorites().add(tvShow);

      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #4");
      tvShow.setName("show4");

      em.persist(tvShow);
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #5");
      tvShow.setName("show5");

      em.persist(tvShow);
      tvShow = new TvShow();
      tvShow.setDescription("the tvShow is #6");
      tvShow.setName("show6");
      em.persist(tvShow);
      tvViewer.getFavorites().add(tvShow);
      em.commit();
    } catch (Exception e) {
      if (em.isActive()) em.rollback();
      e.printStackTrace();
    } finally {
      entityManager.close();
    }

    XStream xStream = new XStream();
    xStream.autodetectAnnotations(true);
    xStream.toXML(tvViewer, System.err);

  }

}
