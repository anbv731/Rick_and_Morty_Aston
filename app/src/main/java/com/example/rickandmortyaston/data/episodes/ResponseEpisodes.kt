package com.example.rickandmortyaston.data.episodes

import com.example.rickandmortyaston.data.network.Info
import com.squareup.moshi.Json

data class ResponseEpisodes constructor(
    @Json(name = "results")
    val results: List<EpisodeDto>,
    @Json(name = "info")
    val info: Info,
)