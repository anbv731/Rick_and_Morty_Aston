package com.example.rickandmortyaston.domain.characters

import javax.inject.Inject

class GetDBCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute():List<CharacterDomain> {
        return repository.getDBCharacters()
    }
}