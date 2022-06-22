package com.example.rickandmortyaston.domain.characters

interface CharactersRepository {
    suspend fun refreshCharacters()
    suspend fun getCharacter(id:Int): CharacterDomain
    suspend fun getCharacters():List<CharacterDomain>
    suspend fun searchCharacters(query:String):List<CharacterDomain>
}