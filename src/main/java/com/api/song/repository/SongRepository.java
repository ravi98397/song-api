package com.api.song.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.api.song.model.Song;

@Component
public interface SongRepository extends JpaRepository<Song, String>{

}
