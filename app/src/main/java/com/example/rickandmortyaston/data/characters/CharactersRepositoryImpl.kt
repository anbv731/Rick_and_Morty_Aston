package com.example.rickandmortyaston.data.characters

import android.content.Context
import com.example.characters.data.database.asDomainModel
import com.example.characters.data.database.asListDomainModel
import com.example.characters.data.database.getDatabase
import com.example.characters.data.network.RetrofitClient
import com.example.characters.data.network.asModel
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import com.example.rickandmortyaston.domain.characters.Request
import com.example.rickandmortyaston.domain.characters.Status
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val context: Context,
) : CharactersRepository {
    private val database = getDatabase(context)
    private var page = 1
    private var maxPage = 1


    override suspend fun getCharacters(refresh: Boolean,request: Request): List<CharacterDomain> {
        if (refresh) {
            page = 1
            val result = RetrofitClient().getApi().getPageData(page, request.name,request.status,request.gender,request.species,request.type)
            maxPage = result.info.pages.toInt()
            val characters = result.results
            database.charactersDao.deleteCharacters()
            database.charactersDao.insertAll(characters.asModel())
            return database.charactersDao.getCharacters().asListDomainModel()
        } else if (page < maxPage) {
            page++
            val result = RetrofitClient().getApi().getPageData(page,request.name,request.status,request.gender,request.species,request.type)
            val characters = result.results
            database.charactersDao.insertAll(characters.asModel())
            return database.charactersDao.getCharacters().asListDomainModel()
        } else {
            throw Exception(context.getString(R.string.theEnd))
        }
    }

    override suspend fun getCharacter(id: Int): CharacterDomain {
        println("call getCharacter maxpage $maxPage")
        return database.charactersDao.getIdCharacters(id).asDomainModel()
    }

    override suspend fun searchCharacters(query: String): List<CharacterDomain> {
        return database.charactersDao.searchCharacters(query).asListDomainModel()
    }

    override suspend fun getDBCharacters(): List<CharacterDomain> {
       return database.charactersDao.getCharacters().asListDomainModel()
    }
}
