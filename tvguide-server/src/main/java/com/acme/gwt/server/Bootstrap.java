package com.acme.gwt.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.acme.gwt.data.TvShow;
import com.acme.gwt.data.TvViewer;
import com.acme.gwt.shared.defs.Geo;
import com.acme.gwt.shared.util.Md5;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.thoughtworks.xstream.XStream;

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

		Injector i = Guice.createInjector(new TvGuideServiceModule());

		try {
			Bootstrap b = i.getInstance(Bootstrap.class);
			TvViewer tvViewer = b.init();

			XStream xStream = new XStream();
			xStream.autodetectAnnotations(true);
			xStream.toXML(tvViewer, System.err);
		} finally {
			i.getInstance(EntityManagerFactory.class).close();
		}

	}

	@Inject
	Provider<EntityManager> emProvider;

	public TvViewer init() {
		EntityManager em = emProvider.get();
		em.getTransaction().begin();
		TvViewer tvViewer = null;
		try {
			tvViewer = new TvViewer();

			tvViewer.setEmail("you@example.com");

			tvViewer.setDigest(Md5.md5Hex("sa"));
			tvViewer.setSalt("unused");
			tvViewer.setGeo(Geo.CALIFORNIA);

			em.persist(tvViewer);

			TvShow tvShow = new TvShow();
			tvShow.setDescription("the Show is #1");
			tvShow.setName("show1");
			em.persist(tvShow);
			tvShow = new TvShow();
			tvShow.setDescription("the Show is #2");
			tvShow.setName("show2");
			em.persist(tvShow);
			tvShow = new TvShow();
			tvShow.setDescription("the Show is #3");
			tvShow.setName("show3");
			em.persist(tvShow);
			tvViewer.getFavorites().add(tvShow);

			tvShow = new TvShow();
			tvShow.setDescription("the Show is #4");
			tvShow.setName("show4");

			em.persist(tvShow);
			tvShow = new TvShow();
			tvShow.setDescription("the Show is #5");
			tvShow.setName("show5");

			em.persist(tvShow);
			tvShow = new TvShow();
			tvShow.setDescription("the Show is #6");
			tvShow.setName("show6");
			em.persist(tvShow);
			tvViewer.getFavorites().add(tvShow);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			e.printStackTrace();
		}

		return tvViewer;
	}

}
