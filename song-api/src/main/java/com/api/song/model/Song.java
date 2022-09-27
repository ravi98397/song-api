package com.api.song.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Song {
	
	@Id
	private String id;
	private String name;
	private float duration;
	private int likes;
	
	@OneToMany(targetEntity = SongArtist.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "songId", referencedColumnName = "id")
	private Set<SongArtist> artists = new HashSet<SongArtist> ();
	
	@OneToMany(targetEntity = SongGenre.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "songId", referencedColumnName = "id")
	private Set<SongGenre> genres = new HashSet<SongGenre> ();
	
}
