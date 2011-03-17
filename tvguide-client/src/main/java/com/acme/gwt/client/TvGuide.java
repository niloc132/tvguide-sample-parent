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

import com.acme.gwt.client.bootstrap.LoginWidget;
import com.acme.gwt.shared.TvViewerProxy;
import com.acme.gwt.shared.util.Md5;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.requestfactory.shared.RequestFactory;
import com.google.gwt.user.client.AsyncProxy;
import com.google.gwt.user.client.AsyncProxy.ConcreteType;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author colin
 */
public class TvGuide implements EntryPoint {

	/**
	 * Async Proxy to load the MD5+RF+Proxy after the view is up and running.
	 * 
	 * @author colin
	 *
	 */
	@ConcreteType(LoginPresenter.class)
	interface LoginPresenterProxy
			extends
				AsyncProxy<LoginWidget.Presenter>,
				LoginWidget.Presenter {
	}

	/**
	 * Actual presenter impl for the Login widget, loaded when the first call comes in to it
	 * @author colin
	 *
	 */
	static final class LoginPresenter implements LoginWidget.Presenter {
		private AuthRF rf = GWT.create(AuthRF.class);
		{
			rf.initialize(new SimpleEventBus());
		}

		private LoginWidget loginWidget;
		private final Receiver<Void> hideReceiver = new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				loginWidget.removeFromParent();
			}
		};
		@Override
		public void login(String email, String password) {
			rf.authReq().authenticate(email, Md5.md5Hex(password)).with("geo",
					"name", "favoriteShows.name", "favoriteShows.description")
					.to(new GateKeeper()).fire(hideReceiver);
		}

		@Override
		public void register(String email, String password) {
			rf.authReq().register(email, Md5.md5Hex(password)).with("geo",
					"name", "favoriteShows.name", "favoriteShows.description")
					.to(new GateKeeper()).fire(hideReceiver);
		}

		@Override
		public void setView(LoginWidget w) {
			this.loginWidget = w;
		}
	}
	interface AuthRF extends RequestFactory {
		TvViewerProxy.TvViewerRequest authReq();
	}
	public void onModuleLoad() {
		//TODO what do do if login is not required, and session is already going

		//Create a presenter proxy, and pass it to the login widget
		LoginWidget.Presenter presenter = GWT.create(LoginPresenterProxy.class);
		RootPanel.get().add(new LoginWidget(presenter));
	}

}
