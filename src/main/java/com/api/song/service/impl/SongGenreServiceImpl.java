package com.api.song.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.song.model.SongGenre;
import com.api.song.model.SongGenrePK;
import com.api.song.mq.EventEmitter;
import com.api.song.repository.SongGenreRepository;
import com.api.song.service.SongGenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SongGenreServiceImpl implements SongGenreService{
	
	@Autowired
	private SongGenreRepository repo;
	
	@Autowired
	private SongServiceImpl songService;
	
	@Autowired
	private EventEmitter eventEmitter;
	
	@Autowired
	private ObjectMapper objectmapper;
	
	@Override
	public List<SongGenre> listallSongGenreRel() {
		return repo.findAll();
	}

	@Override
	public List<String> getSongIdFormGenreId(String genreId) {
		List<String> songId = new ArrayList<String>();
		
		for(SongGenre obj: repo.findByGenreId(genreId)) {
			songId.add(obj.getSongId());
		}
		return songId;
	}

	@Override
	public List<String> getGenreIdFromSongId(String songId) {
		List<String> genreId = new ArrayList<String>();
		
		for(SongGenre obj: repo.findByGenreId(songId)) {
			genreId.add(obj.getSongId());
		}
		return genreId;
	}

	@Override
	public void deleteSongGenreRel(SongGenrePK id) {
		SongGenre songGenre = repo.findById(id).orElse(null);
		if(songGenre != null) {
			repo.delete(songGenre);
			try {
				eventEmitter.publishDeleteSongGenreRelEvent("SONG_REL_EXCHANGE", songGenre);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public SongGenre newSongGenreRel(SongGenre obj) {
		SongGenrePK pk = new SongGenrePK();
		pk.setGenreId(obj.getGenreId());
		pk.setSongId(obj.getSongId());
		
		SongGenre songGenre = repo.findById(pk).orElse(null);
		if(songGenre == null) {
			
			try {
				eventEmitter.publishUpdateSongGenreRelEvent("SONG_REL_EXCHANGE", songGenre);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				return repo.save(songGenre);
		}
		return null;
	}

	@Override
	public void updateSongGenreRel(SongGenre obj) {
		SongGenrePK pk = new SongGenrePK();
		pk.setGenreId(obj.getGenreId());
		pk.setSongId(obj.getSongId());
		
		if(repo.findById(pk).orElse(null) == null && songService.getById(obj.getGenreId()) != null) {
			repo.save(obj);
			System.out.println("Event Processed");
		}
	}

}
