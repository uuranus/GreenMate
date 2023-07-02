package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.Diary
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.data.Todo
import com.greenmate.greenmate.model.repository.GreenMateRepository
import com.greenmate.greenmate.ui.detail.DetailFragment.Companion.TODO
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

    private val _isSaveSuccess = MutableStateFlow(false)
    val isSaveSuccess: StateFlow<Boolean> get() = _isSaveSuccess

    private val _currentGreenMate = MutableStateFlow(
        GreenMate(
            id = "",
            name = "", type = "", image = ""
        )
    )
    val currentGreenMate: StateFlow<GreenMate> get() = _currentGreenMate

    private val _todoFocusState = MutableStateFlow(1)
    val todoFocusState: StateFlow<Int> get() = _todoFocusState

    private val _diaryFocusSate = MutableStateFlow(0)
    val diaryFocusSate: StateFlow<Int> get() = _diaryFocusSate

    private val _todoList = MutableStateFlow(
        listOf(
            Todo("물주기", R.drawable.icon_water, false),
            Todo("환기하기", R.drawable.icon_wind, false),
            Todo("영양관리", R.drawable.icon_medical, false)
        )
    )
    val todoList: StateFlow<List<Todo>> get() = _todoList

    private val _diaryList = MutableStateFlow<List<Diary>>(
        emptyList()
    )
    val diaryList: StateFlow<List<Diary>> get() = _diaryList

    /** edit **/
    private val _imageUrl = MutableStateFlow(_currentGreenMate.value.image)
    private val _changedImageUrl = MutableStateFlow("")
    val changedImageUrl: StateFlow<String> get() = _changedImageUrl
    val imageUrl: StateFlow<String> get() = _imageUrl

    private val _greenMateName = MutableStateFlow("")
    val greenMateName = MutableStateFlow("")

    fun saveImageUrl(url: String) {
        _changedImageUrl.value = url
    }

    fun onNameChanged() {
        _greenMateName.value = greenMateName.value
    }

    fun getCurrentId() = _currentGreenMate.value.id
    fun changeGreenMateInfo() {
        if (greenMateName.value.isEmpty()) {
            _snackBarMessage.value = "닉네임을 입력하세요!"
            return
        }

        val newImageUrl =
            if (_changedImageUrl.value.isNotEmpty()) _changedImageUrl.value else _greenMateName.value

        viewModelScope.launch {
            val response =
                repository.editGreenMateName(_currentGreenMate.value.id, _greenMateName.value)
            val response2 = repository.editGreenMateImage(_currentGreenMate.value.id, newImageUrl)
            _isEditSuccess.value = true
        }
    }

    fun setIsDeleted(deleted: Boolean) {
        _isDeleted.value = deleted
    }

    fun deleteGreenMate() {
        viewModelScope.launch {
            val response = repository.deleteGreenMate(_currentGreenMate.value.id)
            _isDeleted.value = response.isSuccess
        }
    }

    fun setFocus(isTodoFocused: Boolean) {
        if (isTodoFocused) {
            _todoFocusState.value = IS_FOCUSED
            _diaryFocusSate.value = IS_NOT_FOCUSED
        } else {
            _todoFocusState.value = IS_NOT_FOCUSED
            _diaryFocusSate.value = IS_FOCUSED
        }
    }

    fun setCurrentInfo(greenMate: GreenMate) {
        _currentGreenMate.value = greenMate
        _imageUrl.value = greenMate.image
        greenMateName.value = _currentGreenMate.value.name
    }

    fun setCurrentInfoAgain() {
        if (_isEditSuccess.value) {
            _currentGreenMate.value = _currentGreenMate.value.copy(
                name = _greenMateName.value,
                image = _imageUrl.value
            )

            _isEditSuccess.value = false
        }
    }

    fun getAllDiaries() {
        viewModelScope.launch {
            val response = repository.getAllDiaries(_currentGreenMate.value.id)
            response.getOrNull()?.let {
                _diaryList.value = it
            }
        }
    }

    fun saveNewGardening(todoName: String) {
        viewModelScope.launch {
            val response = repository.addDiary("testModule3", todoName)
            _isSaveSuccess.value = response.isSuccess
            if (response.isSuccess) {
                _todoList.value = _todoList.value.filter { it.name != todoName }
            }
        }
    }

    companion object {
        const val IS_FOCUSED = 1
        const val IS_NOT_FOCUSED = 0
    }
}