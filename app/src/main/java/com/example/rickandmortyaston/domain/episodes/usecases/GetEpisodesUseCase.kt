package com.example.rickandmortyaston.domain.episodes.usecases

import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes
import javax.inject.Inject


class GetEpisodesUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(
        refresh: Boolean,
        nextPage: Boolean,
        request: RequestEpisodes
    ): List<EpisodeDomain> {
        return repository.getEpisodes(refresh, nextPage, request)
    }
}