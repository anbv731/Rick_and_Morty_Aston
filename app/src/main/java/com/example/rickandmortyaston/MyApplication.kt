package com.example.rickandmortyaston

import android.app.Application
import com.example.rickandmortyaston.di.CharactersComponent
import com.example.rickandmortyaston.di.CharactersComponentProvider


class MyApplication : Application(), CharactersComponentProvider {

    private val appComponent = DaggerApplicationComponent.create()
    override fun provideCharactersComponent(): CharactersComponent {
        return appComponent.charactersComponent().create(applicationContext)
    }
}