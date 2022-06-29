package com.example.rickandmortyaston.domain.characters.use_cases

import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import com.example.rickandmortyaston.domain.characters.Request
import javax.inject.Inject


class GetCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {
    suspend fun execute(refresh:Boolean,nextPage:Boolean, request: Request):List<CharacterDomain> {
        return repository.getCharacters(refresh,nextPage, request)
    }
}