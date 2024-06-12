package com.api.song.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.api.song.model.Song;
import com.api.song.model.SongArtist;
import com.api.song.mq.EventEmitter;
import com.api.song.service.SongArtistService;
import com.api.song.service.SongService;

@RestController
public class Controller {
	
	@Autowired
	private SongService service;
	
	@Autowired
	private SongArtistService songArtistService;
	
	@Autowired
	EventEmitter eventEmitter;
	
	@GetMapping("/api/sayhello")
	public String sayHello() {
		eventEmitter.publishMsgEvent("SONG_REL_EXCHANGE", "song.song.song", "hello");
		eventEmitter.publishMsgEvent("SONG_REL_EXCHANGE", "artist.artist.artist", "hello");
		eventEmitter.publishMsgEvent("SONG_REL_EXCHANGE", "song.artist.log", "hello");
		return "hello";
	}
	
	
	
	@GetMapping("/api/song/getAll")
	public List<Song> getAllSong(){
		return service.listall();
	}
	
	@PostMapping("/api/song/getAllInIdList")
	public List<Song> getAllInIdList(@RequestBody List<String> ids){
		return service.getByList(ids);
	}
	
	@GetMapping("/api/song/getAllByArtistId")
	public List<Song> getAllByArtistId(@RequestHeader String id){
		return service.getByArtistId(id); 
	}
	
	@GetMapping("api/song/getById")
	public Song getSongById(@RequestHeader String id) {
		return service.getById(id);
	}
	
	@PostMapping("api/song/add")
	public Song addSong(@RequestBody Song song) {
		Song song2 = service.newSong(song);
		if(song2 == null) {
			//need to throw a exception 
			return null;
		}else {
			return song2;
		}
	}
	
	@PutMapping("api/song/udpate")
	public Song updateSong(@RequestBody Song song) {
		Song song2 = service.updateSong(song);
		if(song2 == null) {
			//need to throw a exception 
			return null;
		}else {
			return song2;
		}
	}
	
	@PostMapping("api/song/songArtistRel/add")
	public Song songArtistRel(@RequestBody SongArtist rel) {
		SongArtist songArtist = songArtistService.newSongArtistRel(rel);
		if(songArtist == null) {
			return null;
		}
		return service.getById(rel.getSongId());
	}
}
