package com.example.characters.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharacterDBEntity::class], version = 1)
abstract class CharactersDataBase : RoomDatabase() {
    abstract val charactersDao: CharactersDao
}

private lateinit var INSTANCE: CharactersDataBase

fun getDatabase(context: Context): CharactersDataBase {
    synchronized(CharactersDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CharactersDataBase::class.java,
                "characters"
            ).build()
        }
    }
    return INSTANCE
}
