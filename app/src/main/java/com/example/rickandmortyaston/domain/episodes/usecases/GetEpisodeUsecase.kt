package com.example.rickandmortyaston.domain.episodes.usecases

import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import javax.inject.Inject

class GetEpisodeUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(id: List<Int>): List<EpisodeDomain> {
        return repository.getEpisode(id)
    }
}