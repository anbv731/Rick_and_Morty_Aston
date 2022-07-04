package com.example.rickandmortyaston.domain.locations

import javax.inject.Inject

class GetDBLocationUseCase @Inject constructor(private val repository: LocationRepository) {
    suspend fun execute(id: Int): LocationDomain {
        return repository.getDBLocation(id)
    }
}