package com.acme.gwt.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

import com.acme.gwt.data.TvGuideCallFactory.EpisodesDateCall;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.istack.internal.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public @Entity
class TvEpisode implements HasVersionAndId {

	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	private Integer version;

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	private TvShow tvShow;
	@NotNull
	private Integer season;
	@NotNull
	private Integer episodeNumber;
	private String name;

	@ManyToOne(cascade = CascadeType.ALL)
	public TvShow getTvShow() {
		return tvShow;
	}

	public void setTvShow(TvShow tvShow) {
		this.tvShow = tvShow;
	}

	public Integer getSeason() {
		return season;
	}

	public void setSeason(Integer season) {
		this.season = season;
	}

	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<TvScheduledEpisode> scheduledEpisodes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tvEpisode", orphanRemoval = true)
	@OrderBy(value = "block.startDate")
	public List<TvScheduledEpisode> getScheduledEpisodes() {
		return scheduledEpisodes;
	}

	public void setScheduledEpisodes(List<TvScheduledEpisode> scheduledEpisodes) {
		this.scheduledEpisodes = scheduledEpisodes;
	}

	public static class EpisodeRangeCallable implements EpisodesDateCall {
		@Assisted("startDate")
		Date startDate;
		@Assisted("endDate")
		Date endDate;
		@Assisted
		TvChannel channel;
		@Assisted
		TvShow show;

		//very locally scoped, no need for provider
		@Inject
		EntityManager em;
		@Override
		public List<TvScheduledEpisode> call() throws Exception {
			return new ArrayList<TvScheduledEpisode>();
		}
	}
}
