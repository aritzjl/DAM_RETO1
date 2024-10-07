package com.reto1.ultramarinos.viewmodels;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> = _darkTheme

    init {
        _darkTheme.value = false
    }

    fun toggleTheme(){
        _darkTheme.value = _darkTheme.value?.not()
    }
}
