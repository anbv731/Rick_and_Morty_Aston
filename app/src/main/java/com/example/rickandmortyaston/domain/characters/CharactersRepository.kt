package com.example.rickandmortyaston.domain.characters

interface CharactersRepository {
    suspend fun getCharacters(refresh:Boolean,nextPage:Boolean,request:Request):List<CharacterDomain>
    suspend fun getDBCharacters(request:Request):List<CharacterDomain>
    suspend fun getDBCharacter(id:Int): CharacterDomain
    suspend fun getCharacter(id:Int):CharacterDomain
    suspend fun refreshCharacters(request: Request):List<CharacterDomain>
}