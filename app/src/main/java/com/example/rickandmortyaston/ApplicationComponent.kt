package com.example.rickandmortyaston


import com.example.rickandmortyaston.di.CharactersComponent
import com.example.rickandmortyaston.di.EpisodesComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubcomponentsModule::class])
interface ApplicationComponent {

    fun charactersComponent(): CharactersComponent.Factory
    fun episodesComponent():EpisodesComponent.Factory
}