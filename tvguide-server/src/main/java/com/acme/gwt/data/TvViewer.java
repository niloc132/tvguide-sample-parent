package com.acme.gwt.data;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.acme.gwt.shared.defs.Geo;
import com.google.inject.Inject;
import com.google.inject.Provider;


/**
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

	//  private Geo geo;
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

	public static TvViewer authenticate(String email, String digest) {
		TvViewer user = findTvViewerByEmailAndDigest(email, digest);

		//TODO Stick user, or a way to get the user, in the session. This code can't talk 
		// to HttpSession, as it doesn't know about servlets

		return user;
	}

	// todo: @Finder (namedQuery = SIMPLE_AUTH)static TvViewer findTvViewerByEmailAndDigest(String email,String digest){}

	//handwritten finder
	@Inject static Provider<EntityManager> emProvider;
	public static TvViewer findTvViewerByEmailAndDigest(String email, String digest) {
		try {
			//digest is md5'd on client
			return emProvider.get().createQuery("select vp from TvViewer vp where vp.email=:email and vp.digest=:digest", TvViewer.class).setParameter("email", email).setParameter("digest", digest).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();  //todo: verify for a fit
		}
		return null;
	}
}