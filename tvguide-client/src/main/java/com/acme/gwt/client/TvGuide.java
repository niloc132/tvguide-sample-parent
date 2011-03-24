/**
 *  Copyright 2011 Colin Alworth
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.acme.gwt.client;

import com.acme.gwt.client.ioc.TvGuideGinjector;
import com.acme.gwt.client.presenter.LoginPresenter;
import com.acme.gwt.client.view.LoginView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.AsyncProxy;
import com.google.gwt.user.client.AsyncProxy.ConcreteType;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * EntryPoint for the TvGuide sample GWT application. Decides if the user is already logged
 * in, and draws the app or the login. 
 * 
 * @author colin
 */
public class TvGuide implements EntryPoint {
	@Inject
	AsyncProvider<TvGuideApp> appProvider;
	@Inject
	Provider<LoginView> login;

	/**
	 * Async Proxy to load the MD5+RF+Proxy after the view is up and running. Only needed if 
	 * we don't use an AsyncProvider for the login stuff
	 * 
	 * @author colin
	 *
	 */
	@ConcreteType(LoginPresenter.class)
	interface LoginPresenterProxy
			extends
				AsyncProxy<LoginView.Presenter>,
				LoginView.Presenter {
	}

	public void onModuleLoad() {
		final TvGuideGinjector injector = GWT.create(TvGuideGinjector.class);
		injector.inject(this);

		final String proxyStoreData = getStoreData();
		if (proxyStoreData != null && !proxyStoreData.equals("")) {//if logged in already (read from page vars or some such)
			// start the page
			appProvider.get(new AsyncCallback<TvGuideApp>() {
				@Override
				public void onSuccess(TvGuideApp result) {
					result.setStoreData(proxyStoreData);
				}

				@Override
				public void onFailure(Throwable caught) {
					//TODO say something like 'an error occurred loading the page...'
				}
			});
		} else {//not logged in
			// show login (or allow unauthenticated access?)
			final LoginView w = login.get();
			// Give the login dialog a proxy to the LoginPresenter, which will manage its traffic once it has loaded.
			final LoginPresenterProxy presenter = GWT
					.create(LoginPresenterProxy.class);
			w.setPresenter(presenter);
			RootPanel.get().add(w);

			// tell the presenter about the view, so it can hide it
			// two ways in my mind about doing this, first, wait until the UI is up and going so the user can interact while it loads
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					presenter.setView(w);
				}
			});
			presenter
					.setProxyCallback(new AsyncProxy.ProxyCallback<LoginView.Presenter>() {
						@Override
						public void onInit(LoginView.Presenter instance) {
							injector.inject((LoginPresenter) instance);
						}
					});

			//Otherwise, flip this around, and get an AsyncProvider for the login presenter,
			//and wire it all up when the view is done and shown, and find another way to
			//worry about telling the view about the presenter.
		}
	}
	private native String getStoreData() /*-{
		return $wnd.store;
	}-*/;

}
