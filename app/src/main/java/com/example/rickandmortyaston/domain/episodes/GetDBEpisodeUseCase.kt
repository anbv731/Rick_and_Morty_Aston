package com.example.rickandmortyaston.domain.episodes

import javax.inject.Inject

class GetDBEpisodeUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(id: Int): EpisodeDomain {
        return repository.getDBEpisode(id)
    }
}