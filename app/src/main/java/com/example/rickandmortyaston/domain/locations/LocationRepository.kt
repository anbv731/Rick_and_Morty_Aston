package com.example.rickandmortyaston.domain.locations

import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes

interface LocationRepository {
    suspend fun getLocations(refresh:Boolean,nextPage:Boolean,request: RequestLocation):List<LocationDomain>
    suspend fun getDBLocations(request: RequestLocation):List<LocationDomain>
    suspend fun getLocation(id:Int): LocationDomain
    suspend fun getDBLocation(id:Int): LocationDomain
    }
