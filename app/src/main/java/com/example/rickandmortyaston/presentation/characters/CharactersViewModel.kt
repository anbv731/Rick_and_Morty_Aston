package com.example.rickandmortyaston.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.characters.CharacterDomain
import com.example.rickandmortyaston.domain.characters.GetCharactersUseCase
import com.example.rickandmortyaston.domain.characters.RefreshCharactersUseCase
import com.example.rickandmortyaston.domain.characters.SearchCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val refreshCharactersUseCase: RefreshCharactersUseCase,
    private val getCharactersUseCase:GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters:LiveData<List<CharacterDomain>>get() = _characters
    val errorMessage = MutableLiveData<String>()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                refreshCharactersUseCase.execute()

            } catch (e: Exception) {
                errorMessage.postValue( e.toString())

            }
           _characters.postValue(getCharactersUseCase.execute())
        }
    }
    fun searchData(query:String){
        viewModelScope.launch(Dispatchers.IO) {
             val response =searchCharactersUseCase.execute(query)
            _characters.postValue(response)
//            if(response.isNotEmpty())
//            {_characters.postValue(response)}
//            else {_characters.postValue(emptyList<CharacterDomain>()) }
        }

    }
}