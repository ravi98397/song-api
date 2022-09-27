package com.api.song.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@IdClass(SongArtistPK.class)
public class SongArtist {
	
	@Id
	private String songId;
	
	@Id
	private String artistId;
	
}
