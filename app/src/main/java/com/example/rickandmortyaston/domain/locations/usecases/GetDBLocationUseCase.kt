package com.example.rickandmortyaston.domain.locations.usecases

import com.example.rickandmortyaston.domain.locations.LocationDomain
import com.example.rickandmortyaston.domain.locations.LocationRepository
import javax.inject.Inject

class GetDBLocationUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend fun execute(id: Int): LocationDomain {
        return repository.getDBLocation(id)
    }
}