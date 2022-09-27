package com.api.song.service;

import java.util.List;

import com.api.song.model.SongArtist;
import com.api.song.model.SongArtistPK;

public interface SongArtistService {
	public List<SongArtist> listallSongArtistRel();

	public List<String> getSongIdFormArtistId(String ArtistId);
	
	public List<String> getArtistIdFromSongId(String SongId);
	
	public void deleteSongArtistRel(SongArtistPK id);
	
	public SongArtist newSongArtistRel(SongArtist artist);
	
	//this is process update events received
	public void updateSongArtistRel(SongArtist obj);
}
