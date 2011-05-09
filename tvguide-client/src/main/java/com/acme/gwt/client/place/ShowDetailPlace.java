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
package com.acme.gwt.client.place;

import com.acme.gwt.client.TvGuideRequestFactory;
import com.acme.gwt.shared.TvShowProxy;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;
import com.google.inject.Inject;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;

/**
 * @author colin
 *
 */
public class ShowDetailPlace extends Place {
	private final EntityProxyId<TvShowProxy> id;
	/**
	 * 
	 */
	public ShowDetailPlace(EntityProxyId<TvShowProxy> showId) {
		this.id = showId;
	}
	@Prefix("show-detail")
	public static class Tokenizer implements PlaceTokenizer<ShowDetailPlace> {
		@Inject
		TvGuideRequestFactory rf;
		@Override
		public ShowDetailPlace getPlace(String token) {
			return new ShowDetailPlace(rf.<TvShowProxy> getProxyId(token));
		}

		@Override
		public String getToken(ShowDetailPlace place) {
			return rf.getHistoryToken(place.getId());
		}

	}

	/**
	 * @return the id
	 */
	public EntityProxyId<TvShowProxy> getId() {
		return id;
	}
}
