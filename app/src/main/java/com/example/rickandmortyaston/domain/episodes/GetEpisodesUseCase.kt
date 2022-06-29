package com.example.rickandmortyaston.domain.episodes

import javax.inject.Inject


class GetEpisodesUseCase @Inject constructor(private val repository: EpisodesRepository) {
    suspend fun execute(refresh:Boolean,nextPage:Boolean, request: RequestEpisodes):List<EpisodeDomain> {
        return repository.getEpisodes(refresh,nextPage, request)
    }
}