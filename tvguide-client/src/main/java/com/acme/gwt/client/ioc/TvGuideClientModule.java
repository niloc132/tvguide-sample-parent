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
import com.acme.gwt.client.place.DefaultPlace;
import com.acme.gwt.client.place.TvGuidePlaceHistoryMapper;
import com.acme.gwt.client.place.WelcomePlace;
import com.acme.gwt.client.presenter.TvGuideActivityMapper;
import com.acme.gwt.client.view.WelcomeView;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author colin
 *
 */
public class TvGuideClientModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

		bind(TvGuideRequestFactory.class).in(Singleton.class);

		bind(ActivityMapper.class).to(TvGuideActivityMapper.class);
		bind(PlaceHistoryMapper.class).to(TvGuidePlaceHistoryMapper.class);

		bind(Place.class).annotatedWith(DefaultPlace.class).to(WelcomePlace.class);

		bind(WelcomeView.class).in(Singleton.class);

	}

	@Provides PlaceController providePlaceController(EventBus eventBus) {
		return new PlaceController(eventBus);
	}

	@Provides ActivityManager provideActivityManager(ActivityMapper mapper, EventBus eventBus) {
		return new ActivityManager(mapper, eventBus);
	}

	@Provides PlaceHistoryHandler providePlaceHistoryHandler(PlaceHistoryMapper mapper, 
			PlaceController placeController, EventBus eventBus, @DefaultPlace Place defaultPlace) {
		PlaceHistoryHandler phh = new PlaceHistoryHandler(mapper);
		phh.register(placeController, eventBus, defaultPlace);
		return phh;
	}

}
