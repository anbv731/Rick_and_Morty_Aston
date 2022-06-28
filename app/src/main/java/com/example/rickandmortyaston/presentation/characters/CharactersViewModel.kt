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
    private val context: Context,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase,
    private val getDBCharactersUseCase: GetDBCharactersUseCase
) : ViewModel() {
    private val _characters = MutableLiveData<List<CharacterDomain>>()
    val characters: LiveData<List<CharacterDomain>> get() = _characters
    val errorMessage = MutableLiveData<String>()
    val request = MutableLiveData<Request>()
    var _request = Request()


    init {
        refreshData()
    }

    fun refreshData() {
        _characters.postValue(emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharactersUseCase.execute(true,_request))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _characters.postValue(getDBCharactersUseCase.execute())
            }
        }
    }

    fun searchData(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchCharactersUseCase.execute(query)
            _characters.postValue(response)
            if (response.isEmpty()) {
                errorMessage.postValue(context.getString(R.string.nothingToShow))
            }
        }
    }

    fun changeStatus(value: Int) {
        if(value==-1){_request.status = null}
        else{_request.status = Status.values()[value]}
        request.postValue(_request)
        println("viewmodel"+_request.status)
    }
    fun changeGender(value: Int) {
        if(value==-1){_request.gender = null}
        else{_request.gender = Gender.values()[value]}
        request.postValue(_request)
    }
    fun changeSpecies(value:Int){
        if(value==-1){_request.species=null}
        else{_request.species=Species.values()[value]}
    }

    fun nextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharactersUseCase.execute(false,_request))
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }
}
