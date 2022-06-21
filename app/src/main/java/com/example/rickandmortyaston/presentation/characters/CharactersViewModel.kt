package com.example.rickandmortyaston.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.GetCharactersUseCase
import com.example.rickandmortyaston.domain.characters.RefreshCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val refreshCharactersUseCase: RefreshCharactersUseCase,
    private val getCharactersUseCase:GetCharactersUseCase
) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters:LiveData<List<CharacterDomain>>get() = _characters
    val errorMessage = MutableLiveData<String>()

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                refreshCharactersUseCase.execute()

            } catch (e: Exception) {
                errorMessage.postValue( e.toString())

            }
           _characters.postValue(getCharactersUseCase.execute())
        }
    }
}