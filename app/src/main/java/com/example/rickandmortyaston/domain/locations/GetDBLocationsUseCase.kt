package com.example.rickandmortyaston.domain.locations

import javax.inject.Inject

class GetDBLocationsUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend fun execute(request: RequestLocation):List<LocationDomain> {
        return repository.getDBLocations(request)
    }
}