package com.example.cognittiveassesmenttests.helpers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> get() = _refresh

    fun refreshPage() {
        _refresh.value = true
    }

    fun resetRefresh() {
        _refresh.value = false
    }
}