package com.acme.gwt.shared;

import com.acme.gwt.data.TvViewer;
import com.acme.gwt.server.InjectingLocator;
import com.acme.gwt.server.InjectingServiceLocator;
import com.acme.gwt.server.TvViewerService;
import com.acme.gwt.shared.defs.Geo;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 9:31 PM
 * To change this template use File | Settings | File Templates.
 */
@ProxyFor(value = TvViewer.class, locator = InjectingLocator.class)
public interface TvViewerProxy extends EntityProxy {
	public static final String STORE_KEY = "init_auth";
	String getEmail();

	void setEmail(String email);

	/** auth data can be saved, but cannot be retrieved from the server */
	void setDigest(String digest);

	//do we need either of these, given the present impl?
	String getSalt();
	void setSalt(String salt);

	Geo getGeo();

	void setGeo(Geo geo);

	EntityProxyId<TvViewerProxy> stableId();

	@Service(value = TvViewerService.class, locator = InjectingServiceLocator.class)
	public interface TvViewerRequest extends RequestContext {
		//replace with controller
		Request<TvViewerProxy> authenticate(String email, String digest);
		Request<TvViewerProxy> register(String email, String digest);
		Request<Void> deauth();
	}
}
