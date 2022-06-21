package com.example.rickandmortyaston.domain.characters

import javax.inject.Inject


class RefreshCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute():List<CharacterDomain> {
        return repository.refreshCharacters()
    }
}