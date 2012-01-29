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
package com.acme.gwt.server;

import javax.persistence.EntityManager;

import com.acme.gwt.data.AuthenticationCallFactory;
import com.acme.gwt.data.AuthenticationCallFactory.AuthenticationCall;
import com.acme.gwt.data.TvChannel.ChannelListCallable;
import com.acme.gwt.data.TvChannel.FavoritesChannelCallable;
import com.acme.gwt.data.TvEpisode.EpisodeRangeCallable;
import com.acme.gwt.data.TvGuideCallFactory;
import com.acme.gwt.data.TvGuideCallFactory.ChannelListCall;
import com.acme.gwt.data.TvGuideCallFactory.EpisodesDateCall;
import com.acme.gwt.data.TvGuideCallFactory.UserFavoritesCall;
import com.acme.gwt.data.TvSetupCallFactory;
import com.acme.gwt.data.TvSetupCallFactory.TvShowCall;
import com.acme.gwt.data.TvShow;
import com.acme.gwt.data.TvViewer;
import com.acme.gwt.data.TvViewer.AuthCallable;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @author colin
 */
public class TvGuideServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		// TvViewers will be handed out by this, depending on how auth is scoped
		bind(TvViewer.class).toProvider(AuthenticatedViewerProvider.class);

		// Make the CallFactories available for injection
		// Auth calls
		install(new FactoryModuleBuilder()
			.implement(AuthenticationCall.class, AuthCallable.class)
			.build(AuthenticationCallFactory.class));
		// TvGuide calls
		install(new FactoryModuleBuilder()
			.implement(EpisodesDateCall.class,EpisodeRangeCallable.class)
			.implement(UserFavoritesCall.class, FavoritesChannelCallable.class)
			.implement(ChannelListCall.class, ChannelListCallable.class)
			.build(TvGuideCallFactory.class));

		install(new FactoryModuleBuilder().implement(TvShowCall.class,
				TvShow.SaveCallable.class).build(TvSetupCallFactory.class));

		//bind(EpisodesDateCall.class).to(EpisodeRangeCallable.class);
		// Ensure that something has session stuff ready
		requireBinding(AuthenticatedViewerProvider.class);

		// Ensure that something is bringing a EntityManager to the party
		requireBinding(EntityManager.class);

		// TvViewer must be statically injected, otherwise it has no access to the great 
		// Guicy world of data
		requestStaticInjection(TvViewer.class);
	}
}
