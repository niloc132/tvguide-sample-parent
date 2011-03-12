package com.acme.gwt.data;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;

/**
 * Created by IntelliJ IDEA.
 * User: jim
 * Date: 3/10/11
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class TvShow implements HasVersionAndId {

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
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private List<TvEpisode> episodes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tvShow", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("episodeNumber")
	public List<TvEpisode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<TvEpisode> episodes) {
		this.episodes = episodes;
	}
}
