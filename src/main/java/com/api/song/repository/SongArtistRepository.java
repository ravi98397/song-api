package com.api.song.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.api.song.model.Song;
import com.api.song.model.SongArtist;
import com.api.song.model.SongArtistPK;

@Component
public interface SongArtistRepository extends JpaRepository<SongArtist, SongArtistPK>{
	List<SongArtist> findByArtistId(String artistId);
	List<SongArtist> findBySongId(String songId);
}
