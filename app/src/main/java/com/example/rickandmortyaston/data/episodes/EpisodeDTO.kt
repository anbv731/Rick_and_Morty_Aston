package com.example.rickandmortyaston.data.episodes

import com.squareup.moshi.Json

data class EpisodeDto constructor(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "air_date")
    val airdate: String?,
    @field:Json(name = "episode")
    val episode: String,
    @field:Json(name = "characters")
    val characters: List<String>

)

fun List<EpisodeDto>.asModel(): List<EpisodeDBEntity> {

    return map {
        EpisodeDBEntity(
            name = it.name,
            id = it.id,
            airDate = it.airdate ?: "",
            episode = it.episode,
            characters = it.characters.map {
                it.replace(
                    "https://rickandmortyapi.com/api/character/",
                    ""
                )
            }.toList()
        )
    }
}

fun EpisodeDto.asModelOne(): EpisodeDBEntity {
    return EpisodeDBEntity(
        name = this.name,
        id = this.id,
        airDate = this.airdate ?: "",
        episode = this.episode,
        characters = this.characters.map {
            it.replace(
                "https://rickandmortyapi.com/api/character/",
                ""
            )
        }.toList()
    )
}

