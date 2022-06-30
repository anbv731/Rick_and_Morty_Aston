package com.example.rickandmortyaston.data.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharactersDao {
    @Query("select * from characters")
    fun getCharacters(): List<CharacterDBEntity>

    @Query("select * from characters WHERE id LIKE :id")
    fun getIdCharacters(id: Int): CharacterDBEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterDBEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(character: CharacterDBEntity)

    @Query("select * from characters WHERE name LIKE '%' || :name || '%' and status LIKE '%' || :status || '%' and gender LIKE '%' || :gender || '%'and species LIKE '%' || :species || '%'and type LIKE '%' || :type || '%'")
    fun searchCharacters(
        name: String,
        status: String,
        gender: String,
        species: String,
        type: String
    ): List<CharacterDBEntity>

    @Query("delete from characters")
    fun deleteCharacters()
}