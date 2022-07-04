package com.example.rickandmortyaston.domain.characters.use_cases

import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import com.example.rickandmortyaston.domain.characters.RequestCharacters
import javax.inject.Inject

class GetDBCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute(requestCharacters: RequestCharacters): List<CharacterDomain> {
        return repository.getDBCharacters(requestCharacters)
    }
}