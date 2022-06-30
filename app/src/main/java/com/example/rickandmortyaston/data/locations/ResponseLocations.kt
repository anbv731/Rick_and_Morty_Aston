package com.example.rickandmortyaston.data.locations

import com.example.rickandmortyaston.data.characters.CharacterDto
import com.example.rickandmortyaston.data.network.Info
import com.squareup.moshi.Json

data class ResponseLocations constructor(
    @Json(name = "results")
    val results: List<LocationDto>,
    @Json(name = "info")
    val info: Info,
)