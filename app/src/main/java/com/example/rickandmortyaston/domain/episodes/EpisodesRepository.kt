package com.example.rickandmortyaston.domain.episodes

import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.Request

interface EpisodesRepository {
    suspend fun getCharacters(refresh:Boolean,nextPage:Boolean,request: Request):List<CharacterDomain>
    suspend fun getDBCharacters(request: Request):List<CharacterDomain>
    suspend fun getCharacter(id:Int): CharacterDomain
    suspend fun refreshCharacters(request: Request):List<CharacterDomain>
    }
