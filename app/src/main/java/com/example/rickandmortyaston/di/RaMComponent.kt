package com.example.rickandmortyaston.di

import android.content.Context
import com.example.rickandmortyaston.presentation.characters.CharacterDetailFragment
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import com.example.rickandmortyaston.presentation.episodes.EpisodeDetailFragment
import com.example.rickandmortyaston.presentation.episodes.EpisodesFragment
import com.example.rickandmortyaston.presentation.locations.LocationsFragment
import dagger.BindsInstance
import dagger.Component


@Component(modules = [RaMModule::class])
interface RaMComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): RaMComponent
    }

    fun injectCharactersFragment(target: CharactersFragment)
    fun injectCharactersDetailFragment(target: CharacterDetailFragment)
    fun injectEpisodesFragment(target: EpisodesFragment)
    fun injectEpisodeDetailFragment(target: EpisodeDetailFragment)
    fun injectLocationsFragment(target:LocationsFragment)


}