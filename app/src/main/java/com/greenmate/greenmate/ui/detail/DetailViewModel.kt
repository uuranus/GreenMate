package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {

    private val _currentInfo = MutableStateFlow(
        GreenMate(
            name = "", type = "", image = R.drawable.plant1
        )
    )
    val currentInfo: StateFlow<GreenMate> get() = _currentInfo

    private val _todoState = MutableStateFlow(1)
    val todoState: StateFlow<Int> get() = _todoState

    private val _diarySate = MutableStateFlow(0)
    val diaryState: StateFlow<Int> get() = _diarySate

    private val _todoList = MutableStateFlow(
        listOf(
            Todo("물주기", R.drawable.icon_water, true),
            Todo("환기하기", R.drawable.icon_wind, true),
            Todo("영양관리", R.drawable.icon_medical, false)
        )
    )
    val todoList: StateFlow<List<Todo>> get() = _todoList

    private val _diaryList = MutableStateFlow(
        listOf(
            Diary(
                "05월", "11",
                mutableListOf(),
            ),
            Diary(
                "05월",
                "10",
                mutableListOf(
                    Todo("물주기", R.drawable.icon_water, true),
                    Todo("환기하기", R.drawable.icon_wind, true),
                    Todo("영양관리", R.drawable.icon_medical, true)
                ),
            ),
            Diary(
                "05월", "9",
                mutableListOf(
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

    fun getImageUrl(): Int = _currentInfo.value.image

    fun addNewDiary(newTask: String) {
        val newList = _diaryList.value.toList().mapIndexed { index, diary ->
            if (index == 0) {
                Diary(
                    diary.dateMonth,
                    diary.dateDate,
                    diary.list.toMutableList().also {
                        it.add(
                            Todo(
                                newTask,
                                R.drawable.icon_water
                            )
                        )
                    }
                )
            } else {
                diary
            }
        }
        _diaryList.value = newList
    }

    fun deleteGreenMate() {

    }

}