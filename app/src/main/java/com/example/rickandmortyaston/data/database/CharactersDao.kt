package com.example.characters.data.database

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

    @Query("select * from characters WHERE name LIKE '%' || :name || '%'")
    fun searchCharacters(name: String): List<CharacterDBEntity>
}