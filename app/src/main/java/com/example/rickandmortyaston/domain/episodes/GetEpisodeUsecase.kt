package com.example.rickandmortyaston.domain.episodes

import javax.inject.Inject

class GetEpisodeUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(id:Int): EpisodeDomain {
        return repository.getEpisode(id)
    }
}