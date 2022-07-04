package com.example.rickandmortyaston.domain.locations

interface LocationRepository {
    suspend fun getLocations(
        refresh: Boolean,
        nextPage: Boolean,
        request: RequestLocation
    ): List<LocationDomain>

    suspend fun getDBLocations(request: RequestLocation): List<LocationDomain>
    suspend fun getLocation(id: Int): LocationDomain
    suspend fun getDBLocation(id: Int): LocationDomain
}
