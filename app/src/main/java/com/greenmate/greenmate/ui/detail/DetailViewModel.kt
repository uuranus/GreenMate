package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {

    private val _todoState = MutableStateFlow(1)
    val todoState: StateFlow<Int> get() = _todoState

    private val _diarySate = MutableStateFlow(0)
    val diaryState: StateFlow<Int> get() = _diarySate


    fun setFocus(isTodo: Boolean) {
        if (isTodo) {
            _todoState.value = 1
            _diarySate.value = 0
        } else {
            _todoState.value = 0
            _diarySate.value = 1
        }
    }
}