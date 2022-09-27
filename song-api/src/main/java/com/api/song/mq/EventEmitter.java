package com.api.song.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.song.model.Message;
import com.api.song.model.SongArtist;
import com.api.song.model.SongGenre;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EventEmitter {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	private String createMessage(String Obj, String routingKey) throws JsonProcessingException {
		Message msg = new Message();
		msg.setROUTING_KEY(routingKey);
		msg.setOBJECT(Obj);
		return objectMapper.writeValueAsString(msg);
	}
	
	public void publishMsgEvent(String exchangeType, String routingKey, String msg) {
		try {
			rabbitTemplate.convertAndSend(exchangeType, routingKey, msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("unable to public MsgEvent");
			e.printStackTrace();
		}
	}
	
	public void publishUpdateSongArtistRelEvent(String exchangeType, SongArtist msg) throws JsonProcessingException {
		String obj = objectMapper.writeValueAsString(msg);
		String routingkey = "song.artist.update";
		
		publishMsgEvent(exchangeType, routingkey, createMessage(obj, routingkey));
	}
	
	public void publishUpdateSongGenreRelEvent(String exchangeType, SongGenre msg) throws JsonProcessingException {
		String obj = objectMapper.writeValueAsString(msg);
		String routingkey = "song.genre.update";
		publishMsgEvent(exchangeType, routingkey, createMessage(obj, routingkey));
	}
	
	public void publishDeleteSongArtistRelEvent(String exchangeType,  SongArtist msg) throws JsonProcessingException {
		String obj = objectMapper.writeValueAsString(msg);
		String routingkey = "song.artist.delete";
		publishMsgEvent(exchangeType, routingkey, createMessage(obj, routingkey));
	}
	
	public void publishDeleteSongGenreRelEvent(String exchangeType, SongGenre msg) throws JsonProcessingException {
		String obj = objectMapper.writeValueAsString(msg);
		String routingkey = "song.genre.delete";
		publishMsgEvent(exchangeType, routingkey, createMessage(obj, routingkey));
	}
	
}
