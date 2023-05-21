package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailEditViewModel : ViewModel() {

    private val _imageUrl = MutableStateFlow(0)
    val imageUrl: StateFlow<Int> get() = _imageUrl

    private val _greenMateName = MutableStateFlow("")
    val greenMateName: StateFlow<String> get() = _greenMateName


    fun setSelectedGreenMateInfo(imageUrl: Int) {
        _imageUrl.value = imageUrl
    }

    fun changeGreenMateInfo(){

    }

    fun deleteGreenMate(){

    }
}