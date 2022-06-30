package com.example.rickandmortyaston.presentation.characters


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.*
import com.example.rickandmortyaston.domain.characters.use_cases.GetDBCharacterUseCase
import com.example.rickandmortyaston.domain.episodes.EpisodeDomain
import com.example.rickandmortyaston.domain.episodes.GetDBEpisodeUseCase
import com.example.rickandmortyaston.domain.episodes.GetEpisodeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val getDBCharacterUseCase: GetDBCharacterUseCase,
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private  val getDBEpisodeUseCase: GetDBEpisodeUseCase
) : ViewModel() {

    private val _character = MutableLiveData<CharacterDomain>()
    val character:LiveData<CharacterDomain>get() = _character
    private val _episodes = MutableLiveData<List<EpisodeDomain>>()
    val episodes: LiveData<List<EpisodeDomain>> get() = _episodes
    val errorMessage = MutableLiveData<String>()

    fun getData(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _character.postValue(getDBCharacterUseCase.execute(id))

            } catch (e: Exception) {
                errorMessage.postValue( e.toString())

            }
        }
    }
    fun getEpisodes(character: CharacterDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _episodes.postValue(character.episode.map { getEpisodeUseCase.execute(it.toInt()) }
                    .toList())
            } catch (e: Exception) {
                errorMessage.postValue("Check connection")
                try {
                    _episodes.postValue(character.episode.map { getDBEpisodeUseCase.execute(it.toInt()) }
                        .toList())
                } catch (e: Exception) {}
            }
        }
    }
}