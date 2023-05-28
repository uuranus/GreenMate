package com.greenmate.greenmate.ui.detail

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.model.data.Todo
import com.greenmate.greenmate.model.repository.GreenMateRepository
import com.greenmate.greenmate.util.getToday
import com.greenmate.greenmate.util.makeFullDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddDiaryViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {
    private val _currentId = MutableStateFlow("")
    val currentId: StateFlow<String> get() = _currentId

    private val _currentDate = MutableStateFlow("")
    val currentDate: StateFlow<String> get() = _currentDate

    private val _selectedGardeningActivity = MutableStateFlow(Todo("", 0))
    val selectedGardeningActivity: StateFlow<Todo> get() = _selectedGardeningActivity

    private val _isSaveSuccess = MutableStateFlow(false)
    val isSaveSuccess: StateFlow<Boolean> get() = _isSaveSuccess

    init {
        val today = getToday()
        _currentDate.value = makeFullDateString(
            today.year,
            today.month,
            today.date
        )
    }

    fun setCurrentId(id: String) {
        _currentId.value = id
    }

    fun setGardeningActivity(todo: Todo) {
        println("todo $todo")
        _selectedGardeningActivity.value = todo
    }

    fun saveNewGardening() {
//        val response = repository.addDiary(_currentId.value, _selectedGardeningActivity.value.name)
//        _isSaveSuccess.value = response == "null"
    }

}