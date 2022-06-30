package com.example.characters.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rickandmortyaston.data.characters.CharacterDBEntity
import com.example.rickandmortyaston.data.characters.CharactersDao
import com.example.rickandmortyaston.data.database.MyTypeConverter
import com.example.rickandmortyaston.data.episodes.EpisodeDBEntity
import com.example.rickandmortyaston.data.episodes.EpisodesDao

@Database(entities = [CharacterDBEntity::class,EpisodeDBEntity::class], version = 1)
@TypeConverters (MyTypeConverter::class)
abstract class CharactersDataBase : RoomDatabase() {
    abstract val charactersDao: CharactersDao
    abstract val episodesDao:EpisodesDao
}

private lateinit var INSTANCE: CharactersDataBase

fun getDatabase(context: Context): CharactersDataBase {
    synchronized(CharactersDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CharactersDataBase::class.java,
                "ramdatabase"
            ).build()
        }
    }
    return INSTANCE
}
