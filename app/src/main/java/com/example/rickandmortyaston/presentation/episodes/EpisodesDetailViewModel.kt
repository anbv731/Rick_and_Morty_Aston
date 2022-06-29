package com.example.rickandmortyaston.presentation.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.use_cases.GetCharacterUseCase
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.GetEpisodeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesDetailViewModel @Inject constructor(private val getEpisodeUseCase: GetEpisodeUseCase
) : ViewModel() {

    private val _episode = MutableLiveData<EpisodeDomain>()
    val episode: LiveData<EpisodeDomain>get() = _episode
    val errorMessage = MutableLiveData<String>()

    fun getData(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _episode.postValue(getEpisodeUseCase.execute(id))

            } catch (e: Exception) {
                errorMessage.postValue( e.toString())

            }
        }
    }
}