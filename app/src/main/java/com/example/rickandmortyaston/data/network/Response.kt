package com.example.characters.data.network

import com.squareup.moshi.Json

data class Response constructor(
    @Json(name = "results")
    val results: List<CharacterDto>,
)