package com.example.rickandmortyaston.domain.locations

import javax.inject.Inject


class GetLocationsUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend fun execute(refresh:Boolean,nextPage:Boolean, request: RequestLocation):List<LocationDomain> {
        return repository.getLocations(refresh,nextPage, request)
    }
}