package com.example.rickandmortyaston.domain.characters

class GetCharacterUseCase(private val repository: CharactersRepository) {
    suspend fun execute(id:String){
        return repository.getCharacter(id)
    }
}