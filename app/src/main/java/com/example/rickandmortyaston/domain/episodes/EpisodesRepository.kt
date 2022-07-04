package com.example.rickandmortyaston.domain.episodes

interface EpisodesRepository {
    suspend fun getEpisodes(
        refresh: Boolean,
        nextPage: Boolean,
        request: RequestEpisodes
    ): List<EpisodeDomain>

    suspend fun getDBEpisodes(request: RequestEpisodes): List<EpisodeDomain>
    suspend fun getEpisode(id: List<Int>): List<EpisodeDomain>
    suspend fun getDBEpisode(id: Int): EpisodeDomain
}
