package com.example.rickandmortyaston.di

import com.example.rickandmortyaston.data.characters.CharactersRepositoryImpl
import com.example.rickandmortyaston.data.episodes.EpisodesRepositoryImpl
import com.example.rickandmortyaston.data.locations.LocationsRepositoryImpl
import com.example.rickandmortyaston.domain.characters.CharactersRepository
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import com.example.rickandmortyaston.domain.locations.LocationRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RaMModule {
    @Binds
    abstract fun getCharactersRepository(repository: CharactersRepositoryImpl): CharactersRepository

    @Binds
    abstract fun getEpisodesRepository(repository: EpisodesRepositoryImpl): EpisodesRepository

    @Binds
    abstract fun getLocationsRepository(repository: LocationsRepositoryImpl): LocationRepository

}