package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.model.Todo
import com.greenmate.greenmate.util.makeDateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

class AddDiaryViewModel : ViewModel() {
    private val _currentDate = MutableStateFlow("")
    val currentDate: StateFlow<String> get() = _currentDate

    private val _selectedGardeningActivity = MutableStateFlow(Todo("", 0))
    val selectedGardeningActivity: StateFlow<Todo> get() = _selectedGardeningActivity

    private val _isSaveSuccess = MutableStateFlow(false)
    val isSaveSuccess: StateFlow<Boolean> get() = _isSaveSuccess

    init {
        val today = Calendar.getInstance()
        _currentDate.value = makeDateString(today.get(Calendar.YEAR),
            today.get(Calendar.MONTH) + 1,
            today.get(Calendar.DATE))
    }

    fun setGardeningActivity(todo: Todo) {
        _selectedGardeningActivity.value = todo
    }

    fun saveNewGardening() {
        //TODO 네트워크 연결

        _isSaveSuccess.value = true
    }

}