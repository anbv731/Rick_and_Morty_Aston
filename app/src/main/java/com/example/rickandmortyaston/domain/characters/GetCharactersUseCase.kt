package com.example.rickandmortyaston.domain.characters

import javax.inject.Inject


class GetCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute(refresh:Boolean):List<CharacterDomain> {
        return repository.getCharacters(refresh)
    }
}