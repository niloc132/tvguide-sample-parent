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

import com.acme.gwt.data.Channel;
import com.acme.gwt.data.ScheduledEpisode;
import com.acme.gwt.data.Show;
import com.acme.gwt.data.ViewerProfile;
import com.google.inject.Inject;

/**
 * Represents basic operations that can be performed by external users of this server.
 * Authentication and authorization should occur at this level, with data provided before the call
 * gets here.
 *
 * @author colin
 */
public class TvGuideService {
  List<ScheduledEpisode> getEpisodesInRange(Show show
      , Date begin, Date end) {
    return null;  //todo: review for a fit
  }

  List<ScheduledEpisode> getEpisodesInRange(Channel channel, Date begin, Date end) {
    return null;  //todo: review for a fit
  }

  void setFavoriteShows(List<Show> favoriteShows) {
    try {
      ViewerProfile call = new ViewerProfileCallable().call();
      call.setFavoriteShows(favoriteShows);
    } catch (Exception e) {
      e.printStackTrace();  //todo: verify for a fit
    }
  }

  static class ViewerProfileCallable implements Callable<ViewerProfile> {
    @Inject
    ViewerProfile currentViewerFromSession;

    @Override
    public ViewerProfile call() throws Exception {

      return currentViewerFromSession;
    }
  }
}