package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.characters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val context:Context,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase,
    private val getDBCharactersUseCase: GetDBCharactersUseCase
) : ViewModel() {
    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters:LiveData<List<CharacterDomain>>get() = _characters
    val errorMessage = MutableLiveData<String>()

    init {
        refreshData()
    }

    fun refreshData() {
        _characters.postValue(emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharactersUseCase.execute(true))
            } catch (e: Exception) {
                errorMessage.postValue( e.message)
                _characters.postValue(getDBCharactersUseCase.execute())
            }
        }
    }
    fun searchData(query:String){
        viewModelScope.launch(Dispatchers.IO) {
             val response=searchCharactersUseCase.execute(query)
            _characters.postValue(response)
            if(response.isEmpty()){errorMessage.postValue(context.getString(R.string.nothingToShow))}
        }
    }
    fun nextPage(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharactersUseCase.execute(false))
            } catch (e: Exception) {
                errorMessage.postValue( e.message)
            }
        }
    }
}