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
package com.acme.gwt.shared;

import java.util.Date;
import java.util.List;

import com.acme.gwt.server.InjectingServiceLocator;
import com.acme.gwt.server.TvGuideService;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;

/**
 * Basic RequestContext for getting show information for a given user. At least for now, this is all
 * grouped into one request so that requests may be batched where this is useful/important.
 *
 * @author colin
 */
@Service(value = TvGuideService.class, locator = InjectingServiceLocator.class)
public interface TvGuideRequest extends RequestContext {

	/**
	 * Gets the list of shows the user has marked as favorite.
	 *
	 * @return
	 */
	Request<List<TvShowProxy>> getFavoriteShows();

	/**
	 * Informs the server that the list of favorites has changed.
	 *
	 * @param tvShows
	 * @return
	 * @TODO This should probably go away, and in its place, methods for add and remove favorites.
	 */
	Request<List<TvShowProxy>> setFavoriteShows(List<TvShowProxy> tvShows);

	/**
	 * Gets all the User's channels (based on Locale,Provider, yet to be determined how we look this
	 * up).
	 *
	 * @return
	 */
	Request<List<TvChannelProxy>> getAllChannels();

	/**
	 * Gets all of the shows schedules between the start and end date on the given tvChannel. For the
	 * general (i.e. non-favorites) case, this is the main method to actually do things.
	 * <p/>
	 * It is possible we want the tvChannel param to change to a list, but since this is a single
	 * RequestContext, more than one call to this can be batched, and the client can handle each
	 * as desired.
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Request<List<TvScheduledEpisodeProxy>> findEpisodesByChannelAndDateBetween(
			TvChannelProxy tvChannel, Date startDate, Date endDate);

	/**
	 * Gets all of the scheduled instances of this tvShow in the given range. For users making use of
	 * the favorites functionality, this allows them to select only shows they care about.
	 * <p/>
	 * As with the other overload of getEpisodesInRange, this can be called many times on a given
	 * request to get the data for many shows. This will probably be intended to be called over
	 * longer periods of time, and over all channels available to this user.
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Request<List<TvScheduledEpisodeProxy>> findEpisodesByShowAndDateBetween(
			TvShowProxy tvShow, Date startDate, Date endDate);
}
