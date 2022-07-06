package com.example.rickandmortyaston.domain.episodes.usecases

import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes
import javax.inject.Inject

class GetDBEpisodesUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(request: RequestEpisodes): List<EpisodeDomain> {
        return repository.getDBEpisodes(request)
    }
}