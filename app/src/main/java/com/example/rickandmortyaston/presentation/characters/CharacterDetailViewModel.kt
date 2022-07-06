package com.example.rickandmortyaston.presentation.characters


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.di.IoDispatcher
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.use_cases.GetDBCharacterUseCase
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.usecases.GetDBEpisodeUseCase
import com.example.rickandmortyaston.domain.episodes.usecases.GetEpisodeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val getDBCharacterUseCase: GetDBCharacterUseCase,
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val getDBEpisodeUseCase: GetDBEpisodeUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher

) : ViewModel() {
    private val _character = MutableLiveData<CharacterDomain>()
    val character: LiveData<CharacterDomain> get() = _character
    private val _episodes = MutableLiveData<List<EpisodeDomain>>()
    val episodes: LiveData<List<EpisodeDomain>> get() = _episodes
    val errorMessage = MutableLiveData<String>()

    fun getData(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            try {
                _character.postValue(getDBCharacterUseCase.execute(id))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)

            }
        }
    }

    fun getEpisodes(character: CharacterDomain) {
        viewModelScope.launch(ioDispatcher) {
            try {
                _episodes.postValue(getEpisodeUseCase.execute(character.episode.map { it.toInt() }))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                try {
                    _episodes.postValue(character.episode.map { getDBEpisodeUseCase.execute(it.toInt()) }
                        .toList().filterNotNull())
                } catch (e: Exception) {
                }
            }
        }
    }
}
