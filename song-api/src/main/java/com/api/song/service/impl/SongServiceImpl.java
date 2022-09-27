package com.api.song.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.song.model.Song;
import com.api.song.repository.SongRepository;
import com.api.song.service.SongService;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongRepository repo;
	
	@Autowired
	private SongArtistServiceImpl songArtistServiceImpl;
	
	@Override
	public List<Song> listall() {
		return repo.findAll();
	}

	@Override
	public Song getById(String id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public void deleteSong(String id) {
		repo.deleteById(id);
	}

	@Override
	public Song newSong(Song Song) {
		if(repo.findById(Song.getId()).orElse(null) == null) {
			return repo.save(Song);
		} else {
			return null;
		}
	}

	@Override
	public Song updateSong(Song Song) {
		if(repo.findById(Song.getId()).orElse(null) != null) {
			return repo.save(Song);
		}else {
			return null;
		}
	}

	@Override
	public List<Song> getByList(List<String> ids) {
		if(ids.size() > 0) {
			return repo.findAllById(ids);
		}
		return null;
	}

	@Override
	public List<Song> getByArtistId(String id) {
		List<String> ids = songArtistServiceImpl.getSongIdFormArtistId(id);
		return getByList(ids);
	}

}
