package com.greenmate.greenmate.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.GreenMate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _greenMates = MutableStateFlow(
        listOf(
            GreenMate("그리니", "식물1", "키우기 시작한지 1일째", "좋음", "좋음", "좋음", R.drawable.plant1),
            GreenMate("그리니2", "식물2", "키우기 시작한지 15일째", "좋음", "좋음", "나쁨", R.drawable.plant1),
            GreenMate("그린조아", "식물3", "키우기 시작한지 52일째", "좋음", "나쁨", "좋음", R.drawable.plant2),
            GreenMate("그리니4", "식물4", "키우기 시작한지 24일째", "좋음", "좋음", "좋음", R.drawable.plant2),
            GreenMate("그린조아2", "식물5", "키우기 시작한지 10일째", "좋음", "보통", "보통", R.drawable.plant1),
            GreenMate("그린조아5", "식물6", "키우기 시작한지 30일째", "나쁨", "좋음", "보통", R.drawable.plant2)
        )
    )
    val greenMates: StateFlow<List<GreenMate>> get() = _greenMates

    private val _mainGreenMate =
        MutableStateFlow(_greenMates.value[2])
    val mainGreenMate: StateFlow<GreenMate> get() = _mainGreenMate

    fun setMainGreenMate(greenMate: GreenMate) {
        _mainGreenMate.value = greenMate
    }

    fun getSelectedGreenMate(): GreenMate {
        return _mainGreenMate.value
    }
}