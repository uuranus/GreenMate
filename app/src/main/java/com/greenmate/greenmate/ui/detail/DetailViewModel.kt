package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.Diary
import com.greenmate.greenmate.model.GreenMate
import com.greenmate.greenmate.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {

    private val _currentInfo = MutableStateFlow(GreenMate("", "", "", "", "", "", 0))
    val currentInfo: StateFlow<GreenMate> get() = _currentInfo

    private val _todoState = MutableStateFlow(1)
    val todoState: StateFlow<Int> get() = _todoState

    private val _diarySate = MutableStateFlow(0)
    val diaryState: StateFlow<Int> get() = _diarySate

    private val _todoList = MutableStateFlow<List<Todo>>(
        listOf(
            Todo("물주기", R.drawable.icon_water, true),
            Todo("환기하기", R.drawable.icon_wind, true),
            Todo("영양관리", R.drawable.icon_medical, false)
        )
    )
    val todoList: StateFlow<List<Todo>> get() = _todoList

    private val _diaryList = MutableStateFlow(
        listOf(
            Diary("05월", "11", emptyList()),
            Diary(
                "05월", "10", listOf(
                    Todo("물주기", R.drawable.icon_water, true),
                    Todo("환기하기", R.drawable.icon_wind, true),
                    Todo("영양관리", R.drawable.icon_medical, true)
                )
            ),
            Diary(
                "05월", "9", listOf(
                    Todo("물주기", R.drawable.icon_water, true),
                    Todo("환기하기", R.drawable.icon_wind, true)
                )
            )
        )
    )
    val diaryList: StateFlow<List<Diary>> get() = _diaryList

    fun setFocus(isTodo: Boolean) {
        if (isTodo) {
            _todoState.value = 1
            _diarySate.value = 0
        } else {
            _todoState.value = 0
            _diarySate.value = 1
        }
    }

    fun setCurrentInfo(greenMate: GreenMate) {
        _currentInfo.value = greenMate
    }
}