package com.api.song.service;

import java.util.List;
import java.util.Set;

import com.api.song.model.Song;

public interface SongService {

	public List<Song> listall();
	
	public List<Song> getByList(List<String> ids);
	
	public List<Song> getByArtistId(String id);

	public Song getById(String id);
	
	public void deleteSong(String id);
	
	public Song newSong(Song Song);

	public Song updateSong(Song Song);

}
