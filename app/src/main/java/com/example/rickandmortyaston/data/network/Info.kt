package com.example.rickandmortyaston.data.network

import com.example.characters.data.network.CharacterDto
import com.squareup.moshi.Json

data class Info constructor(
    @Json(name = "pages")
    val pages: String,
)