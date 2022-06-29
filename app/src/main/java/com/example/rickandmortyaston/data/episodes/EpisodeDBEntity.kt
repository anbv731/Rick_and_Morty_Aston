package com.example.rickandmortyaston.data.episodes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain

@Entity(tableName = "episodes", indices = [Index(value = ["id"], unique = true)])
data class EpisodeDBEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    @ColumnInfo(name = "episode")
    val episode: String,
    @ColumnInfo(name = "characters")
    val characters: String
)

fun List<EpisodeDBEntity>.asListDomainModel(): List<EpisodeDomain> {
    return map {
        EpisodeDomain(
            name = it.name,
            id = it.id,
            airDate = it.airDate,
            episode = it.episode,
            characters = it.characters
        )
    }
}

fun EpisodeDBEntity.asDomainModel(): EpisodeDomain {
    return EpisodeDomain(
        name = this.name,
        id = this.id,
        airDate = this.airDate,
        episode = this.episode,
        characters = this.characters
    )
}