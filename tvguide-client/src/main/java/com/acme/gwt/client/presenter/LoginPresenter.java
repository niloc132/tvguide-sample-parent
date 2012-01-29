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
package com.acme.gwt.client.presenter;

import com.acme.gwt.client.TvGuideApp;
import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.client.view.LoginView;
import com.acme.gwt.shared.TvViewerProxy;
import com.acme.gwt.shared.util.Md5;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * All calls are void, allowing them to be async
 * 
 * @author colin
 *
 */
public class LoginPresenter extends Receiver<TvViewerProxy>
		implements
			LoginView.Presenter {
	@Inject
	TvGuideRequestFactory rf;
	@Inject
	AsyncProvider<TvGuideApp> appProvider;

	private LoginView view;

	@Override
	public void setView(LoginView view) {
		this.view = view;
	}

	@Override
	public void login(String email, String password) {
		rf.makeLoginRequest().authenticate(email, Md5.md5Hex(password)).fire(
				this);
	}

	@Override
	public void register(String email, String password) {
		rf.makeLoginRequest().register(email, Md5.md5Hex(password)).fire(this);
	}

	@Override
	public void onSuccess(final TvViewerProxy response) {
		view.asWidget().removeFromParent();
		appProvider.get(new AsyncCallback<TvGuideApp>() {
			@Override
			public void onSuccess(TvGuideApp result) {
				result.setUser(response);
			}
			@Override
			public void onFailure(Throwable caught) {
				//error message, try again
			}
		});
	}

	public IsWidget getView() {
		return view;
	}

}
