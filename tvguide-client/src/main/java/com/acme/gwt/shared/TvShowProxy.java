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

import com.acme.gwt.data.TvShow;
import com.acme.gwt.server.InjectingLocator;
import com.acme.gwt.server.InjectingServiceLocator;
import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.ProxyFor;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

/**
 * First draft at representing an entire show. Does not drag the full list of episodes and seasons
 * along with it, as this could be quite extensive, and does not indicate channels or show times, as
 * these could vary.
 *
 * @author colin
 */

@ProxyFor(value = TvShow.class, locator = InjectingLocator.class)
public interface TvShowProxy extends EntityProxy {
	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String desc);

	@Service(value = TvShow.class, locator = InjectingServiceLocator.class)
	public interface TvShowRequest extends RequestContext {
		//un-implemented on the server, commenting out to stop test failures until it exists
		//InstanceRequest<TvShowProxy, List<ScheduledEpisodeProxy>> findEpisodesInRange(Date startDate, Date endDate);

	}
}

