package com.example.rickandmortyaston.data.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EpisodesDao {

    @Query("select * from episodes WHERE id LIKE :id")
    fun getIdEpisode(id: Int): EpisodeDBEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<EpisodeDBEntity>)

    @Query("select * from episodes WHERE name LIKE '%' || :name || '%' and episode LIKE '%' || :episode || '%'")
    fun searchEpisodes(
        name: String,
        episode: String,
    ): List<EpisodeDBEntity>

    @Query("delete from episodes")
    fun deleteEpisodes()
}