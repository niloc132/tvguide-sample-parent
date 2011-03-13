package com.acme.gwt.data;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Version;

import com.acme.gwt.server.AuthenticatedViewerProvider;
import com.acme.gwt.shared.defs.Geo;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Due to details in how this type is injected when code asks for the current user, this cannot
 * be created by calling injector.getInstance(TvViewer.class), but must be constructed the old
 * fashioned way. A 'create profile' method or the like would be the appropriate place to do this.
 * 
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 8:50 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
//@NamedQuery(name = TvViewer.SIMPLE_AUTH, query = "select vp from TvViewer vp where vp.email=:email and vp.digest=:digest")
public class TvViewer implements HasVersionAndId {

	static final String SIMPLE_AUTH = "simpleAuth";
	private Long id;
	private Geo geo;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	private String email;
	private String digest;
	private String salt;

	private List<TvShow> favorites = new LinkedList<TvShow>();

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@OrderColumn(name = "rank")
	public List<TvShow> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<TvShow> favorites) {
		this.favorites = favorites;
	}

	@Column(unique = true)
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

	@Inject
	static Provider<EntityManager> emProvider;
	@Inject
	static Provider<AuthenticatedViewerProvider> currentUserProvider;

	public static TvViewer authenticate(String email, String digest) {
		TvViewer user = null;
		try {
			user = findTvViewerByEmailAndDigest(email, digest);
			String email1 = user.getEmail();//throw NPE here if possible
			currentUserProvider.get().setCurrentViewer(user);
			return user;
		} catch (Throwable e) {
			throw new RuntimeException("Failed login attempt.");
		}
	}

	// todo: @Finder (namedQuery = SIMPLE_AUTH)static TvViewer findTvViewerByEmailAndDigest(String email,String digest){}

	//handwritten finder
	public static TvViewer findTvViewerByEmailAndDigest(String email,
			String digest) {
		TvViewer singleResult = null;
		try {
			singleResult = emProvider
					.get()
					.createQuery(
							"select vp from TvViewer vp where vp.email=:email and vp.digest=:digest",
							TvViewer.class).setParameter("email", email)
					.setParameter("digest", digest).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace(); //todo: verify for a fit
		} finally {
		}
		return singleResult;
	}
}