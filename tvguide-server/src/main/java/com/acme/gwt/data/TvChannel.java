package com.acme.gwt.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.acme.gwt.data.TvGuideCallFactory.FavoritesGetCall;
import com.acme.gwt.shared.defs.Geo;

/**
 * this is a local channel in a lineup of channels, this is not a network in a list of Cable networks.
 * <p/>
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public @Entity
class TvChannel implements HasVersionAndId {

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

	private String name;
	private String icon;
	private Integer channelNumber;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(Integer channelNumber) {
		this.channelNumber = channelNumber;
	}

	private Geo geo;

	@Enumerated(EnumType.STRING)
	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	private List<TvScheduledEpisode> scheduledEpisodes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tvChannel")
	public List<TvScheduledEpisode> getScheduledEpisodes() {
		return scheduledEpisodes;
	}

	public void setScheduledEpisodes(List<TvScheduledEpisode> scheduledEpisodes) {
		this.scheduledEpisodes = scheduledEpisodes;
	}

	public static class FavoritesChannelCallable implements FavoritesGetCall {
		@Override
		public List<TvShow> call() throws Exception {
			return new ArrayList<TvShow>();
		}

	}
}
