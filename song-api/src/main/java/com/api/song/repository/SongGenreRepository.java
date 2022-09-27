package com.api.song.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.api.song.model.SongGenre;
import com.api.song.model.SongGenrePK;

@Component
public interface SongGenreRepository extends JpaRepository<SongGenre, SongGenrePK>{
	List<SongGenre> findByGenreId(String genreId);
	List<SongGenre> findBySongId(String songId);
}
