package com.example.rickandmortyaston


import com.example.rickandmortyaston.di.CharactersComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubcomponentsModule::class])
interface ApplicationComponent {

    fun charactersComponent(): CharactersComponent.Factory
}