package com.acme.gwt.server;

import java.util.Date;
import java.util.List;

import com.acme.gwt.data.TvChannel;
import com.acme.gwt.data.TvGuideCallFactory;
import com.acme.gwt.data.TvScheduledEpisode;
import com.acme.gwt.data.TvShow;
import com.google.inject.Inject;

public class TvGuideService {
	@Inject TvGuideCallFactory factory;
	public List<TvShow> getFavoriteShows() throws Exception {
		return factory.getFavoriteShows().call();
	}
	public List<TvShow> setFavoriteShows(List<TvShow> favoriteShows) throws Exception {
		return factory.setFavoriteShows(favoriteShows).call();
	}
	public List<TvChannel> getAllChannels() throws Exception {
		return factory.getAllChannels().call();
	}
	
	public List<TvScheduledEpisode> findEpisodesByChannelAndDateBetween(TvChannel channel, Date startDate, Date endDate) throws Exception {
		return factory.findEpisodesByChannelAndDateBetween(channel, startDate, endDate).call();
	}
	
	public List<TvScheduledEpisode> findEpisodesByShowAndDateBetween(TvShow tvShow, Date startDate, Date endDate) throws Exception {
		return factory.findEpisodesByShowAndDateBetween(tvShow, startDate, endDate).call();
	}
}
