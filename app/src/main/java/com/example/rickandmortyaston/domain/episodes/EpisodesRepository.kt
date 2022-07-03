package com.example.rickandmortyaston.domain.episodes

import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes

interface EpisodesRepository {
    suspend fun getEpisodes(refresh:Boolean,nextPage:Boolean,request: RequestEpisodes):List<EpisodeDomain>
    suspend fun getDBEpisodes(request: RequestEpisodes):List<EpisodeDomain>
    suspend fun getEpisode(id:List<Int>): List<EpisodeDomain>
    suspend fun getDBEpisode(id:Int): EpisodeDomain
    }
