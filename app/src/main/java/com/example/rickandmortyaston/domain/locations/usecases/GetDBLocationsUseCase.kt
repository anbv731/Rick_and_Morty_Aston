package com.example.rickandmortyaston.domain.locations.usecases

import com.example.rickandmortyaston.domain.locations.LocationDomain
import com.example.rickandmortyaston.domain.locations.LocationRepository
import com.example.rickandmortyaston.domain.locations.RequestLocation
import javax.inject.Inject

class GetDBLocationsUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend fun execute(request: RequestLocation): List<LocationDomain> {
        return repository.getDBLocations(request)
    }
}