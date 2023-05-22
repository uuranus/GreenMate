package com.greenmate.greenmate.ui.addGreenMate

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddGreenMateViewModel : ViewModel() {
    private val _isModuleAdded = MutableStateFlow(false)
    private val isModuleAdded: StateFlow<Boolean> get() = _isModuleAdded

    private val _isPlantAdded = MutableStateFlow(false)
    private val isPlantAdded: StateFlow<Boolean> get() = _isPlantAdded

    private val _plantTypes = MutableStateFlow(listOf("몬스테라", "몬스테라", "몬스테라", "몬스테라", "몬스테라"))
    val plantTypes: StateFlow<List<String>> get() = _plantTypes

    private val _currentPlantTypes = MutableStateFlow(_plantTypes.value)
    val currentPlantTypes: StateFlow<List<String>> get() = _currentPlantTypes

    val name = MutableStateFlow<String>("")

    fun search(text: String) {
        _currentPlantTypes.value = _plantTypes.value.filter { it.startsWith(text) }
    }

    fun saveGreenMate() {
        if (_isModuleAdded.value.not()) {
            _isModuleAdded.value = false
        } else if (_isPlantAdded.value.not()) {
            _isPlantAdded.value = false
        }
        else{
            //TODO 네트워크 연결해서 저장
        }
    }
}