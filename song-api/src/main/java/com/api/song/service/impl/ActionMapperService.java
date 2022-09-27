package com.api.song.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.song.exception.InvalidRoutingKey;
import com.api.song.mapper.MessageMapper;
import com.api.song.model.Message;
import com.api.song.model.SongArtist;
import com.api.song.model.SongArtistPK;
import com.api.song.model.SongGenre;
import com.api.song.service.impl.SongArtistServiceImpl;
import com.api.song.service.impl.SongGenreServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class ActionMapperService {
	
	@Autowired
	private MessageMapper mapper;
	
	@Autowired
	private SongArtistServiceImpl saService;
	
	@Autowired
	private SongGenreServiceImpl sgService;
	
	public void processMessage(String message) {
			
		try {
			Message msg = mapper.getMassageObj(message);
			System.out.println(msg.toString());
			identifyMessage(msg);
			
		} catch (JsonProcessingException | InvalidRoutingKey e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid Message format");
			e.printStackTrace();
		}
		
	}

	private void identifyMessage(Message msg) throws InvalidRoutingKey, JsonMappingException, JsonProcessingException {
		String keys[] = msg.getROUTING_KEY().split("[.]");
		System.out.println(msg.getROUTING_KEY());
		String source;
		String action;
		
		if(keys.length < 3) {
			System.out.println("keylength = " + keys.length);
			throw new InvalidRoutingKey("Routing key foramt is invalid, it should follow from.to.action : " + msg.getROUTING_KEY());
		}else {
			source = keys[0];
			action = keys[2];
		}
		
		switch (source) {
		case "artist":
			SongArtist songArtist = mapper.getSongArtistObj(msg.getOBJECT());
			takeAction(songArtist, action);
			break;
		case "genre":
			SongGenre songGenre = mapper.getSongGenreObj(msg.getOBJECT());
			takeAction(songGenre, action);
			break;
		default:
			System.out.println("unknown source: no action taken");
			break;
		}
		
	}

	private void takeAction(SongArtist songArtist, String action) {
		
		switch (action) {
		case "update":
			saService.updateSongArtistRel(songArtist);
			break;
		case "delete":
			SongArtistPK pk = new SongArtistPK();
			pk.setArtistId(songArtist.getArtistId());
			pk.setSongId(songArtist.getSongId());
			saService.deleteSongArtistRel(pk);
			break;
		default:
			System.out.println("unknown action: no action taken");
			break;
		}
		
	}
	
	private void takeAction(SongGenre songGenre, String action) {
		
		switch (action) {
		case "update":
			sgService.updateSongGenreRel(songGenre);
			break;
		case "delete":
			sgService.deleteSongGenreRel(songGenre.getCompositeKey());
			break;
		default:
			System.out.println("unknown action: no action taken");
			break;
		}
		
	}
	
}
