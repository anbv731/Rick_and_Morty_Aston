package com.example.rickandmortyaston.domain.characters

interface CharactersRepository {
    suspend fun getCharacters(refresh:Boolean, nextPage:Boolean, requestCharacters:RequestCharacters):List<CharacterDomain>
    suspend fun getDBCharacters(requestCharacters:RequestCharacters):List<CharacterDomain>
    suspend fun getDBCharacter(id:Int): CharacterDomain
    suspend fun getCharacter( id:List<Int>):List<CharacterDomain>
    suspend fun refreshCharacters(requestCharacters: RequestCharacters):List<CharacterDomain>
}