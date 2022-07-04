package com.example.rickandmortyaston.data.characters

import com.example.rickandmortyaston.data.network.Info
import com.squareup.moshi.Json

data class ResponseCharacters constructor(
    @Json(name = "results")
    val results: List<CharacterDto>,
    @Json(name = "info")
    val info: Info,
)