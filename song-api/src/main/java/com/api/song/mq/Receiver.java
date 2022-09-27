package com.api.song.mq;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.song.model.SongArtist;
import com.api.song.model.SongGenre;
import com.api.song.mq.model.ArtistSongRel;
import com.api.song.service.impl.ActionMapperService;
import com.api.song.service.impl.SongArtistServiceImpl;
import com.api.song.service.impl.SongGenreServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
//@RabbitListener(queues="SONG_QUEUE")
public class Receiver {
	
	@Autowired
	private ActionMapperService msgProcessor;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	//@RabbitHandler
	public void receiveMessage(String message) {
	  System.out.println("Received in song <" + message + ">");
	  msgProcessor.processMessage(message);
	  latch.countDown();
	}
	

	public CountDownLatch getLatch() {
	  return latch;
	}
}
