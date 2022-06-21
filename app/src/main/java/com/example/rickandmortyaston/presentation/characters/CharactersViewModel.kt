package com.example.rickandmortyaston.presentation.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.RefreshCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val refreshCharactersUseCase: RefreshCharactersUseCase
) : ViewModel() {

    val characters = MutableLiveData<List<CharacterDomain>>()
    val errorMessage = MutableLiveData<String>()

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                characters.postValue(refreshCharactersUseCase.execute())

            } catch (e: Exception) {
                errorMessage.postValue( e.toString())

            }
        }
    }
}