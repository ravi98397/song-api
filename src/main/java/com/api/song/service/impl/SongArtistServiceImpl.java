package com.api.song.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.song.model.SongArtist;
import com.api.song.model.SongArtistPK;
import com.api.song.mq.EventEmitter;
import com.api.song.repository.SongArtistRepository;
import com.api.song.service.SongArtistService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SongArtistServiceImpl implements SongArtistService{
	
	@Autowired
	private SongArtistRepository relrepo;
	
	@Autowired
	private SongServiceImpl songService;
	
	@Autowired
	private EventEmitter eventEmitter;
	
	
	@Override
	public List<SongArtist> listallSongArtistRel() {
		return relrepo.findAll();
	}

	@Override
	public List<String> getSongIdFormArtistId(String ArtistId) {
		List<String> songId = new ArrayList<String>();
		
		for(SongArtist obj: relrepo.findByArtistId(ArtistId)) {
			songId.add(obj.getSongId());
		}
		return songId;
	}

	@Override
	public List<String> getArtistIdFromSongId(String SongId) {
		List<String> artistId = new ArrayList<String>();
		
		for(SongArtist obj: relrepo.findBySongId(SongId)) {
			artistId.add(obj.getArtistId());
		}
		return artistId;
	}

	@Override
	public void deleteSongArtistRel(SongArtistPK id) {
		SongArtist songartist = relrepo.findById(id).orElse(null);
		if(songartist != null) {
			relrepo.delete(songartist);
			try {
				System.out.println("sending SongArtist delete rel");
				eventEmitter.publishDeleteSongArtistRelEvent("REL_EXCHANGE", songartist);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public SongArtist newSongArtistRel(SongArtist obj) {
		SongArtistPK pk = new SongArtistPK();
		pk.setArtistId(obj.getArtistId());
		pk.setSongId(obj.getSongId());
		
		SongArtist songartist = relrepo.findById(pk).orElse(null);
		
		if(songartist == null) {
			try {
				System.out.println("sending SongArtist udpate rel");
				eventEmitter.publishUpdateSongArtistRelEvent("REL_EXCHANGE", obj);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return relrepo.save(obj);
		}
		return null;
	}

	@Override
	public void updateSongArtistRel(SongArtist obj) {
		System.out.println("update event received from artist");
		SongArtistPK pk = new SongArtistPK();
		pk.setArtistId(obj.getArtistId());
		pk.setSongId(obj.getSongId());
		
		if(relrepo.findById(pk).orElse(null) == null && songService.getById(obj.getSongId()) != null) {
			relrepo.save(obj);
			System.out.println("Event Processed - relationship updated");
		}
		
	}
	
}
