package com.example.rickandmortyaston.domain.characters

import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute(id:Int):CharacterDomain{
        return repository.getCharacter(id)
    }
}