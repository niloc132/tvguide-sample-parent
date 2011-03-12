package com.acme.gwt.client;

import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.AsyncProxy;
import com.google.gwt.user.client.AsyncProxy.ConcreteType;

/**
 * An imports barrier to minimize the initial push to client.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/11/11
 * Time: 4:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class GateKeeper extends Receiver<TvViewerProxy> {
	@Override
	public void onSuccess(TvViewerProxy response) {
		AppProxy app = GWT.<AppProxy>create(AppProxy.class);
		app.setUser(response);
	}

	interface App {
		void setUser(TvViewerProxy user);
	}
	@ConcreteType(TvGuideApp.class)
	interface AppProxy extends AsyncProxy<App>, App {}
}
