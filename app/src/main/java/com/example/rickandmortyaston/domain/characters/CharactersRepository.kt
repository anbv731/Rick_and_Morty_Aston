package com.example.rickandmortyaston.domain.characters

interface CharactersRepository {
    suspend fun refreshCharacters(): List<CharacterDomain>
    suspend fun getCharacter(id:Int): CharacterDomain
}