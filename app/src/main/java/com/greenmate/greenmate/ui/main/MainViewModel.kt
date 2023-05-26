package com.greenmate.greenmate.ui.main

import androidx.lifecycle.ViewModel
import com.greenmate.greenmate.R
import com.greenmate.greenmate.model.data.GreenMate
import com.greenmate.greenmate.model.repository.GreenMateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GreenMateRepository,
) : ViewModel() {
    private val _isDataLoaded = MutableStateFlow(false)
    val isDataLoaded: StateFlow<Boolean> get() = _isDataLoaded

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess: StateFlow<Boolean> get() = _isSuccess

    private val _greenMates = MutableStateFlow<List<GreenMate>>(
        emptyList()
    )
    val greenMates: StateFlow<List<GreenMate>> get() = _greenMates

    private val _mainGreenMate =
        MutableStateFlow(GreenMate(name = "", type = "", image = R.drawable.plant1))
    val mainGreenMate: StateFlow<GreenMate> get() = _mainGreenMate

    fun isDataLoaded() = _isDataLoaded.value
    fun setIsDataLoaded(isDataLoaded: Boolean) {
        _isDataLoaded.value = isDataLoaded
    }

    fun isGreenMateEmpty() = _greenMates.value.isEmpty()
    fun isMainGreenMateEmpty() = _mainGreenMate.value.name.isEmpty()

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


    /* network */
    fun getAllGreenMates() {
        val response = repository.getAllGreenMates()
        _greenMates.value = response
    }

    fun addGreenMate(greenMate: GreenMate) {
        val response = repository.addGreenMate(greenMate)
        _isSuccess.value = response
    }
}