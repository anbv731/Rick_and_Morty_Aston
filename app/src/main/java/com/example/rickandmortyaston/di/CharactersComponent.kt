package com.example.rickandmortyaston.di

import android.content.Context
import com.example.rickandmortyaston.presentation.characters.CharacterDetailFragment
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(modules = [CharactersModule::class])
interface CharactersComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): CharactersComponent
    }

    fun injectCharactersFragment(target: CharactersFragment)
    fun injectCharactersDetailFragment(target: CharacterDetailFragment)

}