package com.example.rickandmortyaston

import com.example.rickandmortyaston.di.CharactersComponent
import com.example.rickandmortyaston.di.EpisodesComponent
import dagger.Module

@Module(subcomponents = [CharactersComponent::class,EpisodesComponent::class])
class SubcomponentsModule {}