package com.acme.gwt.client;

import com.acme.gwt.client.ioc.TvGuideGinjector;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.AsyncProxy;
import com.google.gwt.user.client.AsyncProxy.ConcreteType;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * An imports barrier to minimize the initial push to client.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 4:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class GateKeeper extends Receiver<TvViewerProxy> implements TvGuideApp {
	private TvGuideGinjector injector;
	@Override
	public void setInjector(TvGuideGinjector injector) {
		this.injector = injector;
	}
	@Override
	public void onSuccess(TvViewerProxy response) {
		setUser(response);
	}

	@Override
	public void setUser(TvViewerProxy user) {
		// Attach the root view to the page
		RootLayoutPanel.get().add(injector.getAppShell());

		// Go! Fire the current history token
		injector.getHistoryHandler().handleCurrentHistory();
	}


	@ConcreteType(GateKeeper.class)
	public interface Proxy extends AsyncProxy<TvGuideApp>, TvGuideApp {
	}
}
