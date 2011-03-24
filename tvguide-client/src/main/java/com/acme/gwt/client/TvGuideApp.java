package com.acme.gwt.client;

import com.acme.gwt.client.widget.TvGuideAppShell;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.requestfactory.shared.DefaultProxyStore;
import com.google.gwt.requestfactory.shared.ProxySerializer;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.inject.Inject;

/**
 * Start the app up and running with the given user.
 */
public class TvGuideApp {
	@Inject
	TvGuideAppShell shell;
	@Inject
	PlaceHistoryHandler historyHandler;
	@Inject
	TvGuideRequestFactory rf;

	public void setUser(TvViewerProxy user) {
		// Attach the root view to the page
		RootLayoutPanel.get().add(shell);

		// Go! Fire the current history token
		historyHandler.handleCurrentHistory();
	}

	/**
	 * @param proxyStoreData
	 */
	public void setStoreData(String proxyStoreData) {
		DefaultProxyStore store = new DefaultProxyStore(proxyStoreData);
		ProxySerializer s = rf.getSerializer(store);
		setUser(s.deserialize(TvViewerProxy.class, TvViewerProxy.STORE_KEY));
		//if this fails, go to login.... stupid users, tampering with the page
		//TODO
	}
}
