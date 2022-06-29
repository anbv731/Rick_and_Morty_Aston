package com.example.rickandmortyaston.di

import android.content.Context
import com.example.rickandmortyaston.presentation.characters.CharacterDetailFragment
import com.example.rickandmortyaston.presentation.characters.CharactersFragment
import com.example.rickandmortyaston.presentation.episodes.EpisodeDetailFragment
import com.example.rickandmortyaston.presentation.episodes.EpisodesFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [EpisodesModule::class])
interface EpisodesComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): EpisodesComponent
    }

    fun injectEpisodesFragment(target: EpisodesFragment)
    fun injectEpisodesDetailFragment(target: EpisodeDetailFragment)

}
