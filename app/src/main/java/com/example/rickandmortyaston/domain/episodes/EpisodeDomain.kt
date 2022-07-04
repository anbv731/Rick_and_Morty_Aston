package com.example.rickandmortyaston.domain.episodes

data class EpisodeDomain(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
    val characters: List<String>
)
