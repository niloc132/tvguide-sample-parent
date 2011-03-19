package com.acme.gwt.client;

import com.acme.gwt.client.ioc.TvGuideGinjector;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.inject.Inject;

/**
 * Basic interface to get the app going. Wow, we need to rename some of this stuff.
 * 
 * This can be called as a {@link com.google.gwt.requestfactory.shared.Receiver} for
 * TvViewerProxy objects, or setUser can be called directly. setInjector must be called
 * first to provide an injector instance.
 */
public interface TvGuideApp {
	@Inject
	void setInjector(TvGuideGinjector injector);

	void onSuccess(TvViewerProxy response);
	void setUser(TvViewerProxy user);
}
