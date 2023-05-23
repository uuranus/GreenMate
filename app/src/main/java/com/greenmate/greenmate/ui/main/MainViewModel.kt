package com.greenmate.greenmate.ui.main

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.GreenMate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> get() = _isDataLoaded

    private val _greenMates = MutableStateFlow<List<GreenMate>>(
//        emptyList())

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
        MutableStateFlow(GreenMate("", "", image = R.drawable.plant1))
    val mainGreenMate: StateFlow<GreenMate> get() = _mainGreenMate

    fun isDataLoaded() = _isDataLoaded.value
    fun setIsDataLoaded(isDataLoaded: Boolean) {
        _isDataLoaded.value = isDataLoaded
    }

    fun isGreenMateEmpty() = _greenMates.value.isEmpty()
    fun isMainGreenMateEmpty() = _mainGreenMate.value.name.isNullOrEmpty()

    fun setMainGreenMateByFirst() {
        if (_greenMates.value.isEmpty()) return
        _mainGreenMate.value = _greenMates.value[0]
    }

    fun setMainGreenMate(greenMate: GreenMate) {
        _mainGreenMate.value = greenMate
    }

    fun getSelectedGreenMate(): GreenMate {
        return _mainGreenMate.value
    }
}