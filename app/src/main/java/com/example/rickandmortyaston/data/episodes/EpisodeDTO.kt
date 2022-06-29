package com.example.rickandmortyaston.data.episodes

import com.squareup.moshi.Json

data class EpisodeDto constructor(
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "air_date")
    val airDate:String,
    @Json(name = "episode")
    val episode: String,
    @Json(name = "characters")
    val characters:String

    )

fun List<EpisodeDto>.asModel(): List<EpisodeDBEntity> {
    return map {
        EpisodeDBEntity(
            name = it.name,
            id = it.id,
            airDate = it.airDate,
            episode = it.episode,
            characters = it.characters
        )
    }
}