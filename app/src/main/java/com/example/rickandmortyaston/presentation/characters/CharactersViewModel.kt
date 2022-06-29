package com.example.rickandmortyaston.presentation.characters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.R
import com.example.rickandmortyaston.domain.characters.*
import com.example.rickandmortyaston.domain.characters.use_cases.GetCharactersUseCase
import com.example.rickandmortyaston.domain.characters.use_cases.GetDBCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val context: Context,
    private val getCharactersUseCase: GetCharactersUseCase,
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
                _characters.postValue(getCharactersUseCase.execute(true,false,_request))

            } catch (e: Exception) {
                errorMessage.postValue(e.message)
                _characters.postValue(getDBCharactersUseCase.execute(_request))
            }
        }
    }

    fun searchData() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
            val response = getCharactersUseCase.execute(false,false, _request)
            _characters.postValue(response)
            if (response.isEmpty()) {
                errorMessage.postValue(context.getString(R.string.nothingToShow))}
            }catch(e: Exception){errorMessage.postValue(e.message)
                _characters.postValue(getDBCharactersUseCase.execute(_request))}
        }
    }
    fun nextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _characters.postValue(getCharactersUseCase.execute(false,true, _request))
            } catch (e: Exception) {
                errorMessage.postValue(e.message)
            }
        }
    }

    fun changeName(value: String) {
        _request.name=value
        searchData()
    }

    fun changeStatus(value: Int) {
        if (value == -1) {
            _request.status = ""
        } else {
            _request.status = Status.values()[value].name
        }
        searchData()
    }

    fun changeGender(value: Int) {
        if (value == -1) {
            _request.gender = ""
        } else {
            _request.gender = Gender.values()[value].name
        }
        searchData()
    }

    fun changeSpecies(value: Int) {
        if (value == -1) {
            _request.species = ""
        } else {
            _request.species = Species.values()[value].name
        }
        searchData()
    }
    fun changeType(value: Int) {
        if (value == -1) {
            _request.type = ""
        } else {
            _request.type = Type.values()[value].name
        }
        searchData()
    }
}
