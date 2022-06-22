package com.example.rickandmortyaston.domain.characters

import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute(query:String) :List<CharacterDomain>{
        return repository.searchCharacters(query)
    }
}