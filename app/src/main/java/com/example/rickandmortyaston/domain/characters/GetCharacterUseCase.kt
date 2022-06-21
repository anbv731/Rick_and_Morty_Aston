package com.example.rickandmortyaston.domain.characters

class GetCharacterUseCase(private val repository: CharactersRepository) {
    suspend fun execute(id:Int):CharacterDomain{
        return repository.getCharacter(id)
    }
}