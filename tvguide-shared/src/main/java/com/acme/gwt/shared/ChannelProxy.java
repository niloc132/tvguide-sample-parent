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

import com.acme.gwt.data.Channel;
import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.InstanceRequest;
import com.google.gwt.requestfactory.shared.ProxyFor;
import com.google.gwt.requestfactory.shared.Service;

/**
 * Proxy object for a tv channel, with options to provide a name, icon/symbol, and channel number.
 * Number is almost certainly based on particular user details, as channels can have different
 * mappings through different providers and in different areas.
 * <p/>
 * Channel objects do not themselves contain more data, at least on the client, as there could be
 * a very large amount of data that would need to either be included, or not. As such, showtimes and
 * shows are made available through different requests, and possibly should be cached on the client.
 *
 * @author colin
 */
public
@ProxyFor(Channel.class)
interface ChannelProxy extends EntityProxy {
  String getName();

  void setName(String name);

  String getIcon();

  void setIcon(String icon);

  int getChannelNumber();

  void setChannelNumber(int channelNum);


  public EntityProxyId<ChannelProxy> stableId();
}

@Service(Channel.class)
interface ChannelRequest {
  InstanceRequest<ChannelProxy, List<ScheduledEpisodeProxy>> findEpisodesInRange(Date startDate, Date endDate);

}