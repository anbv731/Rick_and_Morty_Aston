package com.example.rickandmortyaston.presentation.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.use_cases.GetCharacterUseCase
import com.example.rickandmortyaston.domain.characters.use_cases.GetDBCharacterUseCase
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.GetDBEpisodeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesDetailViewModel @Inject constructor(
    private val getDBEpisodeUseCase: GetDBEpisodeUseCase,
    private val getDBCharacterUseCase: GetDBCharacterUseCase,
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _episode = MutableLiveData<EpisodeDomain>()
    val episode: LiveData<EpisodeDomain> get() = _episode
    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters: LiveData<List<CharacterDomain>> get() = _characters
    val errorMessage = MutableLiveData<String>()

    fun getData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _episode.postValue(getDBEpisodeUseCase.execute(id))
            } catch (e: Exception) {
                errorMessage.postValue(e.toString())
            }
        }
    }

    fun getCharacters(episode: EpisodeDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharacterUseCase.execute(episode.characters.map { it.toInt() }
                    .toList()))
            } catch (e: Exception) {
                errorMessage.postValue("Check connection")
                try {
                    _characters.postValue(episode.characters.map { getDBCharacterUseCase.execute(it.toInt()) }
                        .toList().filterNotNull())
                } catch (e: Exception) {
                }
            }
        }
    }
}