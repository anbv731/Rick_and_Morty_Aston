package com.example.rickandmortyaston

import android.app.Application
import com.example.rickandmortyaston.di.CharactersComponent
import com.example.rickandmortyaston.di.CharactersComponentProvider
import com.example.rickandmortyaston.di.EpisodesComponent
import com.example.rickandmortyaston.di.EpisodesComponentProvider


class MyApplication : Application(), CharactersComponentProvider ,EpisodesComponentProvider{

    private val appComponent = DaggerApplicationComponent.create()
    override fun provideCharactersComponent(): CharactersComponent {
        return appComponent.charactersComponent().create(applicationContext)
    }

    override fun provideEpisodesComponent(): EpisodesComponent {
        return  appComponent.episodesComponent().create(applicationContext)
    }
}