package com.example.rickandmortyaston.presentation.episodes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.di.IoDispatcher
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.RequestEpisodes
import com.example.rickandmortyaston.domain.episodes.usecases.GetDBEpisodesUseCase
import com.example.rickandmortyaston.domain.episodes.usecases.GetEpisodesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val context: Context,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val getDBEpisodesUseCase: GetDBEpisodesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _episodes = MutableLiveData<List<EpisodeDomain>>()
    val episodes: LiveData<List<EpisodeDomain>> get() = _episodes
    val errorMessage = MutableLiveData<String>()
    var request = RequestEpisodes()


    init {
        refreshData()
    }

    fun refreshData() {
        _episodes.postValue(emptyList())
        viewModelScope.launch(ioDispatcher) {
            try {
                _episodes.postValue(getEpisodesUseCase.execute(true, false, request))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _episodes.postValue(getDBEpisodesUseCase.execute(request))
            }
        }
    }

    private fun searchData() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = getEpisodesUseCase.execute(false, false, request)
                _episodes.postValue(response)
                if (response.isEmpty()) {
                    errorMessage.postValue(context.getString(R.string.nothingToShow))
                }
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _episodes.postValue(getDBEpisodesUseCase.execute(request))
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch(ioDispatcher) {
            try {
                _episodes.postValue(getEpisodesUseCase.execute(false, true, request))
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                if (e.message != "?????????? ????????????") {
                    try {
                        _episodes.postValue(getDBEpisodesUseCase.execute(request))
                    } catch (e: java.lang.Exception) {
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

    fun changeEpisode(value: Int) {
        println("change season to $value")
        if (value == -1) {
            request.episode = ""
        } else {
            request.episode = "S0${value + 1}"
        }
        searchData()
    }

    fun getSeason(): Int {
        return request.episode.substring(2, 3).toInt()
    }
}