package com.acme.gwt.client;

import com.acme.gwt.client.ioc.TvGuideGinjector;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * all the crap that needs big java imports begins polluting the heap here.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 4:24 AM
 * To change this template use File | Settings | File Templates.
 */
class TvGuideApp implements GateKeeper.App {

	@Override
	public void setUser(TvViewerProxy user) {
		// Make the ginjector
		TvGuideGinjector injector = GWT.create(TvGuideGinjector.class);

		// Attach the root view to the page
		RootLayoutPanel.get().add(injector.getAppShell());

		// Go! Fire the current history token
		injector.getHistoryHandler().handleCurrentHistory();
	}
}
