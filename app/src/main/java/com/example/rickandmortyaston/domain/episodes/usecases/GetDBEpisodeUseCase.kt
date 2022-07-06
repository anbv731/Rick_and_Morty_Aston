package com.example.rickandmortyaston.domain.episodes.usecases

import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import javax.inject.Inject

class GetDBEpisodeUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(id: Int): EpisodeDomain {
        return repository.getDBEpisode(id)
    }
}