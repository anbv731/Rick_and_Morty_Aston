package com.example.rickandmortyaston.domain.characters.use_cases

import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute(id:Int): CharacterDomain {
        return repository.getCharacter(id)
    }
}