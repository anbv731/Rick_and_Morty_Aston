package com.example.rickandmortyaston.domain.characters

interface CharactersRepository {
    suspend fun refreshCharacters(){}
    suspend fun getCharacter(id:String){}
}