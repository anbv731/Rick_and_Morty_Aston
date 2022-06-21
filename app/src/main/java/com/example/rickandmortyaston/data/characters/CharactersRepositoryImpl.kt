package com.example.rickandmortyaston.data.characters

import android.content.Context
import com.example.characters.data.database.asDomainModel
import com.example.characters.data.database.asListDomainModel
import com.example.characters.data.database.getDatabase
import com.example.characters.data.network.RetrofitClient
import com.example.characters.data.network.asModel
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val context: Context,
) : CharactersRepository {
    private val database = getDatabase(context)


    override suspend fun refreshCharacters(): List<CharacterDomain> {
        try {
            val characters = RetrofitClient().getApi().getData().results
            database.charactersDao.insertAll(characters.asModel())
        } catch (e: Exception) {
            println(e)
        }
        return database.charactersDao.getCharacters().asListDomainModel()
    }
    override suspend fun getCharacter(id: Int): CharacterDomain {
        return database.charactersDao.getIdCharacters(id).asDomainModel()
    }
}
