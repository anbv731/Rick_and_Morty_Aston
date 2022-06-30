package com.example.rickandmortyaston.data.characters

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
fun CharacterDto.asModelOne(): CharacterDBEntity {
    return   CharacterDBEntity(
        name = this.name,
        id = this.id,
        status = this.status,
        species = this.species,
        created = this.created,
        image = this.image,
        gender = this.gender,
        type = this.type
    )

}