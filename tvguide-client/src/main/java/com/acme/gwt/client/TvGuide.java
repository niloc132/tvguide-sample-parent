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
import com.acme.gwt.client.ioc.TvGuideGinjector;
import com.acme.gwt.shared.TvViewerProxy;
import com.acme.gwt.shared.util.Md5;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.requestfactory.shared.RequestFactory;
import com.google.gwt.user.client.AsyncProxy;
import com.google.gwt.user.client.AsyncProxy.ConcreteType;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author colin
 */
public class TvGuide implements EntryPoint {

	interface LoginController extends LoginWidget.Presenter {
		void setInjector(TvGuideGinjector injector);
	}
	/**
	 * Async Proxy to load the MD5+RF+Proxy after the view is up and running.
	 * 
	 * @author colin
	 *
	 */
	@ConcreteType(LoginControllerImpl.class)
	interface LoginControllerProxy
	extends LoginController,
	AsyncProxy<LoginController> {
	}

	/**
	 * Actual presenter impl for the Login widget, loaded when the first call comes in to it
	 * @author colin
	 *
	 */
	static final class LoginControllerImpl extends Receiver<TvViewerProxy> implements LoginController {
		private AuthRF rf;
		private TvGuideApp app;

		@Override
		public void setInjector(TvGuideGinjector injector) {
			rf = injector.authRF();
			app = injector.app();
		}

		private LoginWidget loginWidget;

		@Override
		public void login(String email, String password) {
			rf.authReq().authenticate(email, Md5.md5Hex(password)).with("geo", "name").fire(this);
		}

		@Override
		public void register(String email, String password) {
			rf.authReq().register(email, Md5.md5Hex(password)).with("geo", "name").fire(this);
		}
		@Override
		public void onSuccess(TvViewerProxy response) {
			app.setUser(response);
			loginWidget.removeFromParent();
		}

		@Override
		public void setView(LoginWidget w) {
			this.loginWidget = w;
		}
	}
	public interface AuthRF extends RequestFactory {
		TvViewerProxy.TvViewerRequest authReq();
	}
	public void onModuleLoad() {
		// Make an injector
		TvGuideGinjector injector = GWT.create(TvGuideGinjector.class);

		// if not yet logged in, make a login widget, and give it a presenter
		if (true) {
			// Create a login proxy
			LoginController presenter = GWT.create(LoginControllerProxy.class);
			presenter.setInjector(injector);
			RootPanel.get().add(new LoginWidget(presenter));
		} else {
			// already logged in, so fire up the app
			TvGuideApp app = injector.app();
			app.setInjector(injector);
			//TODO
			//app.setUser(user);
		}
	}

}
