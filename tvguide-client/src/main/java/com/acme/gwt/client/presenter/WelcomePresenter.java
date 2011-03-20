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

import java.util.List;

import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.client.place.WelcomePlace;
import com.acme.gwt.client.view.WelcomeView;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;

/**
 * @author colin
 */
public class WelcomePresenter extends AbstractActivity
		implements
			WelcomeView.Presenter {
	@Inject
	WelcomeView view;
	@Inject
	TvGuideRequestFactory rf;

	public WelcomePresenter(WelcomePlace place) {
		//place is meaningless, this is just a simple view to show
	}

	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		rf.makeGuideRequest().getFavoriteShows().fire(
				new Receiver<List<TvShowProxy>>() {
					@Override
					public void onSuccess(List<TvShowProxy> response) {
						view.getDriver().display(response);
						panel.setWidget(view);
					}
				});
	}
}