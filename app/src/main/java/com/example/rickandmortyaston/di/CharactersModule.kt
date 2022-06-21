package com.example.rickandmortyaston.di

import com.example.rickandmortyaston.data.characters.CharactersRepositoryImpl
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import dagger.Binds
import dagger.Module

@Module
abstract class CharactersModule {
    @Binds
    abstract fun getCharactersRepository(repository: CharactersRepositoryImpl): CharactersRepository

}