package com.example.rickandmortyaston.presentation.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.use_cases.GetCharacterUseCase
import com.example.rickandmortyaston.domain.characters.use_cases.GetDBCharacterUseCase
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.GetDBEpisodeUseCase
import com.example.rickandmortyaston.domain.locations.GetDBLocationUseCase
import com.example.rickandmortyaston.domain.locations.GetDBLocationsUseCase
import com.example.rickandmortyaston.domain.locations.GetLocationUseCase
import com.example.rickandmortyaston.domain.locations.LocationDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationDetailViewModel @Inject constructor(
    private val getDBLocationUseCase: GetDBLocationUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getDBCharacterUseCase: GetDBCharacterUseCase,
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _location = MutableLiveData<LocationDomain>()
    val location: LiveData<LocationDomain> get() = _location
    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters: LiveData<List<CharacterDomain>> get() = _characters
    val errorMessage = MutableLiveData<String>()

    fun getData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _location.postValue(getDBLocationUseCase.execute(id))
            } catch (e: Exception) {
                try{_location.postValue(getLocationUseCase.execute(id))}
                catch (e:Exception){errorMessage.postValue("Check connection")}
            }
        }
    }

    fun getCharacters(location:LocationDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharacterUseCase.execute(location.residents.map { it.toInt() }
                    .toList()))
            } catch (e: Exception) {
                errorMessage.postValue("Check connection")
                try {
                    _characters.postValue(location.residents.map { getDBCharacterUseCase.execute(it.toInt()) }
                        .toList())
                } catch (e: Exception) {
                }
            }
        }
    }
}