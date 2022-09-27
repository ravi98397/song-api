package com.api.song.service;

import java.util.List;

import com.api.song.model.SongGenre;
import com.api.song.model.SongGenrePK;

public interface SongGenreService {
	public List<SongGenre> listallSongGenreRel();

	public List<String> getSongIdFormGenreId(String ArtistId);
	
	public List<String> getGenreIdFromSongId(String SongId);
	
	public void deleteSongGenreRel(SongGenrePK id);
	
	public SongGenre newSongGenreRel(SongGenre artist);
	
	//this is to process update events received from mq
	public void updateSongGenreRel(SongGenre obj);
}
