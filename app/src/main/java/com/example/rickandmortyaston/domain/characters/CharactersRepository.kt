package com.example.rickandmortyaston.domain.characters

interface CharactersRepository {
    suspend fun getCharacters(refresh:Boolean,request:Request):List<CharacterDomain>
    suspend fun getDBCharacters():List<CharacterDomain>
    suspend fun getCharacter(id:Int): CharacterDomain
    suspend fun searchCharacters(query:String):List<CharacterDomain>
}