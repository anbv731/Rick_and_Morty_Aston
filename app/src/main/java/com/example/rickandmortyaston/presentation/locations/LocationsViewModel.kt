package com.example.rickandmortyaston.presentation.locations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.GetDBEpisodesUseCase
import com.example.rickandmortyaston.domain.episodes.GetEpisodesUseCase
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes
import com.example.rickandmortyaston.domain.locations.GetDBLocationsUseCase
import com.example.rickandmortyaston.domain.locations.GetLocationsUseCase
import com.example.rickandmortyaston.domain.locations.LocationDomain
import com.example.rickandmortyaston.domain.locations.RequestLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val context: Context,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getDBLocationsUseCase: GetDBLocationsUseCase
) : ViewModel() {
    private val _locations = MutableLiveData<List<LocationDomain>>()
    val locations: LiveData<List<LocationDomain>> get() = _locations
    val errorMessage = MutableLiveData<String>()
    var request = RequestLocation()


    init {
        refreshData()
    }

    fun refreshData() {
        _locations.postValue(emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _locations.postValue(getLocationsUseCase.execute(true,false,request))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _locations.postValue(getDBLocationsUseCase.execute(request))
            }
        }
    }

    fun searchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = getLocationsUseCase.execute(false,false, request)
                _locations.postValue(response)
                if (response.isEmpty()) {
                    errorMessage.postValue(context.getString(R.string.nothingToShow))}
            }catch(e: Exception){errorMessage.postValue(e.message)
                _locations.postValue(getDBLocationsUseCase.execute(request))}
        }
    }
    fun nextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _locations.postValue(getLocationsUseCase.execute(false,true, request))
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }

    fun changeName(value: String) {
        request.name=value
        searchData()
    }
    fun changeType(value: Int) {
        if (value == -1) {
            request.type = ""
        } else {
           request.type = "S0${value+1}"
        }
        searchData()
    }
    fun changeDimension(value: Int) {
        if (value == -1) {
            request.dimension = ""
        } else {
            request.dimension = "S0${value+1}"
        }
        searchData()
    }
}