package com.example.rickandmortyaston

import android.app.Application
import com.example.rickandmortyaston.di.DaggerRaMComponent
import com.example.rickandmortyaston.di.RaMComponent
import com.example.rickandmortyaston.di.RaMComponentProvider


class MyApplication : Application(), RaMComponentProvider {

    private val appComponent = DaggerRaMComponent.factory()
    override fun provideRaMComponent(): RaMComponent {
        return appComponent.create(applicationContext)
    }
}