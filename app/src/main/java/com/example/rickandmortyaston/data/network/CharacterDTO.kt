package com.example.characters.data.network


import com.example.rickandmortyaston.data.characters.CharacterDBEntity
import com.squareup.moshi.Json

data class CharacterDto constructor(
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "status")
    val status: String,
    @Json(name = "species")
    val species: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "created")
    val created: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "image")
    val image: String,

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
            type = it.type
        )
    }
}