package com.greenmate.greenmate.ui.main

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.GreenMate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _mainGreenMate: MutableStateFlow<GreenMate?> = MutableStateFlow(null)
    val mainGreenMate: StateFlow<GreenMate?> get() = _mainGreenMate

    private val _greenMates: MutableStateFlow<List<GreenMate>> = MutableStateFlow(
        listOf(
            GreenMate("그리니", "식물1", "좋음", "좋음", "좋음", R.drawable.plant1),
            GreenMate("그리니2", "식물2","좋음", "좋음", "나쁨", R.drawable.plant1),
            GreenMate("그린조아", "식물3","좋음", "나쁨", "좋음", R.drawable.plant2),
            GreenMate("그리니4", "식물4","좋음", "좋음", "좋음", R.drawable.plant2),
            GreenMate("그린조아2","식물5", "좋음", "보통", "보통", R.drawable.plant1),
            GreenMate("그린조아5", "식물6","나쁨", "좋음", "보통", R.drawable.plant2)
        )
    )
    val greenMates: StateFlow<List<GreenMate>> get() = _greenMates

    fun setMainGreenMate(greenMate: GreenMate) {
        _mainGreenMate.value = greenMate
    }
}