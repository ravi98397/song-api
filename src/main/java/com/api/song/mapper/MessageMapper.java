package com.api.song.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.song.model.Message;
import com.api.song.model.SongArtist;
import com.api.song.model.SongGenre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageMapper {
	
	@Autowired
	ObjectMapper mapper;
	
	public Message getMassageObj(String strObj) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(strObj, Message.class);
	}
	
	public SongArtist getSongArtistObj(String strObj) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(strObj, SongArtist.class);
	}
	
	public SongGenre getSongGenreObj(String strObj) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(strObj, SongGenre.class);
	}
}
