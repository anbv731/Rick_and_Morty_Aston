package com.example.rickandmortyaston.data.characters

import com.squareup.moshi.Json

data class CharacterDto constructor(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "species")
    val species: String,
    @field:Json(name = "gender")
    val gender: String,
    @field:Json(name = "created")
    val created: String,
    @field:Json(name = "type")
    val type: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name="episode")
    val episode: List<String>
    )

fun List<CharacterDto>.asModel(): List<CharacterDBEntity> {
    return map {
        CharacterDBEntity(
            name = it.name,
            id = it.id,
            status = it.status,
            species = it.species,
            created = it.created,
            image = it.image,
            gender = it.gender,
            type = it.type,
            episode = it.episode.map {
                it.replace(
                    "https://rickandmortyapi.com/api/episode/",
                    ""
                )
            }.toList()
        )
    }
}
fun CharacterDto.asModelOne(): CharacterDBEntity {
    return   CharacterDBEntity(
        name = this.name,
        id = this.id,
        status = this.status,
        species = this.species,
        created = this.created,
        image = this.image,
        gender = this.gender,
        type = this.type,
        episode = this.episode.map {
            it.replace(
                "https://rickandmortyapi.com/api/episode/",
                ""
            )
        }.toList()
    )

}