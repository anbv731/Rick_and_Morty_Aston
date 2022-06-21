package com.example.rickandmortyaston

import com.example.rickandmortyaston.di.CharactersComponent
import dagger.Module

@Module(subcomponents = [CharactersComponent::class])
class SubcomponentsModule {}