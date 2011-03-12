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

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.persistence.EntityManager;

import com.acme.gwt.data.TvChannel;
import com.acme.gwt.data.TvScheduledEpisode;
import com.acme.gwt.data.TvShow;
import com.acme.gwt.data.TvViewer;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Represents basic operations that can be performed by external users of this server.
 * Authentication and authorization should occur at this level, with data provided before the call
 * gets here.
 *
 * @author colin
 */
public class TvGuideService {
	@Inject Provider<EntityManager> emProvider;

	@Inject Provider<TvViewer> viewerProvider;


	public List<TvScheduledEpisode> findEpisodesByShowAndDateBetween(TvShow tvShow, Date begin, Date end) {
		return null;  //todo: call the appropriate finder
	}

	public List<TvScheduledEpisode> findEpisodesByChannelAndDateBetween(TvChannel tvChannel, Date begin, Date end) {
		return null;  //todo: call the appropriate finder
	}

	public List<TvShow> getFavoriteShows() {
		TvViewer call = null;
		try {
			call = viewerProvider.get();
		} catch (Exception e) {
			e.printStackTrace();  //todo: verify for a fit
		}
		return call.getFavorites();
	}

	public void setFavoriteShows(List<TvShow> favoriteTvShows) {
		try {
			TvViewer call = viewerProvider.get();
			call.setFavorites(favoriteTvShows);
		} catch (Exception e) {
			e.printStackTrace();  //todo: verify for a fit
		}
	}

	public List<TvChannel> getAllChannels() {
		try {
			return (List<TvChannel>) new Callable() {
				public Object call() throws Exception {
					try {
						//todo: make current user's geo matter to this list
						return emProvider.get().createQuery("select Channel from TvChannel Channel", TvChannel.class).getResultList();
					} catch (Exception e) {
						e.printStackTrace();  //todo: verify for a fit
					}
					return null;
				}
			}.call();
		} catch (Exception e) {
			e.printStackTrace();  //todo: verify for a fit
		}
		return null;
	}
}
