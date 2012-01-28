package com.acme.gwt.server;

import java.util.Date;
import java.util.List;

import com.acme.gwt.data.TvChannel;
import com.acme.gwt.data.TvScheduledEpisode;
import com.acme.gwt.data.TvShow;

public class TvGuideService {
	public List<TvShow> getFavoriteShows() {
		return null;
	}
	public void setFavoriteShows(List<TvShow> favoriteShows) {
		
	}
	public List<TvChannel> getAllChannels() {
		return null;
	}
	
	public List<TvScheduledEpisode> findEpisodesByChannelAndDateBetween(TvChannel channel, Date startDate, Date endDate) {
		return null;
	}
	
	public List<TvScheduledEpisode> findEpisodesByShowAndDateBetween(TvShow tvShow, Date startDate, Date endDate) {
		return null;
	}
}
