package com.example.rickandmortyaston.data.characters

import android.content.Context
import com.example.characters.data.database.getDatabase
import com.example.characters.data.network.RetrofitClient
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import com.example.rickandmortyaston.domain.characters.RequestCharacters
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val context: Context,
) : CharactersRepository {
    private val database = getDatabase(context)
    private var page = 1
    private var maxPage = 1


    override suspend fun getCharacters(
        refresh: Boolean,
        nextPage: Boolean,
        requestCharacters: RequestCharacters
    ): List<CharacterDomain> {
        if (!nextPage) {
            page = 1
            val result = RetrofitClient().getApi().getCharactersData(
                page,
                requestCharacters.name,
                requestCharacters.status,
                requestCharacters.gender,
                requestCharacters.species,
                requestCharacters.type
            )
            maxPage = result.info.pages.toInt()
            val characters = result.results
            if (refresh) database.charactersDao.deleteCharacters()
            database.charactersDao.insertAll(characters.asModel())
            return database.charactersDao.searchCharacters(
                requestCharacters.name,
                requestCharacters.status,
                requestCharacters.gender,
                requestCharacters.species,
                requestCharacters.type
            ).asListDomainModel()
        } else if (page < maxPage) {
            page++
            val result = RetrofitClient().getApi().getCharactersData(
                page,
                requestCharacters.name,
                requestCharacters.status,
                requestCharacters.gender,
                requestCharacters.species,
                requestCharacters.type
            )
            val characters = result.results
            database.charactersDao.insertAll(characters.asModel())
            return database.charactersDao.searchCharacters(
                requestCharacters.name,
                requestCharacters.status,
                requestCharacters.gender,
                requestCharacters.species,
                requestCharacters.type
            ).asListDomainModel()
        } else {
            throw Exception(context.getString(R.string.theEnd))
        }
    }

    override suspend fun getDBCharacter(id: Int): CharacterDomain {
        return database.charactersDao.getIdCharacters(id).asDomainModel()
    }

    override suspend fun getCharacter(id: List<Int>): List<CharacterDomain> {
        if (id.size == 1) {
            val result = RetrofitClient().getApi().getSingleCharacter(id.toString())
            database.charactersDao.insertOne(result.asModelOne())
            return listOf(result.asModelOne().asDomainModel())
        } else {
            val result = RetrofitClient().getApi().getCharactersById(id.toString())
            database.charactersDao.insertAll(result.asModel())
            return result.asModel().asListDomainModel()
        }


    }

    override suspend fun refreshCharacters(requestCharacters: RequestCharacters): List<CharacterDomain> {
        page = 1
        val result = RetrofitClient().getApi().getCharactersData(
            page,
            requestCharacters.name,
            requestCharacters.status,
            requestCharacters.gender,
            requestCharacters.species,
            requestCharacters.type
        )
        val characters = result.results
        database.charactersDao.deleteCharacters()
        database.charactersDao.insertAll(characters.asModel())
        return database.charactersDao.searchCharacters(
            requestCharacters.name,
            requestCharacters.status,
            requestCharacters.gender,
            requestCharacters.species,
            requestCharacters.type
        ).asListDomainModel()
    }

    override suspend fun getDBCharacters(requestCharacters: RequestCharacters): List<CharacterDomain> {
        return database.charactersDao.searchCharacters(
            requestCharacters.name,
            requestCharacters.status,
            requestCharacters.gender,
            requestCharacters.species,
            requestCharacters.type
        ).asListDomainModel()
    }
}
