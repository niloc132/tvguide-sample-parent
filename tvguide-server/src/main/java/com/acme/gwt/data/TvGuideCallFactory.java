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
package com.acme.gwt.data;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.google.inject.assistedinject.Assisted;

/**
 * @author colin
 *
 */
public interface TvGuideCallFactory {
	FavoritesGetCall getFavoriteShows();
	//FavoritesSetCall setFavoriteShows(List<TvShow> shows);
	//...

	EpisodesDateCall findEpisodesByChannelAndDateBetween(TvChannel tvChan,
			@Assisted("startDate")
			Date start, @Assisted("endDate")
			Date end);
	EpisodesDateCall findEpisodesByShowAndDateBetween(TvShow show,
			@Assisted("startDate")
			Date start, @Assisted("endDate")
			Date end);

	public interface FavoritesGetCall extends Callable<List<TvShow>> {
	}
	public interface FavoritesSetCall extends Callable<Void> {
	}

	//...

	public interface EpisodesDateCall
			extends
				Callable<List<TvScheduledEpisode>> {
	}
}
