package com.example.rickandmortyaston.data.locations

import com.squareup.moshi.Json

data class LocationDto constructor(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "type")
    val type: String,
    @field:Json(name = "dimension")
    val dimension: String,
    @field:Json(name = "residents")
    val residents: List<String>
)

fun List<LocationDto>.asModel(): List<LocationDBEntity> {

    return map {
        LocationDBEntity(
            name = it.name,
            id = it.id,
            type = it.type,
            dimension = it.dimension,
            residents = it.residents.map {
                it.replace(
                    "https://rickandmortyapi.com/api/character/",
                    ""
                )
            }.toList()
        )
    }
}

fun LocationDto.asModelOne(): LocationDBEntity {
    return LocationDBEntity(
        name = this.name,
        id = this.id,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents.map {
            it.replace(
                "https://rickandmortyapi.com/api/character/",
                ""
            )
        }.toList()
    )
}

