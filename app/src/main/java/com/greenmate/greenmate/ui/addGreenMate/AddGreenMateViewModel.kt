package com.greenmate.greenmate.ui.addGreenMate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddGreenMateViewModel : ViewModel() {
    private val _isModuleFirst = MutableStateFlow(true)
    val isModuleFirst: StateFlow<Boolean> get() = _isModuleFirst

    private val _isSavedSuccess = MutableStateFlow(false)
    val isSavedSuccess: StateFlow<Boolean> get() = _isSavedSuccess

    private val _plantTypes = MutableStateFlow(listOf("몬스테라", "몬스테라", "몬스테라", "몬스테라", "몬스테라"))
    val plantTypes: StateFlow<List<String>> get() = _plantTypes

    private val _currentPlantTypes = MutableStateFlow(_plantTypes.value)
    val currentPlantTypes: StateFlow<List<String>> get() = _currentPlantTypes

    val name = MutableStateFlow<String>("")

    fun setModuleAdded(isModuleAdded: Boolean) {
        _isModuleFirst.value = isModuleAdded
    }

    fun isModuleAdded() = _isModuleFirst.value

    fun search(text: String) {
        _currentPlantTypes.value = _plantTypes.value.filter { it.startsWith(text) }
    }

    fun saveGreenMate() {

    }
}