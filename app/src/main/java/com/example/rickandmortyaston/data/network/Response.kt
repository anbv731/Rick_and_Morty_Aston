package com.example.characters.data.network

import com.example.rickandmortyaston.data.network.Info
import com.squareup.moshi.Json

data class Response constructor(
    @Json(name = "results")
    val results: List<CharacterDto>,
    @Json(name = "info")
    val info: Info,
)