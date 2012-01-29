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

import java.util.ArrayList;
import java.util.List;

import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.client.place.ShowEditorPlace;
import com.acme.gwt.client.view.ShowEditorView;
import com.acme.gwt.shared.TvEpisodeProxy;
import com.acme.gwt.shared.TvSetupRequest;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * Reuses the show editor view to edit newly created shows.
 *
 */
public class CreateShowPresenter extends AbstractActivity
		implements
			ShowEditorView.Presenter {
	@Inject
	TvGuideRequestFactory rf;
	@Inject
	ShowEditorView view;
	@Inject
	PlaceController placeController;
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setIsSaved(false);
		view.setPresenter(this);

		TvSetupRequest req = rf.makeSetupRequest();
		// make the new object
		TvShowProxy newInstance = req.create(TvShowProxy.class);
		newInstance.setEpisodes(new ArrayList<TvEpisodeProxy>());

		//set up a request to save it, and pass control to the editor
		req.saveShow(newInstance).to(new Receiver<TvShowProxy>() {
			@Override
			public void onSuccess(final TvShowProxy saved) {
				//TODO remove this
				// for now, also adding this to the user's favorites
				rf.makeGuideRequest().getFavoriteShows().fire(
						new Receiver<List<TvShowProxy>>() {
							@Override
							public void onSuccess(List<TvShowProxy> favs) {
								favs.add(saved);
								rf.makeGuideRequest().setFavoriteShows(favs)
										.fire();
							}
						});

				// show the editor
				placeController.goTo(new ShowEditorPlace(saved.stableId()));
			}
		});

		//pass it to the driver and show the view
		view.getEditor().edit(newInstance, req);
		panel.setWidget(view);
	}

	@Override
	public void back() {
		History.back();
	}

	@Override
	public void save() {
		// when saving, complete the save, and pass control to the editor (see start method)
		RequestContext req = view.getEditor().flush();
		if (!view.getEditor().hasErrors()) {
			req.fire();
		}
	}

}
