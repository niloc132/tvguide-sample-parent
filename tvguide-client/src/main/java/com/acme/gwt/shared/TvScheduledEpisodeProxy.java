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

import com.acme.gwt.data.TvScheduledEpisode;
import com.acme.gwt.server.InjectingLocator;
import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

/**
 * Maps a TvChannel,TvAirTime tuple to a single episode, as only one episode can be shown at a given
 * time on a given channel.
 * <p/>
 * Okay, a given User,TvChannel, since the same TvChannel can have different times in different zones.
 * But I think that a TvChannel object is already local to the given user, so User is assumed.
 *
 * @author colin
 */
public
@ProxyFor(value = TvScheduledEpisode.class, locator = InjectingLocator.class)
interface TvScheduledEpisodeProxy extends EntityProxy {
  void setTvEpisode(TvEpisodeProxy show);

  TvEpisodeProxy getTvEpisode();

  TvChannelProxy getTvChannel();

  void setTvChannel(TvChannelProxy tvChannel);

  TvAirTimeProxy getBlock();

  void setBlock(TvAirTimeProxy block);

  public EntityProxyId<TvScheduledEpisodeProxy> stableId();
}
