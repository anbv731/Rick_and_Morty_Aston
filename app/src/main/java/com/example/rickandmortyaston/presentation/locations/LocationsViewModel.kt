package com.example.rickandmortyaston.presentation.locations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.di.IoDispatcher
import com.example.rickandmortyaston.domain.locations.LocationDomain
import com.example.rickandmortyaston.domain.locations.RequestLocation
import com.example.rickandmortyaston.domain.locations.usecases.GetDBLocationsUseCase
import com.example.rickandmortyaston.domain.locations.usecases.GetLocationsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val context: Context,
    private val getLocationsUseCase: GetLocationsUseCase,
    private val getDBLocationsUseCase: GetDBLocationsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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
        viewModelScope.launch(ioDispatcher) {
            try {
                _locations.postValue(getLocationsUseCase.execute(true, false, request))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _locations.postValue(getDBLocationsUseCase.execute(request))
            }
        }
    }

    private fun searchData() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = getLocationsUseCase.execute(false, false, request)
                _locations.postValue(response)
                if (response.isEmpty()) {
                    errorMessage.postValue(context.getString(R.string.nothingToShow))
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _locations.postValue(getDBLocationsUseCase.execute(request))
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch(ioDispatcher) {
            try {
                _locations.postValue(getLocationsUseCase.execute(false, true, request))
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                if (e.message != "?????????? ????????????") {
                    try {
                        _locations.postValue(getDBLocationsUseCase.execute(request))
                    } catch (e: Exception) {
                        errorMessage.postValue(e.message)
                    }

                }
            }
        }
    }

    fun changeName(value: String) {
        request.name = value
        searchData()
    }

    fun changeType(value: Int) {
        if (value == -1) {
            request.type = ""
        } else {
            request.type = RequestLocation.typeLocations[value]
        }
        searchData()
    }

    fun changeDimension(value: Int) {
        if (value == -1) {
            request.dimension = ""
        } else {
            request.dimension = RequestLocation.dimension[value]
        }
        searchData()
    }
}