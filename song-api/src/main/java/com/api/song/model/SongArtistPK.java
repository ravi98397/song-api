package com.api.song.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SongArtistPK implements Serializable {
	private String songId;
	private String artistId;
}
