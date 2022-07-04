package com.example.rickandmortyaston.domain.episodes

import javax.inject.Inject

class GetDBEpisodesUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(request: RequestEpisodes): List<EpisodeDomain> {
        return repository.getDBEpisodes(request)
    }
}