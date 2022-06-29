package com.example.rickandmortyaston.domain.episodes

import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.Request

interface EpisodesRepository {
    suspend fun getEpisodes(refresh:Boolean,nextPage:Boolean,request: RequestEpisodes):List<EpisodeDomain>
    suspend fun getDBEpisodes(request: RequestEpisodes):List<EpisodeDomain>
    suspend fun getEpisode(id:Int): EpisodeDomain
    }
