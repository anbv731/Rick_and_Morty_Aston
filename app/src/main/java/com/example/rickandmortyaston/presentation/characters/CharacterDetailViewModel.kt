package com.example.rickandmortyaston.presentation.characters


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyaston.domain.characters.*
import com.example.rickandmortyaston.domain.characters.use_cases.GetCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _character = MutableLiveData<CharacterDomain>()
    val character:LiveData<CharacterDomain>get() = _character
    val errorMessage = MutableLiveData<String>()

    fun getData(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _character.postValue(getCharacterUseCase.execute(id))

            } catch (e: Exception) {
                errorMessage.postValue( e.toString())

            }
        }
    }
}