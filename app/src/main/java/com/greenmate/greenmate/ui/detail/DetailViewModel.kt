package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.Todo
import com.greenmate.greenmate.model.repository.GreenMateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {
    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage: StateFlow<String> get() = _snackBarMessage

    private val _isEditSuccess = MutableStateFlow(false)
    val isEditSuccess: StateFlow<Boolean> get() = _isEditSuccess

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean> get() = _isDeleted

    private val _currentInfo = MutableStateFlow(
        GreenMate(
            id = "",
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
                "2023-05-11",
                mutableListOf(
                    Todo("물주기", R.drawable.icon_water, true),
                    Todo("환기하기", R.drawable.icon_wind, true),
                    Todo("영양관리", R.drawable.icon_medical, true)
                ),
            ),
            Diary(
                "2023-05-13",
                mutableListOf(
                    Todo("물주기", R.drawable.icon_water, true),
                    Todo("환기하기", R.drawable.icon_wind, true)
                )
            )
        )
    )
    val diaryList: StateFlow<List<Diary>> get() = _diaryList

    /** edit **/
    private val _imageUrl = MutableStateFlow(_currentInfo.value.image)
    val imageUrl: StateFlow<Int> get() = _imageUrl

    private val _greenMateName = MutableStateFlow("")
    val greenMateName = MutableStateFlow("")

    fun onNameChanged() {
        _greenMateName.value = greenMateName.value
    }

    fun getCurrentId() = _currentInfo.value.id
    fun changeGreenMateInfo() {
        if (greenMateName.value.isEmpty()) {
            _snackBarMessage.value = "닉네임을 입력하세요!"
            return
        }

        val newGreenMate =
            _currentInfo.value.copy(
                image = _imageUrl.value,
                name = _greenMateName.value,
                type = _currentInfo.value.type
            )
        val response = repository.editGreenMate(newGreenMate)
        _isEditSuccess.value = true
    }

    fun setIsDeleted(deleted: Boolean) {
        _isDeleted.value = deleted
    }

    fun deleteGreenMate() {
        repository.deleteGreenMate(_currentInfo.value.id)
        _isDeleted.value = true
    }

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
        if (_currentInfo.value.id.isNotEmpty()) return
        _currentInfo.value = greenMate
        _imageUrl.value = greenMate.image
        greenMateName.value = _currentInfo.value.name
    }

    fun setCurrentInfoAgain() {
        if (_isEditSuccess.value) {
            _currentInfo.value = _currentInfo.value.copy(
                name = _greenMateName.value,
                image = _imageUrl.value
            )

            _isEditSuccess.value = false
        }
    }

    fun getAllDiaries() {
        viewModelScope.launch {
            val response = repository.getAllDiaries("testModule3")
            response.getOrNull()?.let {
                _diaryList.value = it
            }
        }
    }

}