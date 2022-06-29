package com.example.rickandmortyaston.di

import com.example.rickandmortyaston.data.episodes.EpisodesRepositoryImpl
import com.example.rickandmortyaston.domain.episodes.EpisodesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class EpisodesModule {
    @Binds
    abstract fun getEpisodesRepository(repository: EpisodesRepositoryImpl): EpisodesRepository
}