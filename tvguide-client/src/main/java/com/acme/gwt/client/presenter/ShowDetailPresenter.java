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

import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.client.place.ShowDetailPlace;
import com.acme.gwt.client.view.ShowDetailView;
import com.acme.gwt.shared.TvEpisodeProxy;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/**
 * @author colin
 *
 */
public class ShowDetailPresenter extends AbstractActivity
		implements
			ShowDetailView.Presenter {
	@Inject
	ShowDetailView view;

	@Inject
	TvGuideRequestFactory rf;

	@Inject
	PlaceController placeController;

	private final ShowDetailPlace place;
	@Inject
	public ShowDetailPresenter(@Assisted
	ShowDetailPlace place) {
		this.place = place;
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		//load it and show it
		rf.find(place.getId()).with(view.getEditor().getPaths()).to(
				new Receiver<TvShowProxy>() {
					@Override
					public void onSuccess(TvShowProxy response) {
						//deal with data, wire into view
						view.getEditor().display(response);

						//show view
						panel.setWidget(view);
					}
				}).fire();
	}

	@Override
	public void focusEpisode(TvEpisodeProxy episode) {
		//TODO make a EpisodeDetailPlace around the entity id, push it
		//placeController.goTo(...);
	}

	@Override
	public void back() {
		History.back();
	}
}
