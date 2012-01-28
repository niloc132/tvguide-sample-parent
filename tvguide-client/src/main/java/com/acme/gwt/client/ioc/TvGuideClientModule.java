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
package com.acme.gwt.client.ioc;

import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.client.bootstrap.LoginWidget;
import com.acme.gwt.client.place.DefaultPlace;
import com.acme.gwt.client.place.TvGuidePlaceHistoryMapper;
import com.acme.gwt.client.place.WelcomePlace;
import com.acme.gwt.client.presenter.TvGuideActivityMapper;
import com.acme.gwt.client.presenter.TvGuideActivityMapper.ActivityFactory;
import com.acme.gwt.client.view.FavoriteShowsListView;
import com.acme.gwt.client.view.LoginView;
import com.acme.gwt.client.view.ShowDetailView;
import com.acme.gwt.client.view.WelcomeView;
import com.acme.gwt.client.widget.FavoriteShowsListWidget;
import com.acme.gwt.client.widget.ShowDetailWidget;
import com.acme.gwt.client.widget.WelcomeWidget;
import com.acme.gwt.shared.TvViewerProxy;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

/**
 * Compile-time dependency injection for the TvGuide app. Note that the configure() method need
 * not be translatable, as it is run by the generators, and the compiled code is based then off
 * of what Gin learns when it is run.
 * 
 * @author colin
 */
public class TvGuideClientModule extends AbstractGinModule {
	@Override
	protected void configure() {
		// Events
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

		// Access to the current user
		bind(TvViewerProxy.class).toProvider(TvViewerProvider.class);

		// A/P, history mapping
		bind(ActivityMapper.class).to(TvGuideActivityMapper.class);
		bind(PlaceHistoryMapper.class).to(TvGuidePlaceHistoryMapper.class);

		// Place to Activity assisted injection
		install(new GinFactoryModuleBuilder().build(ActivityFactory.class));

		// Default place to let the app start without history
		bind(Place.class).annotatedWith(DefaultPlace.class).to(
				WelcomePlace.class);

		// View interfaces to their singleton Widgets
		// the Widgets themselves are set as singletons
		bind(WelcomeView.class).to(WelcomeWidget.class);
		bind(ShowDetailView.class).to(ShowDetailWidget.class);
		bind(FavoriteShowsListView.class).to(FavoriteShowsListWidget.class);

		bind(LoginView.class).to(LoginWidget.class);//not singleton, since it should only be loaded once
	}

	@Singleton
	@Provides
	TvGuideRequestFactory provideRequestFactory(EventBus eventBus) {
		TvGuideRequestFactory rf = GWT.create(TvGuideRequestFactory.class);
		rf.initialize(eventBus);
		return rf;
	}

	@Singleton
	@Provides
	PlaceController providePlaceController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}

	@Singleton
	@Provides
	ActivityManager provideActivityManager(ActivityMapper mapper,
			EventBus eventBus) {
		return new ActivityManager(mapper, eventBus);
	}

	@Singleton
	@Provides
	PlaceHistoryHandler providePlaceHistoryHandler(PlaceHistoryMapper mapper,
			PlaceController placeController, EventBus eventBus, @DefaultPlace
			Place defaultPlace) {
		PlaceHistoryHandler phh = new PlaceHistoryHandler(mapper);
		phh.register(placeController, eventBus, defaultPlace);
		return phh;
	}

}
