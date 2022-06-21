package com.example.rickandmortyaston.domain.characters



class RefreshCharactersUseCase  constructor(private val repository: CharactersRepository) {
    suspend fun execute():List<CharacterDomain> {
        return repository.refreshCharacters()
    }
}