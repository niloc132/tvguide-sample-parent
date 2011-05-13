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
import com.acme.gwt.client.place.ShowEditorPlace;
import com.acme.gwt.client.view.ShowEditorView;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.requestfactory.shared.Receiver;

/**
 * @author colin
 *
 */
public class ShowEditorPresenter extends AbstractActivity
		implements
			ShowEditorView.Presenter {
	@Inject
	ShowEditorView view;

	@Inject
	TvGuideRequestFactory rf;
	@Inject
	PlaceController placeController;

	private final ShowEditorPlace place;

	private final Receiver<TvShowProxy> receiver = new Receiver<TvShowProxy>() {
		@Override
		public void onSuccess(TvShowProxy show) {
			view.setIsSaved(true);
			view.getEditor().edit(
					show,
					rf.makeSetupRequest().saveShow(show).with(
							view.getEditor().getPaths()).to(this));
			view.asWidget().setVisible(true);//ugh
			dirtyCheck.scheduleRepeating(5000);
		}
	};

	private final Timer dirtyCheck = new Timer() {
		@Override
		public void run() {
			if (view.getEditor().isDirty()) {
				view.setIsSaved(false);
				this.cancel();
			}
		}
	};
	@Inject
	public ShowEditorPresenter(@Assisted
	ShowEditorPlace place) {
		this.place = place;
	}

	@Override
	public void start(final AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		rf.find(place.getId()).with(view.getEditor().getPaths()).to(receiver)
				.fire(new Receiver<Void>() {
					@Override
					public void onSuccess(Void arg0) {
						panel.setWidget(view);
					}
				});
	}

	@Override
	public String mayStop() {
		if (view.getEditor().isDirty()) {
			return "Changes have not been saved - are you sure you want to continue?";
		}
		return null;
	}

	@Override
	public void back() {
		History.back();
	}

	@Override
	public void save() {
		view.asWidget().setVisible(false);//ugh
		view.getEditor().flush().fire();
	}

}
