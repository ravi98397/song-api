package com.api.song.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SongGenrePK.class)
public class SongGenre {
	
		@Id
		private String genreId;
		
		@Id
		private String songId;
		
		public SongGenrePK getCompositeKey() {
			SongGenrePK pk = new SongGenrePK();
			pk.setGenreId(this.genreId);
			pk.setSongId(this.songId);
			return pk;
		}
		
}
